package org.programutvikling.user;

import org.junit.jupiter.api.Test;
import org.programutvikling.domain.user.UserValidator;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
    @Test
    void username() {
        assertTrue(UserValidator.username("12345Roger1236!"));
        assertFalse(UserValidator.username("TEst test test"));
        assertFalse(UserValidator.username("546787654 hdwfi qwe.fhieq 7"));
    }

    @Test
    void password() {
        assertTrue(UserValidator.password("123456789ASDFGHJK456789"));
        assertTrue(UserValidator.password("dfghjklkjhgfrtyuio"));
        assertTrue(UserValidator.password("ååååååååææææøøø"));
        assertTrue(UserValidator.password("fregstrhwrtghwrghrtgtre"));
        assertFalse(UserValidator.password(""));
        assertFalse(UserValidator.password("12335"));
        assertFalse(UserValidator.password("asdfg"));
        assertFalse(UserValidator.password("SELEC"));
    }

    @Test
    void name() {
        assertTrue(UserValidator.name("TEst test test"));
        assertFalse(UserValidator.name("546787654 hdwfi qwe.fhieq 7"));
    }

    @Test
    void email() {
    }


    @Test
    void zip() {
    }

    @Test
    void city() {
    }
}