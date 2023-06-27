package com.vn.tcshop.foodapp.Models.Responses;

public class RecoverEmailResponse {
    private String email;
    private String login_info;
    private String error_recover_email;

    public String getError_recover_email() {
        return error_recover_email;
    }

    public void setError_recover_email(String error_recover_email) {
        this.error_recover_email = error_recover_email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin_info() {
        return login_info;
    }

    public void setLogin_info(String login_info) {
        this.login_info = login_info;
    }
}
