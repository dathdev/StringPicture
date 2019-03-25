package com.StringPicture.StringPicture.svc.ImageProcessor;

import com.StringPicture.StringPicture.svc.ImageProcessor.Models.Line;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class Stringifier {
    private static double WHITE = 255.0;

    public static void stringify(Mat image) {
        //get all circle points
        List<Point> circlePoints = Stringifier.getAllCirclePoints(image);
        //TODO: get all lines with intensity > threshold
        //TODO: threshold TBD
        //TODO: number of pins TBD
    }

    private static List<Line> getConnectableLines(Mat image, List<Point> points) {
        List<Line> result = new ArrayList<>();

        for (Point start : points) {
            for (Point end : points) {
                Line currentLine = new Line(start, end);
                // read all pixel on the line
            }
        }

        return result;
    }

    private static boolean shouldConnect(Line line, Mat image, double threshold) {
        return false;
    }

    private static List<Point> getAllCirclePoints(Mat image) {
        Mat blackSquareWhiteCircle = generateWhiteCircleOnBlackSquareImage(image);

        List<Point> result = new ArrayList<>();
        double test = blackSquareWhiteCircle.get(image.rows()/2,0)[0];
        for (int x = image.rows()/2; x < image.rows(); x++) {
            for (int y = 0; y <= image.cols()/2; y++) {
                if (blackSquareWhiteCircle.get(x,y)[0] == WHITE && !result.contains(new Point(x,y))) {
                    result.add(new Point(x,y));
                }
            }
        }
        for (int x = image.rows()-1; x >= image.rows()/2; x--) {
            for (int y = image.rows()/2; y < image.rows(); y++){
                 if (blackSquareWhiteCircle.get(x,y)[0] == WHITE && !result.contains(new Point(x,y))) {
                    result.add(new Point(x,y));
                }
            }
        }
        for (int x = image.rows()/2; x >= 0; x--) {
            for (int y = image.rows()-1; y >= image.rows()/2; y--) {
                if (blackSquareWhiteCircle.get(x,y)[0] == WHITE && !result.contains(new Point(x,y))) {
                    result.add(new Point(x,y));
                }
            }
        }
        for (int x = 0; x <= image.rows()/2; x++) {
            for (int y = image.rows()/2; y >= 0; y--) {
                if (blackSquareWhiteCircle.get(x,y)[0] == WHITE && !result.contains(new Point(x,y))) {
                    result.add(new Point(x,y));
                }
            }
        }

        return result;
    }

    private static Mat generateWhiteCircleOnBlackSquareImage(Mat image) {
        Mat background = new Mat(image.rows(), image.cols(), CvType.CV_8U, Scalar.all(0));
        Point center = new Point(image.rows()/2, image.cols()/2);
        Imgproc.circle(background, center, image.cols()/2, new Scalar(255,255,255), 1, 8, 0);
        return background;
    }
}
