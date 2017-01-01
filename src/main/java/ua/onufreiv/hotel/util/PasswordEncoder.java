package ua.onufreiv.hotel.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yurii on 1/1/17.
 */
public class PasswordEncoder {
    public static String encode(String password) {
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
            e.printStackTrace();
            return null;
        }
        return encodedPwd;
    }
}
