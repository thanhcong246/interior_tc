package com.vn.tcshop.foodapp.Models.Responses;

public class RecoverCodeToPesponse {
    private String email;
    private String login_info;
    private String error_recover_email_code_to;

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

    public String getError_recover_email_code_to() {
        return error_recover_email_code_to;
    }

    public void setError_recover_email_code_to(String error_recover_email_code_to) {
        this.error_recover_email_code_to = error_recover_email_code_to;
    }
}
