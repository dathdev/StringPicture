package com.StringPicture.StringPicture.svc.Storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder storagePath for storing files
     */
    private String storagePath = StorageHelper.STORAGE_PATH;
    private String resultPath = StorageHelper.RESULT_PATH;

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }
}
