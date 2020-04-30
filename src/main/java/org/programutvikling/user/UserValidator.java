package org.programutvikling.user;

public class UserValidator {
    // ingen tall
    public static final String NO_NUMBERS = "^[\\D]+$";
    public static final String ONLY_NUMBERS = "^[\\d]+$";
    public static final String NO_SPACES = "^[\\S]+$";
    public static final int PASSWORD_LENGTH = 4;
    public static final int ZIP_LENGTH = 4;
    public static final String EMAIL_VERIFICATION = "^[a-zæøåA-ZÆØÅ0-9]+(?:[_.-][a-zæøåA-ZÆØÅ0-9]+)*@[a-zæøåA-ZÆØÅ0-9]+(?:[_.-][a-zæøåA-ZÆØÅ0-9]+)*\\.[a-zæøåA-ZÆØÅ]{2,3}$";
    private static final String NORSK_PHONE_VERIFICATION = "^((0047)?|(\\+47)?|(47)?)\\d{8}$";

    static boolean username(String username) {
        return !username.isBlank() && username.matches(NO_SPACES);
    }
    static boolean password(String password) {
        return password.length() >= PASSWORD_LENGTH;
    }
    static boolean name(String name) {
        return name.matches(NO_NUMBERS) && !name.isBlank();
    }
    static boolean email(String email) {
        return email.matches(EMAIL_VERIFICATION);
    }
    static boolean phone(String phone) {
        return phone.matches(NORSK_PHONE_VERIFICATION);
    }
    static boolean zip(String zip) {
        return zip.matches(ONLY_NUMBERS) && zip.length() == ZIP_LENGTH;
    }
    static boolean city(String city) {
        return city.matches(NO_NUMBERS);
    }
}


