package com.yyatsiuk.api.core.controllers;

import com.yyatsiuk.api.core.enumerations.ImageType;
import com.yyatsiuk.api.core.models.response.ImageResponse;
import com.yyatsiuk.api.core.service.S3Service;
import com.yyatsiuk.api.core.utils.ThumbnailGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
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
    public ResponseEntity<ImageResponse> saveImage(@RequestPart MultipartFile image,
                                                   @RequestParam ImageType type) throws IOException {

        byte[] resizeImage = ThumbnailGenerator.resizeImage(image.getInputStream(), 0.4, 0.8);
        String key = generateS3Key(image.getOriginalFilename(), type);
        String fileUrl = s3Service.saveObject(resizeImage, key);

        return ResponseEntity.ok(new ImageResponse(fileUrl));
    }

    private String generateS3Key(String fileName, ImageType type) {
        return switch (type) {
            case PRODUCT -> String.format("%s/%s", productImagesKey, fileName);
            case USER -> String.format("%s/%s", usersImagesKey, fileName);
        };
    }

}
