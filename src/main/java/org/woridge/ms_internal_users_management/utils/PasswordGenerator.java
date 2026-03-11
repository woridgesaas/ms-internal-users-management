package org.woridge.ms_internal_users_management.utils;

import java.security.SecureRandom;

public final class PasswordGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "@/.$^-_&?!,.#%";

    private static final String ALL = UPPER + LOWER + DIGITS + SPECIAL;

    private PasswordGenerator() {
        throw new UnsupportedOperationException("You can't instantiate Password Generator utils object");
    }

    public static String generate() {

        StringBuilder sb = new StringBuilder();

        // garantir chaque type
        sb.append(UPPER.charAt(RANDOM.nextInt(UPPER.length())));
        sb.append(LOWER.charAt(RANDOM.nextInt(LOWER.length())));
        sb.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        sb.append(SPECIAL.charAt(RANDOM.nextInt(SPECIAL.length())));

        // compléter jusqu'à 10 caractères
        for (int i = 4; i < 10; i++) {
            sb.append(ALL.charAt(RANDOM.nextInt(ALL.length())));
        }

        return sb.toString();
    }
}