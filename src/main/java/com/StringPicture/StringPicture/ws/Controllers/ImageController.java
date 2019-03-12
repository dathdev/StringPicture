package com.StringPicture.StringPicture.ws.Controllers;

import com.StringPicture.StringPicture.svc.ImageProcessor.ImageProcessor;
import com.StringPicture.StringPicture.svc.Storage.StorageFileNotFoundException;
import com.StringPicture.StringPicture.svc.Storage.StorageService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ImageController {
    private final StorageService storageService;

    @Autowired
    public ImageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/images")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> listUploadedFiles() {
        List<String> fileNames = new ArrayList<>();
        fileNames.addAll(storageService.loadAll().map(Path::getFileName).map(Path::toString).collect(Collectors.toList()));

        String json = new Gson().toJson(fileNames);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @GetMapping("/image/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadStorageAsResource(filename);
        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/image/stringify")
    @ResponseBody
    public ResponseEntity<Resource> stringifyImage(@RequestParam("file") MultipartFile source) {
        try {
            storageService.store(source);
            String filename = source.getOriginalFilename();
            ImageProcessor.stringifyImage(filename);
            Resource result = storageService.loadResultsAsResource(filename);
            return ResponseEntity.ok().header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + result.getFilename() + "\"").body(result);
        } catch (Exception e) {
            return ResponseEntity.status(409).contentType(MediaType.APPLICATION_JSON).build();
        }

    }

    @PostMapping("/image/upload")
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            storageService.store(file);
            return ResponseEntity.ok().header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getOriginalFilename() + "\"").body(Helper.generateJsonResponseMessage("Uploaded " + file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(409).contentType(MediaType.APPLICATION_JSON).build();
        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}
