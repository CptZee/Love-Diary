package com.github.cptzee.lovediary.Manager;

import com.github.cptzee.lovediary.Data.User.User;

public class SessionManager {
    private User currentUser;
    private SessionManager(){
        //Required constructor to prevent the direct instantiation of the class
    }
    private static SessionManager instance;
    public static SessionManager getInstance(){
        if(instance == null)
            instance = new SessionManager();
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
