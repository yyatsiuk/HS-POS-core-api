package com.yyatsiuk.api.core.utils.hash;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeGenerator {

    private static final int CODE_LENGTH_MIN = 8;
    private static final int CODE_LENGTH_MAX = 64;
    private static final String SHA256 = "SHA-256";

    @SneakyThrows
    public static String generateRandomCode(String text, int length) {
        if (length < CODE_LENGTH_MIN || length > CODE_LENGTH_MAX) {
            throw new IllegalArgumentException(
                    String.format("SLength must not be less than %s or grater than %s",
                            CODE_LENGTH_MIN, CODE_LENGTH_MAX));
        }

        MessageDigest hashFunction = MessageDigest.getInstance(SHA256);
        byte[] hash = hashFunction.digest(text.getBytes(StandardCharsets.UTF_8));
        String hexHash = bytesToHex(hash);

        StringBuilder code = new StringBuilder();
        final RandomGenerator generator = RandomGenerator.getDefault();

        IntStream.generate(() -> generator.nextInt(hexHash.length() + 1))
                .limit(length)
                .forEach(index -> code.append(hexHash.charAt(index)));

        return code.toString().toUpperCase();
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
