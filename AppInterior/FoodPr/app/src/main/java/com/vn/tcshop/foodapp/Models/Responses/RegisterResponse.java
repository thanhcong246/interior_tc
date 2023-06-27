package com.vn.tcshop.foodapp.Models.Responses;

public class RegisterResponse {
    private String errorcode;
    private String message;

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
