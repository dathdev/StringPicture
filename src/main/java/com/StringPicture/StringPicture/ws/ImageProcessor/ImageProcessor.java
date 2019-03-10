package com.StringPicture.StringPicture.ws.ImageProcessor;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;

public class ImageProcessor {
    public static void main(String args[]) {
        String sourcePath = "src/assets/img/sample1.jpg";
        Mat result = new ImageBuilder().getImageMat(sourcePath).applyGrayscale().cropCircle(650, 600, 280).build();

        HighGui.imshow("Cropped image", result);
        HighGui.waitKey();
        System.exit(0);
    }
}
