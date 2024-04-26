package com.imageupload.controller;

import com.imageupload.entity.Image;
import com.imageupload.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }

        try {
            byte[] imageData = file.getBytes();
            imageService.saveImage(imageData);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<Image> imageOptional = imageService.getImage(id);
        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
//            MediaType mediaType = MediaType.APPLICATION_PDF;
//            return ResponseEntity.ok().contentType(mediaType).body(image.getImage());

            byte[] fileBytes = image.getImage(); // Assuming this is the byte array representing the Excel file

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "filename.zip"); // Change "filename.xlsx" to your desired file name

            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);


        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/image-stream/{id}")
    public ResponseEntity<InputStreamResource> getImageStream(@PathVariable Long id) {
        Optional<Image> imageOptional = imageService.getImage(id);
        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            MediaType mediaType = MediaType.IMAGE_PNG; // Default to PNG

            ByteArrayInputStream inputStream = new ByteArrayInputStream(image.getImage());
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

            return ResponseEntity.ok().contentType(mediaType).body(inputStreamResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

