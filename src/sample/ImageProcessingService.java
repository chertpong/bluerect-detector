package sample;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ImageProcessingService {
    public Mat drawOverRectangle(Mat frame) {
        Mat result = frame.clone();
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
        Size kernel33 = new Size(7,7    );
        Imgproc.GaussianBlur(frame, frame, kernel33, 0);
        Imgproc.adaptiveThreshold(frame, frame,255,Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY,75,10);
        Core.bitwise_not(frame, frame);
        Mat lines = new Mat();
        Imgproc.Canny(frame, frame, 50, 200, 3);

        Imgproc.HoughLinesP(frame, lines, 1, Math.PI / 180, 50,frame.cols() / 10);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(frame, contours, hierarchy,Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
        // TODO: remove this
        System.out.println("contour size " + contours.size());
//        Imgproc.drawContours(result, contours, -1, new Scalar(0, 255, 255), 2);
        List<MatOfPoint> rects = new ArrayList<>();
        for (int x = 0; x < contours.size(); x++) {
            Rect rect = null;

            MatOfPoint2f thisContour2f = new MatOfPoint2f();
            MatOfPoint approxContour = new MatOfPoint();
            MatOfPoint2f approxContour2f = new MatOfPoint2f();
            contours.get(x).convertTo(thisContour2f, CvType.CV_32FC2);
            double arcLength = Imgproc.arcLength(thisContour2f, true);
            Imgproc.approxPolyDP(thisContour2f, approxContour2f,  arcLength * 0.10, true);
            approxContour2f.convertTo(approxContour, CvType.CV_32S);
            RotatedRect rotatedRect = Imgproc.minAreaRect(approxContour2f);
            if (approxContour.size().height == 4 && rotatedRect.size.area() > 2000 && rotatedRect.size.area() < 20000) {
                System.out.println(rotatedRect.size.area());
                rect = Imgproc.boundingRect(approxContour);
            }
            if (rect != null) {
                rects.add(contours.get(x));
            }
        }
        System.out.println("rect" + rects.size());
//        result = frame;
        Imgproc.drawContours(result, rects, -1, new Scalar(0, 0, 255), 2);
        return result;
//        for (int rectIndex = 0; rectIndex < rects.size(); rectIndex++) {
//
//            System.out.println("drawn" + rectIndex);
//        }
//        for( int x = 0; x < lines.rows(); x++ )
//        {
//            double[] vec = lines.get(x, 0);
//            double x1 = vec[0],
//                    y1 = vec[1],
//                    x2 = vec[2],
//                    y2 = vec[3];
//            Point start = new Point(x1, y1);
//            Point end = new Point(x2, y2);
//            double dx = x1 - x2;
//            double dy = y1 - y2;
//            Imgproc.line(frame, start, end, new Scalar(0,255, 0, 255),2);
//
//        }

//        Imgproc.HoughLines(frame, frame, 1, Math.PI / 180, 100, 10);
//        cv::GaussianBlur(img,img,size,0);
//        adaptiveThreshold(img, img,255,CV_ADAPTIVE_THRESH_MEAN_C, CV_THRESH_BINARY,75,10);
//        cv::bitwise_not(img, img);
    }
}
