package org.openjfx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    // Method for validating a string
    public static boolean isValidString(String str) {
        return str.matches("^[a-zA-Z0-9\\\\p{Punct} ]{1,15}$");
    }

    // Method for validating an email
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]{1,50}@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}