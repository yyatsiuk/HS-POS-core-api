package com.yyatsiuk.api.core.service;

public interface S3Service {

    String saveObject(byte[] data, String key);

}
