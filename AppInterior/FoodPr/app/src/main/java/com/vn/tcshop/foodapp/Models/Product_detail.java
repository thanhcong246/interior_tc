package com.vn.tcshop.foodapp.Models;

public class Product_detail {
    private int product_id;
    private String name;
    private int price;
    private int product_detail_id;
    private String img_url_one, img_url_two,img_url_three,img_url_four;

    public Product_detail(){}

    public Product_detail(int product_id, String name, int price, int product_detail_id, String img_url_one, String img_url_two, String img_url_three, String img_url_four) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.product_detail_id = product_detail_id;
        this.img_url_one = img_url_one;
        this.img_url_two = img_url_two;
        this.img_url_three = img_url_three;
        this.img_url_four = img_url_four;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProduct_detail_id() {
        return product_detail_id;
    }

    public void setProduct_detail_id(int product_detail_id) {
        this.product_detail_id = product_detail_id;
    }

    public String getImg_url_one() {
        return img_url_one;
    }

    public void setImg_url_one(String img_url_one) {
        this.img_url_one = img_url_one;
    }

    public String getImg_url_two() {
        return img_url_two;
    }

    public void setImg_url_two(String img_url_two) {
        this.img_url_two = img_url_two;
    }

    public String getImg_url_three() {
        return img_url_three;
    }

    public void setImg_url_three(String img_url_three) {
        this.img_url_three = img_url_three;
    }

    public String getImg_url_four() {
        return img_url_four;
    }

    public void setImg_url_four(String img_url_four) {
        this.img_url_four = img_url_four;
    }
}
