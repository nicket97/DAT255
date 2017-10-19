package platooning;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.opencv.core.CvType.CV_32F;

import java.awt.Dimension;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_imgproc.CvMoments;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;

import communication.MopedSteeringHandler;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
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

		if (newFile == null) {
			newFile = (File) imgFile;
		} else {
			oldFile = newFile;
			newFile = (File) imgFile;
			oldFile.delete();
		}

		//DataGrabber.setImagePath(newFile.toURI().toString());

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		String bookObject = "/Users/olofenstrom/Desktop/icon.jpg";
		String bookScene = newFile.getAbsolutePath();

		try {
		Mat objectImage = Imgcodecs.imread(bookObject, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Mat sceneImage = Imgcodecs.imread(bookScene, Imgcodecs.CV_LOAD_IMAGE_COLOR);

		MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
		FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.ORB);
		featureDetector.detect(objectImage, objectKeyPoints);

		MatOfKeyPoint objectDescriptors = new MatOfKeyPoint();
		DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
		descriptorExtractor.compute(objectImage, objectKeyPoints, objectDescriptors);

		//Create matrix for output image.
		Mat outputImage = new Mat(objectImage.rows(), objectImage.cols(), Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Scalar newKeypointColor = new Scalar(255, 0, 0);

		Features2d.drawKeypoints(objectImage, objectKeyPoints, outputImage, newKeypointColor, 0);

		//Match object image with scene image
		MatOfKeyPoint sceneKeyPoints = new MatOfKeyPoint();
		MatOfKeyPoint sceneDescriptors = new MatOfKeyPoint();
		featureDetector.detect(sceneImage, sceneKeyPoints);
		descriptorExtractor.compute(sceneImage, sceneKeyPoints, sceneDescriptors);


		List<MatOfDMatch> matches = new LinkedList<MatOfDMatch>();

		DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
		sceneDescriptors.convertTo(sceneDescriptors, CV_32F);
		objectDescriptors.convertTo(objectDescriptors, CV_32F);

		descriptorMatcher.knnMatch(objectDescriptors, sceneDescriptors, matches, 2);

		LinkedList<DMatch> goodMatchesList = new LinkedList<DMatch>();

		float nndrRatio = 0.9f;

		for (int i = 0; i < matches.size(); i++) {
			MatOfDMatch matofDMatch = matches.get(i);
			DMatch[] dmatcharray = matofDMatch.toArray();
			DMatch m1 = dmatcharray[0];
			DMatch m2 = dmatcharray[1];

			if (m1.distance <= m2.distance * nndrRatio) {
				goodMatchesList.addLast(m1);

			}
		}

		if (goodMatchesList.size() >= 7) {
				System.out.println("good matches list: " + goodMatchesList.size());

				List<KeyPoint> objKeypointlist = objectKeyPoints.toList();
				List<KeyPoint> scnKeypointlist = sceneKeyPoints.toList();


				LinkedList<Point> objectPoints = new LinkedList();
				LinkedList<Point> scenePoints = new LinkedList();

				for (int i = 0; i < goodMatchesList.size(); i++) {
					objectPoints.addLast(objKeypointlist.get(goodMatchesList.get(i).queryIdx).pt);
					scenePoints.addLast(scnKeypointlist.get(goodMatchesList.get(i).trainIdx).pt);
				}

				MatOfPoint2f objMatOfPoint2f = new MatOfPoint2f();
				objMatOfPoint2f.fromList(objectPoints);
				MatOfPoint2f scnMatOfPoint2f = new MatOfPoint2f();
				scnMatOfPoint2f.fromList(scenePoints);

				Mat homography = Calib3d.findHomography(objMatOfPoint2f, scnMatOfPoint2f, Calib3d.RANSAC, 3);

				Mat obj_corners = new Mat(4, 1, CvType.CV_32FC2);
				Mat scene_corners = new Mat(4, 1, CvType.CV_32FC2);

				obj_corners.put(0, 0, new double[]{0, 0});
				obj_corners.put(1, 0, new double[]{objectImage.cols(), 0});
				obj_corners.put(2, 0, new double[]{objectImage.cols(), objectImage.rows()});
				obj_corners.put(3, 0, new double[]{0, objectImage.rows()});

				Core.perspectiveTransform(obj_corners, scene_corners, homography);

				Mat img = Imgcodecs.imread(bookScene, Imgcodecs.CV_LOAD_IMAGE_COLOR);

				Point x1 = new Point(scene_corners.get(0, 0));
				Point x2 = new Point(scene_corners.get(1, 0));
				Point x3 = new Point(scene_corners.get(2, 0));
				Point x4 = new Point(scene_corners.get(3, 0));

				int posX = (int)(x1.x+x2.x+x3.x+x4.x)/4;
				int posy = (int)(x1.y+x2.y+x3.y+x4.y)/4;
				Imgproc.drawMarker(img, new Point(posX,posy),new Scalar(0, 255, 0));

				System.out.print("X: " + posX + "\n" + "Y: " + posy + "\n");


				//Imgcodecs.imwrite("test1.jpg", outputImage);
				//Imgcodecs.imwrite("test2.jpg", matchoutput);
				Imgcodecs.imwrite("test3.jpg", img);
				int posX1 = (posX - 200);

				// if nothing found in image, set posx1 to 0
				if (posX1 == -200) {
					System.out.println("cant find image");
					posX1 = MopedSteeringHandler.getHandling();

				}

				
				//img.delete();

				// output steering signal
				DataGrabber.setImagePath(newFile.toURI().toString());
				DataGrabber.setPosX(posX);
				DataGrabber.setPosY(posy);
				
				return toSteering(posX1);

		} else {
			return MopedSteeringHandler.getHandling();
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR ERROR ERROR ERROR ERROR\nERROR ERROR ERROR ERROR");
			return MopedSteeringHandler.getHandling();
		}
	}

	private int toSteering(int posX) {
		int maxSteering = 30;
		int maxPixel = maxSteering + 10;
		int steerInt = 0;
		if (posX > maxPixel) {
			steerInt = -maxSteering;
		} else if (posX < -maxPixel) {
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

	/*
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
	*/
}
