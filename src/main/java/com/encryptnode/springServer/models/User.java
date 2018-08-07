package com.encryptnode.springServer.models;

import com.encryptnode.springServer.pojos.UserPojo;
import org.mindrot.jbcrypt.BCrypt;

public class User {
    private static final String DEFAULT_NAME = "No Name";
    private static final String DEFAULT_PASS = "password";

    public int user_id;
    public String username;
    private String password;

    public User() {
        this(DEFAULT_NAME, DEFAULT_PASS);
    }

//    public User(String username, String password) {
//        this(username, password);
//    }

    public User(UserPojo user) {
        this(user.username, user.password);
    }

    public User(String username, String password) {
        this(-1, username, password);
    }

    public User(int user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }

    public boolean checkPassword(String attempt) {
        boolean result = BCrypt.checkpw(attempt, this.password);
        return result;
    }
}
