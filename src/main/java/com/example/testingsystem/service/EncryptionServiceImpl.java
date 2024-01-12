package com.example.testingsystem.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;
import java.util.Base64;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    private SecretKey secretKey;

    {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            secretKey = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String encrypt(String str){
        String encryptedText;
        try {
            Cipher encryptCipher = Cipher.getInstance("AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = encryptCipher.doFinal(str.getBytes());
            encryptedText = Base64.getUrlEncoder().encodeToString(encryptedBytes);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return encryptedText;
    }

    @Override
    public String decrypt(String str){
        String decryptedText;
        try {
            Cipher decryptCipher = Cipher.getInstance("AES");
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
            System.out.println("база началась");
            byte[] decodedEncryptedBytes = Base64.getUrlDecoder().decode(str);
            System.out.println("база закончилась");
            byte[] decryptedBytes = decryptCipher.doFinal(decodedEncryptedBytes);
            decryptedText = new String(decryptedBytes);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return decryptedText;
    }

}
