package org.programutvikling.user;

public class UserValidator {
    // ingen tall
    public static final int PASSWORD_LENGTH = 6;

    static boolean username(String username) {
        return (!username.isBlank());
    }
    static boolean password(String password) {
        return password.length() > PASSWORD_LENGTH;
    }
}


