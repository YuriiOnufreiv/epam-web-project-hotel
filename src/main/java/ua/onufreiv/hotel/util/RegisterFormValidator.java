package ua.onufreiv.hotel.util;

/**
 * Created by yurii on 1/28/17.
 */
public class RegisterFormValidator {
    private static final int PHONE_NUMBER_LENGTH = 12;

    public static boolean isValidPhoneNumber(String number) {
        if (number.length() != PHONE_NUMBER_LENGTH) {
            return false;
        }

        for (int i = 0; i < PHONE_NUMBER_LENGTH; i++) {
            if (!Character.isDigit(number.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isValidPassword(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,8}";
        return password.matches(pattern);
    }
}
