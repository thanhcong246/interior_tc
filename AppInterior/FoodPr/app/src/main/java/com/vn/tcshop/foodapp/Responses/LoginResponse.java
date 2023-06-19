package com.vn.tcshop.foodapp.Responses;

public class LoginResponse {
    private String name;
    private String email;
    private String password;

    private String error_login;

    public String getError_login() {
        return error_login;
    }

    public void setError_login(String error_login) {
        this.error_login = error_login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
