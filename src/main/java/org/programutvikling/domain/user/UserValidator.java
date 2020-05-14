package org.programutvikling.domain.user;

public class UserValidator {
    // ingen tall
    public static final String NO_NUMBERS = "^[\\D]+$";
    public static final String ONLY_NUMBERS = "^[\\d]+$";
    public static final String NO_SPACES = "^[\\S]+$";
    public static final int PASSWORD_LENGTH = 4;
    public static final int ZIP_LENGTH = 4;
    public static final String EMAIL_VERIFICATION = "^[a-zæøåA-ZÆØÅ0-9]+(?:[_.-][a-zæøåA-ZÆØÅ0-9]+)*@[a-zæøåA-ZÆØÅ0-9]+(?:[_.-][a-zæøåA-ZÆØÅ0-9]+)*\\.[a-zæøåA-ZÆØÅ]{2,}$";

    /** No reason to accept spaces. Hard to see the difference between "user" and "user ".
     */
    public static boolean username(String username) {
        return !username.isBlank() && username.matches(NO_SPACES);
    }
    public static boolean password(String password) {
        return password.length() >= PASSWORD_LENGTH;
    }
    /** Accepts everything except a blank name.
     * "Tom W. Hap'l, v/ Host243.no" or even the real name "X Æ A-12".
     * */
    public static boolean name(String name) {
        return !name.isBlank();
    }
    public static boolean email(String email) {
        return email.matches(EMAIL_VERIFICATION);
    }
    public static boolean address(String address) {
        return !address.isBlank();
    }
    public static boolean zip(String zip) {
        return zip.matches(ONLY_NUMBERS) && zip.length() == ZIP_LENGTH
                && !zip.matches("0000");
    }
    public static boolean city(String city) {
        return !city.isBlank();
    }

    public static boolean isUserValid(String username, String password, String name, String email,
                                      String address, String zip, String city) {
        return username(username) && password(password) && name(name) && email(email) && address(address) && zip(zip) && city(city);
    }

    private static boolean isPrimaryKeyAMatch(User u1, User u2) {
            return u2.getName().equals(u1.getName())
                    && u2.getUsername().equals(u1.getUsername())
                    && u2.getEmail().equals(u1.getEmail());
        }
    }



