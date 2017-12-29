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

        return Base64.encodeToString(md.digest(DataByte), Base64.DEFAULT);

    }
}




    /*public string CalculateHash(string clearTextPassword, string salt)
    {

        // hasło do tablicy bajtów (salt - to nazwa użytkownika)
        byte[] saltedHashBytes = Encoding.UTF8.GetBytes(clearTextPassword + salt);
        // Algorytm haszujący SHA256
        HashAlgorithm algorithm = new SHA256Managed();
        byte[] hash = algorithm.ComputeHash(saltedHashBytes);
        // zwraca hasz to Base64
        return Convert.ToBase64String(hash);
    }


    public String CalculateHash(String Login, String Password) {

		String temp = new StringBuilder(Password).append(Login).toString();

		byte[] bytes;
		try {
			bytes = temp.getBytes(StandardCharsets.UTF_8);
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(bytes);
			return new String(hash, StandardCharsets.UTF_8);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;

	}



    */