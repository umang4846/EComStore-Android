package com.appprocessors.ecomstore.utils;

import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PassEncDec {
    String AES= "AES";

     String encrypt(String data,String passKey) throws  Exception{
        SecretKeySpec key = generateKey(passKey);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal,Base64.DEFAULT);
        return encryptedValue;
    }


    public  SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }

    public String decrypt(String data,String passKey) throws Exception{
        SecretKeySpec key = generateKey(passKey);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodedValue = Base64.decode(data,Base64.DEFAULT);
        byte[] encVal = c.doFinal(decodedValue);
        String encryptedValue =new String(encVal);
        return encryptedValue;
    }


}
