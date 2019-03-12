package com.StringPicture.StringPicture.svc.ImageProcessor;

import com.StringPicture.StringPicture.svc.Storage.StorageHelper;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import java.nio.file.Path;

public class ImageProcessor {
    public static void stringifyImage(String filename){
        Mat result = new ImageBuilder().getImageMat(StorageHelper.STORAGE_PATH + filename).applyGrayscale().cropCircle(650, 600, 280).stringify().build();
        Imgcodecs.imwrite(StorageHelper.RESULT_PATH + filename, result);
    }

    public static void main(String args[]) {
        String sourcePath = "src/assets/img/sample1.jpg";
        Mat result = new ImageBuilder().getImageMat(sourcePath).applyGrayscale().cropCircle(650, 600, 280).stringify().build();

        HighGui.imshow("Cropped image", result);
        HighGui.waitKey();
        System.exit(0);
    }
}
