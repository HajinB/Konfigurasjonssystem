package org.programutvikling.domain.user;

import javafx.collections.ObservableList;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.model.Model;

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
        return !username.isBlank() || !username.isEmpty() && username.matches(NO_SPACES);
    }
    public static boolean password(String password) {
        return password.length() >= PASSWORD_LENGTH;
    }
    /** Accepts everything except a blank name.
     * "Tom W. Hap'l, v/ Host243.no" or even the real name "X Æ A-12".
     * */
    public static boolean name(String name) {
        return !name.isBlank() || !name.isEmpty();
    }
    static boolean email(String email) {
        return email.matches(EMAIL_VERIFICATION);
    }
    public static boolean address(String address) {
        return !address.isBlank() || !address.isEmpty();
    }
    static boolean zip(String zip) {
        return zip.matches(ONLY_NUMBERS) && zip.length() == ZIP_LENGTH;
    }
    public static boolean city(String city) {
        return !city.isBlank() || !city.isEmpty();
    }

    public static boolean isUserValid(String username, String password, String name, String email,
                                      String address, String zip, String city) {
        return username(username) && password(password) && name(name) && email(email) && address(address) && zip(zip) && city(city);
    }

    public static User returnUserIfAlreadyExists(User user, UserRegister register){
            for(User u: register.getRegister()){
                if(isPrimaryKeyAMatch(u, user)){
                    return u;
                }
            }
            return null;
        }

    private static boolean isPrimaryKeyAMatch(User u1, User u2) {
            return u2.getName().equals(u1.getName())
                    && u2.getUsername().equals(u1.getUsername())
                    && u2.getEmail().equals(u1.getEmail());
        }
    }

//    static void usernameValidation(String username) {
//        if(Model.INSTANCE.getUserRegister().usernameExists(username) && Model.INSTANCE.getUserRegister().getRegister() != null) {
//            System.out.println("UsernameAlreadyExistsException thrown!");
//            throw new UsernameAlreadyExistsException();
//        } else {
//            System.out.println("UsernameAlreadyExistsException NOT thrown, Model.INSTANCE.getUserRegister().usernameExists(username) = " + Model.INSTANCE.getUserRegister().usernameExists(username));
//        }
//    }
//
//    static public void emailValidation(String email) {
//        if(Model.INSTANCE.getUserRegister().emailExists(email) && Model.INSTANCE.getUserRegister().getRegister() != null) {
//            System.out.println("EmailExistsException thrown!");
//            throw new EmailExistsException();
//        } else {
//            System.out.println("EmailExistsException NOT thrown, Model.INSTANCE.getUserRegister().emailExists(email) = " + Model.INSTANCE.getUserRegister().emailExists(email));
//        }
//    }



