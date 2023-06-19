package com.vn.tcshop.foodapp.Models;

import android.graphics.drawable.Drawable;

public class MenuItemModel {
    private int id;
    private String title;
    private Drawable icon;

    public MenuItemModel(int id, String title, Drawable icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getIcon() {
        return icon;
    }
}
