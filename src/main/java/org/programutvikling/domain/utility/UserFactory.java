package org.programutvikling.domain.utility;

import org.programutvikling.domain.user.User;

public class UserFactory {
    public User createUser(boolean admin, String username, String password, String name, String email, String address,
                           String zip, String city) {
        return new User(admin, username, password, name, email, address, zip, city);
    }
}
