package com.yyatsiuk.api.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThumbnailGenerator {

    private static final String JPEG_FORMAT = "JPEG";

    @SneakyThrows
    public static byte[] resizeImage(InputStream originalImage, double scale, double quality) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .scale(scale)
                .outputFormat(JPEG_FORMAT)
                .outputQuality(quality)
                .toOutputStream(outputStream);

        return outputStream.toByteArray();
    }

}
