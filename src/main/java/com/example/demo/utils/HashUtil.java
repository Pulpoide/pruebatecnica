package com.example.demo.utils;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class HashUtil {

    public static String getHash(byte[] bytes, String algorithm) {
        String hashValue = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(bytes);
            byte[] digestedBytes = messageDigest.digest();
            hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();

        } catch (Exception e) {
            System.out.println("Hubo un error con el hash!");
        }
        return hashValue;
    }
}
