package com.vn.tcshop.foodapp.Models.Responses;

public class RecoverCodePesponse {
    private String email;
    private String login_info;
    private String error_recover_email_code;

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

    public String getError_recover_email_code() {
        return error_recover_email_code;
    }

    public void setError_recover_email_code(String error_recover_email_code) {
        this.error_recover_email_code = error_recover_email_code;
    }
}
