package com.vn.tcshop.foodapp.Models;

public class Cart {
    private int product_id;
    private String email;
    private int price;
    private String cart_name;
    private String cart_img;
    private int quantity_product_id;
    private int sum_product_id_price;
    private String error_get_cart;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCart_name() {
        return cart_name;
    }

    public void setCart_name(String cart_name) {
        this.cart_name = cart_name;
    }

    public String getCart_img() {
        return cart_img;
    }

    public void setCart_img(String cart_img) {
        this.cart_img = cart_img;
    }

    public int getQuantity_product_id() {
        return quantity_product_id;
    }

    public void setQuantity_product_id(int quantity_product_id) {
        this.quantity_product_id = quantity_product_id;
    }

    public int getSum_product_id_price() {
        return sum_product_id_price;
    }

    public void setSum_product_id_price(int sum_product_id_price) {
        this.sum_product_id_price = sum_product_id_price;
    }

    public String getError_get_cart() {
        return error_get_cart;
    }

    public void setError_get_cart(String error_get_cart) {
        this.error_get_cart = error_get_cart;
    }
}
