package org.programutvikling.domain.user;

import org.junit.jupiter.api.Test;
import org.programutvikling.model.ModelUserRegister;

import static org.junit.jupiter.api.Assertions.*;

class UserRegisterTest {

    @Test
    void loginCredentialsMatches() {
        assertNotNull(ModelUserRegister.INSTANCE.getUserRegister().loginCredentialsMatches("admin","adminadmin"));
    }

    @Test
    void isAdmin() {
        assertTrue(ModelUserRegister.INSTANCE.getUserRegister().isAdmin(ModelUserRegister.INSTANCE.getUserRegister().loginCredentialsMatches("admin","adminadmin")));
    }

    @Test
    void usernameExists() {
    }
    @Test
    void systemOut() {
        System.out.println(ModelUserRegister.INSTANCE.getUserRegister());
    }
}