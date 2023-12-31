package com.plannerapp.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class LoggedUser {

    private long id;

    private String username;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void logIn(long id,String username){
        this.id = id;
        this.username = username;
    }
    public void logOut(){
        this.id = 0;
        this.username = null;
    }
}
