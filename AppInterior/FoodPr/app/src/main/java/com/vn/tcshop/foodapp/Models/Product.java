package com.vn.tcshop.foodapp.Models;

public class Product {
    private int product_id;
    private String name;
    private String image_url;
    private int price;

    public Product(int product_id, String name, String image_url, int price) {
        this.product_id = product_id;
        this.name = name;
        this.image_url = image_url;
        this.price = price;
    }

    public Product() {
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image_url;
    }

    public void setImage(String image_url) {
        this.image_url = image_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
