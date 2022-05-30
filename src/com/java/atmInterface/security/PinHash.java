package com.java.atmInterface.security;

import com.java.atmInterface.security.helper.DatatypeConverter;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

// Seperate class for handling the security of the account PIN
public class PinHash {
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    
    public static final int SALT_BYTES = 16;
    public static final int HASH_BYTES  = 512;
    public static final int PBKDF2_ITERATIONS = 1000;

    // set variables for the indexes for validation later
    public static final int ITERATION_INDEX = 0;
    public static final int SALT_INDEX = 1;
    public static final int PBKDF2_INDEX = 2;

    /**
     * Returns a salted PBKDF2 hash of the pin.
     *
     * @param   pin         the pin to hash
     * @return              a salted PBKDF2 hash of the pin
     */
    public static String generateHash(String pin) 
        throws NoSuchAlgorithmException, InvalidKeySpecException 
    {
        // generate a random salt
            SecureRandom rnd = new SecureRandom();
            byte[] salt = new byte[SALT_BYTES];
            rnd.nextBytes(salt);

            // hash the pin
            byte[] hash = pbkdf2(pin.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTES);
            // format iterations:salt:hash
            return PBKDF2_ITERATIONS + ":" + toHexString(salt) + ":" + toHexString(hash);
    }

    /**
     * Validates a pin using a hash.
     *
     * @param   pin         the pin to check
     * @param   goodHash    the hash of the valid pin
     * @return              true if the pin is correct, false if not
     */
    public static boolean validatePin(String pin, String goodHash) 
        throws NoSuchAlgorithmException, InvalidKeySpecException 
    {
           return validatePin(pin.toCharArray(), goodHash);
    }

    // Overload the above method
    public static boolean validatePin(char[] pin, String goodHash) 
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // decode the hash into its parameters
        String[] params = goodHash.split(":");
        int iterations = Integer.parseInt(params[ITERATION_INDEX]);
        byte[] salt = toByteArray(params[SALT_INDEX]);
        byte[] hash = toByteArray(params[PBKDF2_INDEX]);

        // compute the hash of the provided pin, using the same salt,
        // iteration count, and hash length
        byte[] testHash = pbkdf2(pin, salt, iterations, hash.length);

        System.out.println("hash " + hash);
        System.out.println("test hash " + testHash);
        // compare the hashes in constant time. the pin is correct if
        // both hashes match.
        return isEqual(hash, testHash);
    }

    /*
    * Constant Time Algorithm 
    * Author: Coda Hale
    * https://codahale.com/a-lesson-in-timing-attacks/
    */

    /**
     * Compares two byte arrays in length-constant time. This comparison
     * method is utilized to prevent timing attacks on the hashes.
     * 
     * @param   a       the first byte array
     * @param   b       the second byte array 
     * @return          true if both byte arrays are the same, false if not
    **/
    public static boolean isEqual(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }

        return result == 0;
    }

    /**
     *  Generates the PBKDF2 hash of a pin
     *
     * @param   pin         the pin to hash
     * @param   salt        the salt
     * @param   iterations  the iteration count (slowness factor)
     * @param   bytes       the length of the hash to compute in bytes
     * @return              the PBDKF2 hash of the pin
     */
    private static byte[] pbkdf2(char[] pin, byte[] salt, int iterations, int bytes)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        PBEKeySpec spec = new PBEKeySpec(pin, salt, iterations, bytes);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    /**
     * Converts a byte array into a hexadecimal string.
     *
     * @param   array       the byte array to convert
     * @return              a length*2 character string
     */
    private static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    /**
     * Converts a string of hexadecimal characters into a byte array.
     *
     * @param   hex         the hex string
     * @return              the hex string decoded into a byte array
     */
    private static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}
