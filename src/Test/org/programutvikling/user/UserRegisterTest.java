package org.programutvikling.user;

import org.junit.jupiter.api.Test;
import org.programutvikling.gui.ContextModel;
import org.programutvikling.user.*;
import static org.junit.jupiter.api.Assertions.*;

class UserRegisterTest {

    @Test
    void loginCredentialsMatches() {
        assertNotNull(ContextModel.INSTANCE.getUserRegister().loginCredentialsMatches("admin","adminadmin"));

    }

    @Test
    void isAdmin() {
        assertTrue(ContextModel.INSTANCE.getUserRegister().isAdmin(ContextModel.INSTANCE.getUserRegister().loginCredentialsMatches("admin","adminadmin")));

    }

    @Test
    void usernameExists() {
    }
    @Test
    void systemOut() {
        System.out.println(ContextModel.INSTANCE.getUserRegister());
    }
}