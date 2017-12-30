package com.example.zumirka.androidquiz.Utilities;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Zumirka on 29.12.2017.
 */

public class Encryption {

   public String CalculateHash(String Password, String Login) throws NoSuchAlgorithmException {

       String input= new StringBuilder(Password).append(Login).toString();
       byte DataByte[]=input.getBytes();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String base=Base64.encodeToString(md.digest(DataByte), Base64.DEFAULT);

       return  base.substring(0,base.length()-1);
    }
}

