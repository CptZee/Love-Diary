package com.github.cptzee.lovediary.Data;

public class User {
    private String uuid;
    private String email;
    private String username;
    private String code;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getCode() {
        return code;
    }
}
