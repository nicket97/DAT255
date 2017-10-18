package platooning;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import java.awt.Dimension;
import java.io.File;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_imgproc.CvMoments;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.opencv.core.Mat;

import communication.MopedSteeringHandler;
import server.DataGrabber;

/**
 * Responsible for image recognition and calculations for platooning. Analyzes
 * the images that are sent from the MOPED to the server and returns new
 * steering values.
 */
public class PlatooningController {
	private static File oldFile;
	private static File newFile;

	public int locateImage(Object imgFile) {
		// input img
		
		if (newFile == null) {
			newFile = (File) imgFile;
		} else {
			oldFile = newFile;
			newFile = (File) imgFile;
			oldFile.delete();
		}
		
		
		DataGrabber.setImagePath(newFile.toURI().toString());
		IplImage orgImg = cvLoadImage(newFile.getAbsolutePath());
		

		IplImage thresholdImage = createThreshold(orgImg);
		thresholdImage = morphImage(thresholdImage);

		Dimension position = getCoordinates(thresholdImage);

		int posX1 = (int) (position.getWidth() - 150);

		// if nothing found in image, set posx1 to 0
		if (posX1 == -150.0) {
			System.out.println("cant find green");
			posX1 = MopedSteeringHandler.getHandling();

		}

		//img.delete();

		// output steering signal
		return toSteering(posX1);
	}

	private int toSteering(int posX) {
		int maxSteering = 25;
		int steerInt = 0;
		if (posX > maxSteering) {
			steerInt = -maxSteering;
		} else if (posX < -maxSteering) {
			steerInt = maxSteering;
		} else {
			// here posx is between -maxSteering and maxSteering
			steerInt = posX * 1;
			steerInt = steerInt * -1;
		}

		steerInt = steerInt * -1;

		// steerInt -= 10;
		System.out.println("GENERATED STEERINT: " + steerInt);
		return steerInt;
	}

	static Dimension getCoordinates(IplImage thresholdImage) {
		int posX = 0;
		int posY = 0;
		CvMoments moments = new CvMoments();
		cvMoments(thresholdImage, moments, 1);

		double momX10 = cvGetSpatialMoment(moments, 1, 0); // (x,y)
		double momY01 = cvGetSpatialMoment(moments, 0, 1);// (x,y)
		double area = cvGetCentralMoment(moments, 0, 0);
		posX = (int) (momX10 / area);
		posY = (int) (momY01 / area);

		return new Dimension(posX, posY);
	}

	static IplImage createThreshold(IplImage orgImg) {
		IplImage imgHSV = cvCreateImage(cvGetSize(orgImg), 8, 3);

		cvCvtColor(orgImg, imgHSV, CV_BGR2HSV);
		IplImage imgThreshold = cvCreateImage(cvGetSize(orgImg), 8, 1);

		// custom range from MyRobotLab
		cvInRangeS(imgHSV, cvScalar(36, 66, 20, 0), cvScalar(79, 255, 255, 0), imgThreshold);

		cvReleaseImage(imgHSV);

		// gaussian blur
		cvSmooth(imgThreshold, imgThreshold);

		return imgThreshold;
	}

	static IplImage morphImage(IplImage img) {

		OpenCVFrameConverter fc = new OpenCVFrameConverter.ToMat();

		// Morphological operations
		// erode
		Mat test123 = cvarrToMat(img);
		Mat element = new Mat(9, 9, CV_8U, new opencv_core.Scalar(1d));
		erode(test123, test123, element);
		erode(test123, test123, element);

		img = fc.convertToIplImage(fc.convert(test123));

		// dilate
		Mat test1234 = cvarrToMat(img);
		Mat element1 = new Mat(10, 10, CV_8U, new opencv_core.Scalar(1d));
		dilate(test123, test1234, element1);

		img = fc.convertToIplImage(fc.convert(test1234));

		return img;
	}

	static IplImage loadImage() {

		// temp filepath
		File folder = new File("/Users/erikstrid/Desktop/CameraTest/src/main/resources");
		File[] listOfFiles = folder.listFiles();

		String x = "";
		int max = 0;

		for (int i = 0; i < listOfFiles.length; i++) {

			System.out.println(listOfFiles[i].getName());
			x = listOfFiles[i].getName();
			x.substring(0, x.length() - 4);

			int temp = Integer.parseInt(x);

			if (max < temp) {
				max = temp;
			}
		}
		x = String.valueOf(max);

		IplImage orgImg = cvLoadImage("/Users/erikstrid/Desktop/CameraTest/src/main/resources/" + x + ".jpg");

		return orgImg;
	}

	// simple steering command method for early testing purposes
	static double calculateSteeringLinear(double position) {
		if (position < 0) {
			return -2 * position / 10;
		} else if (position > 0) {
			return 2 * position / 10;
		} else
			return 0;
	}

	// simple steering command method 2 for early testing purposes
	static double calculateSteeringPotential(double position) {
		if (position < 0) {
			return -Math.pow((position), 2) / 1000;
		} else if (position > 0) {
			return Math.pow((position), 2) / 1000;
		} else
			return 0;
	}

	static double calculateAngle(double posX, double dist) {
		if (posX < 0) {
			return -(Math.atan(dist / (-posX)));
		} else {
			return (Math.atan(dist / (posX)));
		}

	}
}