import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.InvalidKeySpecException;

public class Customer {
    private String firstName;
    private String lastName;
    private String uuid = "";

    private byte accountPinHash[];
    private ArrayList<Account> customerAccounts;

    // create new customer with given name, pin, and bank branch
    public Customer(String firstName, String lastName, String pin, Bank bank) {

        // set user's name
        this.firstName = firstName;
        this.lastName = lastName;

        // store the pin's salted PBKDF2 hash instead of original, for security reasons
        this.accountPinHash = getAccountPinHash(pin);

        // get a new, unique universal unique ID for the user
        this.uuid = bank.getNewUserUUID();

        // create empty list of accounts
        customerAccounts = new ArrayList<Account>();

        // print log message
        System.out.printf("New user %s, %s with ID %s created.\n",
                lastName, firstName, uuid);
    }

    // method to get account pin as hash for security purposes
    public byte[] getAccountPinHash(String pin) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Generate salt for hash
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);

        // initialize key factory 
        PBEKeySpec spec = new PBEKeySpec(pin.toCharArray(), salt, 1000, 512);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();

        return hash;
    }
}
