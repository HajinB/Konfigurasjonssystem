package org.programutvikling.domain.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserValidatorTest {
    @Test
    void username() {
        // Accepts everything except spaces. Hard to see the difference between a user named "user" and "user ", no reason to accept it.
        assertTrue(UserValidator.username("12345Roger1236!"));

        assertFalse(UserValidator.username("TEst test test"));
        assertFalse(UserValidator.username("546787654 hdwfi qwe.fhieq 7"));
        assertFalse(UserValidator.username("                                        "));
    }

    @Test
    void password() {
        assertTrue(UserValidator.password("123456789ASDFGHJK456789"));
        assertTrue(UserValidator.password("dfghjklkjhgfrtyuio"));
        assertTrue(UserValidator.password("ååååååååææææøøø"));
        assertTrue(UserValidator.password("fregstrhwrtghwrghrtgtre"));
        assertTrue(UserValidator.password("                       "));

        assertFalse(UserValidator.password(""));
        assertFalse(UserValidator.password("135"));
        assertFalse(UserValidator.password("asg"));
        assertFalse(UserValidator.password("SC"));
        assertFalse(UserValidator.password(""));
    }

    @Test
    void name() {
        assertTrue(UserValidator.name("TEst test test"));
        assertTrue(UserValidator.name("Chamo'n Aqua, v/ www.AquaService90.no/"));

        assertFalse(UserValidator.name(""));
        assertFalse(UserValidator.name("                    "));
    }
    @Test
    void address() {
        assertTrue(UserValidator.address("Carl Dons Veg"));
        assertTrue(UserValidator.address("Postboks C/O 924-A, Strømøy Postboxfirma"));

        assertFalse(UserValidator.address(""));
        assertFalse(UserValidator.address("            "));
        assertFalse(UserValidator.address("                                        "));
    }
    @Test
    void email() {
        assertTrue(UserValidator.email("ola@nordmann.no"));
        assertTrue(UserValidator.email("a@aa.no"));
        assertTrue(UserValidator.email("1@12.co.uk"));

        assertFalse(UserValidator.email("a@"));
    }

    @Test
    void zip() {
        assertTrue(UserValidator.zip("0864"));
        assertTrue(UserValidator.zip("0001"));

        assertFalse(UserValidator.zip("0"));
        assertFalse(UserValidator.zip("08"));
        assertFalse(UserValidator.zip("086"));
        assertFalse(UserValidator.zip("0000"));
        assertFalse(UserValidator.zip(""));
        assertFalse(UserValidator.zip("    "));
        assertFalse(UserValidator.zip("____"));
        assertFalse(UserValidator.zip("abcd"));
    }

    @Test
    void city() {
        assertTrue(UserValidator.city("Trondheim"));
        assertTrue(UserValidator.city("Oslo"));
        assertTrue(UserValidator.city("Ås"));

        assertFalse(UserValidator.city(""));
        assertFalse(UserValidator.city("                    "));
    }
}