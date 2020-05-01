package org.programutvikling.user;

import org.junit.jupiter.api.Test;
import org.programutvikling.model.Model;

import static org.junit.jupiter.api.Assertions.*;

class UserRegisterTest {

    @Test
    void loginCredentialsMatches() {
        assertNotNull(Model.INSTANCE.getUserRegister().loginCredentialsMatches("admin","adminadmin"));

    }

    @Test
    void isAdmin() {
        assertTrue(Model.INSTANCE.getUserRegister().isAdmin(Model.INSTANCE.getUserRegister().loginCredentialsMatches("admin","adminadmin")));

    }

    @Test
    void usernameExists() {
    }
    @Test
    void systemOut() {
        System.out.println(Model.INSTANCE.getUserRegister());
    }
}