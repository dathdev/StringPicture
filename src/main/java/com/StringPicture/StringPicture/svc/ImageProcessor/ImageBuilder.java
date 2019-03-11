package com.StringPicture.StringPicture.svc.ImageProcessor;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ImageBuilder {
    private Mat _image;

    public ImageBuilder() {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            _image = new Mat();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ImageBuilder getImageMat(String imgPath){
        _image = Imgcodecs.imread(imgPath, Imgcodecs.IMREAD_COLOR);
        return this;
    }

    public ImageBuilder applyGrayscale() {
        Imgproc.cvtColor(_image, _image, Imgproc.COLOR_BGR2GRAY);
        return this;
    }

    public ImageBuilder cropCircle(int x, int y, int radius){
        Mat mask = new Mat(_image.rows(), _image.cols(), CvType.CV_8U, Scalar.all(0));
        Point center = new Point(x, y);
        Imgproc.circle(mask, center, radius, new Scalar(255,255,255), -1, 8, 0);

        Mat masked = new Mat();
        _image.copyTo(masked, mask);

        Mat thresh = new Mat();
        Imgproc.threshold(mask, thresh, 1, 255, Imgproc.THRESH_BINARY);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(thresh, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        Rect rect = Imgproc.boundingRect(contours.get(0));
        Mat cropped = masked.submat(rect);

        _image = cropped;
        return this;
    }

    public Mat build() {
        return _image;
    }
}
