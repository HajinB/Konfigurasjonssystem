package org.programutvikling.domain.user;

public class UserValidator {
    // ingen tall
    public static final String NO_NUMBERS = "^[\\D]+$";
    public static final String ONLY_NUMBERS = "^[\\d]+$";
    public static final String NO_SPACES = "^[\\S]+$";
    public static final int PASSWORD_LENGTH = 4;
    public static final int ZIP_LENGTH = 4;
    public static final String EMAIL_VERIFICATION = "^[a-zæøåA-ZÆØÅ0-9]+(?:[_.-][a-zæøåA-ZÆØÅ0-9]+)*@[a-zæøåA-ZÆØÅ0-9]+(?:[_.-][a-zæøåA-ZÆØÅ0-9]+)*\\.[a-zæøåA-ZÆØÅ]{2,}$";

    public static boolean username(String username) {
        return !username.isBlank() && username.matches(NO_SPACES);
    }
    public static boolean password(String password) {
        return password.length() >= PASSWORD_LENGTH;
    }
    public static boolean name(String name) {
        return name.matches(NO_NUMBERS) && !name.isBlank();
    }
    static boolean email(String email) {
        return email.matches(EMAIL_VERIFICATION);
    }
    static boolean zip(String zip) {
        return zip.matches(ONLY_NUMBERS) && zip.length() == ZIP_LENGTH;
    }
    static boolean city(String city) {
        return city.matches(NO_NUMBERS);
    }
}


