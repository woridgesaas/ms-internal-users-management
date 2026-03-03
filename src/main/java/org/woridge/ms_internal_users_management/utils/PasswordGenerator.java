package org.woridge.ms_internal_users_management.utils;

import java.security.SecureRandom;

public final class PasswordGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "@/.$^-_&?!,.#%";

    private static final String CHARS = UPPER + LOWER + DIGITS + SPECIAL;
    private PasswordGenerator() {throw new UnsupportedOperationException("You can't instantiate Password Generator utils object");}

    public static String generate() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
       return sb.toString();
       // return "douaeAy@22";
    }

}