package camera;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_imgproc.CvMoments;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacpp.opencv_core.Mat;

import java.awt.Dimension;
import java.io.File;

public class ImageRecognition {


    public double locateImage(Object imgFile){
        //input img
    	File img = (File) imgFile;

    	IplImage orgImg = imgFile;

        IplImage thresholdImage = createThreshold(orgImg);
        thresholdImage = morphImage(thresholdImage);

        Dimension position = getCoordinates(thresholdImage);

        System.out.print(position.getWidth());

        //output steering signal
        return calculateSteeringLinear(position.getWidth() - 486.0);
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
        OpenCVFrameConverter fc = new OpenCVFrameConverter.ToMat();

        IplImage imgHSV = cvCreateImage(cvGetSize(orgImg), 8, 3);

        cvCvtColor(orgImg, imgHSV, CV_BGR2HSV);
        IplImage imgThreshold = cvCreateImage(cvGetSize(orgImg), 8, 1);

        //custom range from MyRobotLab
        cvInRangeS(imgHSV, cvScalar(40, 100, 50, 0), cvScalar(100, 255, 255, 0), imgThreshold);

        cvReleaseImage(imgHSV);

        //gaussian blur
        cvSmooth(imgThreshold, imgThreshold);

        //contours
        CvMemStorage storage = CvMemStorage.create();
        CvSeq contour = new CvSeq(null);

        cvFindContours(imgThreshold, storage, contour, Loader.sizeof(CvContour.class),
                CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);

        return imgThreshold;
    }

    static IplImage morphImage(IplImage img){

        OpenCVFrameConverter fc = new OpenCVFrameConverter.ToMat();

        //Morphological operations
        //erode
        Mat test123 = cvarrToMat(img);
        Mat element = new Mat(12, 12, CV_8U, new opencv_core.Scalar(1d));
        erode(test123, test123, element);
        erode(test123, test123, element);

        img = fc.convertToIplImage(fc.convert(test123));

        //dilate
        Mat test1234 = cvarrToMat(img);
        Mat element1 = new Mat(6, 6, CV_8U, new opencv_core.Scalar(1d));
        dilate(test123, test1234, element1);

        img = fc.convertToIplImage(fc.convert(test1234));

        return img;
    }

    static IplImage loadImage(){

        //temp filepath
        File folder = new File("/Users/erikstrid/Desktop/CameraTest/src/main/resources");
        File[] listOfFiles = folder.listFiles();

        String x = "";
        int max = 0;

        for (int i = 0; i < listOfFiles.length; i++) {

            System.out.println(listOfFiles[i].getName());
            x = listOfFiles[i].getName();
            x.substring(0, x.length() - 4);

            int temp = Integer.parseInt(x);

            if(max < temp){
                max = temp;
            }
        }
        x = String.valueOf(max);


        IplImage orgImg = cvLoadImage("/Users/erikstrid/Desktop/CameraTest/src/main/resources/" + x + ".jpg");

        return orgImg;
    }

    //simple steering command method for early testing purposes
    static double calculateSteeringLinear(){
        if(position<0){
            return -2* position/10;
        }else if(position>0){
            return 2* position/10;
        }
        else
            return 0;
    }
    //simple steering command method 2 for early testing purposes
    static double calculateSteeringPotential() {
        if (position < 0) {
            return -Math.pow((position), 2) / 1000;
        } else if (position > 0) {
            return Math.pow((position), 2) / 1000;
        } else
            return 0;
    }


}
