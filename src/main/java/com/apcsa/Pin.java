package main.java.com.apcsa;

import org.mindrot.jbcrypt.BCrypt;

// deal with pin hashes using bcrypt for security
public class Pin {
    // define bcrypt workload to use when generating hashes
    private static int workload = 12;
    private static String salt = BCrypt.gensalt(workload);

    // generate a string representing a hashed pin
    public static String generateHash(String pin) {
        String hashed_pin = BCrypt.hashpw(pin, salt);

        return hashed_pin;
    }

    // verify a computed hash from plaintext pin with that of a stored hash
    public static boolean validatePin(String pin, String hash) {
        boolean pin_verified = false;

        if (hash == null) {
            throw new java.lang.IllegalArgumentException("Invalid hash provided.");
        }

        pin_verified = BCrypt.checkpw(pin, hash);

        return pin_verified;
    }
}
