package com.vn.tcshop.foodapp.Models;

import com.google.gson.annotations.SerializedName;

public class Ward {
    @SerializedName("name")
    private String name;

    @SerializedName("code")
    private int code;

    @SerializedName("division_type")
    private String divisionType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDivisionType() {
        return divisionType;
    }

    public void setDivisionType(String divisionType) {
        this.divisionType = divisionType;
    }

    public Ward() {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
