package com.vn.tcshop.foodapp.Models;

public class Product_detail {
    private int product_id;
    private String name;
    private int old_price;
    private String image_url;
    private int discount;
    private int price;
    private int product_detail_id;
    private String img_url_one, img_url_two, img_url_three, img_url_four;

    public Product_detail() {
    }

    public Product_detail(int product_id, String name, int old_price, String image_url, int discount, int price, int product_detail_id, String img_url_one, String img_url_two, String img_url_three, String img_url_four) {
        this.product_id = product_id;
        this.name = name;
        this.old_price = old_price;
        this.image_url = image_url;
        this.discount = discount;
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

    public int getOld_price() {
        return old_price;
    }

    public void setOld_price(int old_price) {
        this.old_price = old_price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
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
