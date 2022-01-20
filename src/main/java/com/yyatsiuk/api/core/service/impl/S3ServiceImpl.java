package com.yyatsiuk.api.core.service.impl;

import com.yyatsiuk.api.core.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Log4j2
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    private final S3Client s3Client;

    @Override
    public String saveObject(byte[] data, String key) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        log.info("Uploading image: {} to the s3 bucket", key);
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(data));
        return s3Client.utilities().getUrl(
                GetUrlRequest.builder().bucket(bucketName).key(key).build()).toString();
    }

}
