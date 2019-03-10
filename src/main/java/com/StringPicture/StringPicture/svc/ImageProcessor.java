package com.StringPicture.StringPicture.svc;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ImageProcessor {
    public static void main(String args[]) {
        try {
            System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

            Mat src =
                    Imgcodecs.imread("src/assets/img/sample1.jpg", Imgcodecs.IMREAD_COLOR);

            if ( src.empty() ) {
                System.out.println("Error opening image");
            }

            // convert to grayscale
            Mat gray = new Mat();
            src.convertTo(src, 0);
            Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

            // crop circle
            Mat mask = new Mat(src.rows(), src.cols(), CvType.CV_8U, Scalar.all(0));
            Point center = new Point(100, 100);
            int radius = 100;
            Imgproc.circle(mask, center, radius, new Scalar(255,255,255), -1, 8, 0);

            Mat masked = new Mat();
            src.copyTo( masked, mask );

            Mat thresh = new Mat();
            Imgproc.threshold( mask, thresh, 1, 255, Imgproc.THRESH_BINARY );

            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(thresh, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            Rect rect = Imgproc.boundingRect(contours.get(0));
            Mat cropped = masked.submat(rect);

            HighGui.imshow("Cropped circle", cropped);
            HighGui.waitKey();

            System.exit(0);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
