package com.StringPicture.StringPicture.svc.Storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file);

    Stream<Path> loadAll();

    Path loadStorage(String filename);

    Path loadResults(String filename);

    Resource loadStorageAsResource(String filename);

    Resource loadResultsAsResource(String filename);

    void deleteAll();

}
