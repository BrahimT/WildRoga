package com.example.tools;

import android.text.Editable;
import android.util.Log;
import android.widget.EditText;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtilities {

    public static char[] editTextToCharArray(EditText et){
        Editable text = et.getText();

        char[] ca = new char [text.length()];

        for(int i = 0; i < text.length() - 1; i++){
            ca[i] = text.charAt(i);
        }

        return ca;
    }



    public static byte[] getSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt;
    }
    //https://www.baeldung.com/java-password-hashing consulted
    public static byte[] hashPassword(char[] password, byte[] salt){

        password = trim(password);

        KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return addSaltToHash(salt, factory.generateSecret(spec).getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] addSaltToHash(byte[] salt, byte[]hash){
        byte[] combined = new byte[salt.length + hash.length];

        for(int i = 0; i < salt.length; i++){
            combined[i] = salt[i];
        }

        for(int i = 0; i < hash.length; i++){
            combined[salt.length + i] = hash[i];
        }

        return combined;
    }

    public static byte[] getSaltFromHashedPassword(byte[] hashedPassword, int saltLength){
        byte[] salt = new byte[saltLength];

        for(int i = 0; i < saltLength; i++){
            salt[i] = hashedPassword[i];
        }

        return salt;
    }

    public static byte[] getHashFromHashedPassword(byte[] hashedPassword, int saltLength){
        byte[] hash = new byte[hashedPassword.length - saltLength];

        for(int i = saltLength; i < hashedPassword.length; i++){
            hash[i - saltLength] = hashedPassword[i];
        }

        return hash;
    }

    //from https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    public static boolean verifyPassword(byte[] hash1, char[] password){
        byte[] hash2 = getHashFromHashedPassword(hashPassword(password, getSaltFromHashedPassword(hash1, 16)), 16);
        hash1 = getHashFromHashedPassword(hash1, 16);

        int diff = hash1.length ^ hash2.length;

        for(int i = 0; i < hash1.length && i < hash2.length; i++)
        {
            diff |= hash1[i] ^ hash2[i];
        }

        return diff == 0;
    }

//    public static boolean verifyPassword(byte[] hashedPassword1, char[] password){
//        byte[] hashedPassword2 = hashPassword(password, getSaltFromHashedPassword(hashedPassword1, 16));
//        byte[] hash2 = getHashFromHashedPassword(hashedPassword2, 16);
//        byte[] hash1 = getHashFromHashedPassword(hashedPassword1, 16);
//
//
//        Log.d("hash1", byteArrayToString(hash1));
//        Log.d("hash2", byteArrayToString(hash2));
//
//        return byteArrayContentEquals(hash1, hash2);
//    }



    public static boolean byteArrayContentEquals(byte[] array1, byte[] array2){

        if(array1 == null && array2 == null) return true;

        if(array1 == null || array2 == null) return false;

        if (array1.length != array2.length) return false;

        for(int i = 0; i < array1.length; i++){
            if(array1[i] != array2[i]) return false;
        }

        return true;
    }

    public static char[] trim(char[] ca){

        if (ca.length == 0) return ca;

        int startIndex = -1;
        int endIndex = -1;

        for(int i = 0; i < ca.length; i++){
            if(ca[i] != ' '){
                startIndex = i;
                break;
            }
        }

        if(startIndex == -1) return ca;

        for(int i = ca.length - 1; i >= 0; i--){
            if(ca[i] != ' '){
                endIndex = i + 1;
                break;
            }
        }

        char[] trimmed = new char[endIndex - startIndex];

        for(int i = startIndex; i < endIndex; i++){
            trimmed[i - startIndex] = ca[i];
        }

        return trimmed;
    }

    public static String charArrayToString (char[] ca){
        String s = "";
        for(int i = 0; i < ca.length; i++){
            s += ca[i];
        }

        return s;
    }

    public static String byteArrayToString(byte[] ba){
        String s = "";
        for(int i = 0; i < ba.length; i++){
            s += (char) ba[i];
        }

        return s;
    }

}
