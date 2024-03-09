package org.openjfx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean isValidString(String str) {
        return str.matches("^[a-zA-ZŪŪĀĀŠŠēūŗčļņžģāķĒŪĀŠĻŅĢČĶēū]+( [a-zA-ZŪŪĀĀŠŠēūŗčļņžģāķĒŪĀŠĻŅĢČĶēū]+)*$");
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}