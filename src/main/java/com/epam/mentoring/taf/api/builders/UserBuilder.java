package com.epam.mentoring.taf.api.builders;

import com.epam.mentoring.taf.api.models.User;

public class UserBuilder {

    private String email;
    private String username;
    private String password;

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withUserName(String userName) {
        this.username = userName;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public User build(){
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
