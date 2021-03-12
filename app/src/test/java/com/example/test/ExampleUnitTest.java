package com.example.test;

import android.widget.EditText;

import com.example.tools.PasswordUtilities;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testVerifyPassword(){
        char[] test = "Testing123".toCharArray();

        assertTrue(PasswordUtilities.verifyPassword(PasswordUtilities.hashPassword(test, PasswordUtilities.getSalt()), test));
    }

    @Test
    public void testBadVerifyPassword(){
        char[] test1 = "Testing123".toCharArray();
        char[] test2 = "Testing124".toCharArray();

        assertFalse(PasswordUtilities.verifyPassword(PasswordUtilities.hashPassword(test1, PasswordUtilities.getSalt()), test2));
    }

    @Test
    public void testAddSaltToHash(){

        byte[] saltHash = generateSaltHash();

        byte[] test = PasswordUtilities.addSaltToHash(PasswordUtilities.getSaltFromHashedPassword(saltHash, 16), PasswordUtilities.getHashFromHashedPassword(saltHash, 16));

        assertTrue(PasswordUtilities.byteArrayContentEquals(saltHash, test));
    }

    private byte[] generateSaltHash(){
        byte[] saltHash = new byte[20];
        for(int i = 0; i < 16; i++){
            saltHash[i] = (byte) i;
        }

        for(int i = 0; i < 4; i++){
            saltHash[i + 16] = (byte) i;
        }

        return saltHash;
    }
}