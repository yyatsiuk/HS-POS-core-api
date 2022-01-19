package com.yyatsiuk.api.core.web.controllers;

import com.yyatsiuk.api.core.enumerations.ImageType;
import com.yyatsiuk.api.core.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/images")
@CrossOrigin("*")
public class ImageController {

    private final S3Service s3Service;

    @Value("${aws.s3.key.products.images}")
    private String productImagesKey;

    @Value("${aws.s3.key.users.images}")
    private String usersImagesKey;

    public ImageController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveImage(@RequestPart MultipartFile image,
                                          @RequestParam ImageType type) throws IOException {

        byte[] imageBytes = image.getBytes();
        String key = generateS3Key(image.getOriginalFilename(), type);
        String fileUrl = s3Service.saveObject(imageBytes, key);

        return ResponseEntity.created(URI.create(fileUrl)).build();
    }

    private String generateS3Key(String fileName, ImageType type) {
        return switch (type) {
            case PRODUCT -> String.format("%s/%s", productImagesKey, fileName);
            case USER -> String.format("%s/%s", usersImagesKey, fileName);
        };
    }

}
