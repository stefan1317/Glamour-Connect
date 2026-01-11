package com.example.glamour_connect.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class StringUtils {

    public static final String ROLE = "ROLE_";

    private static final Pattern EMAIL_REGEX = Pattern.
            compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static boolean isEmailValid(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }
}
