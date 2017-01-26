package ua.onufreiv.hotel.util;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yurii on 1/1/17.
 */
public class PasswordEncoder {
    private final static Logger logger = Logger.getLogger(PasswordEncoder.class);

    private static PasswordEncoder instance;

    private PasswordEncoder() {
    }

    public static PasswordEncoder getInstance() {
        if (instance == null) {
            instance = new PasswordEncoder();
        }
        return instance;
    }

    public String encode(String password) {
        String encodedPwd;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte byteData[] = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            encodedPwd = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Failed to encode the password: ", e);
            return null;
        }
        return encodedPwd;
    }
}
