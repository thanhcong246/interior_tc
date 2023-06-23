package com.vn.tcshop.foodapp.Models;

public class ProductReviews {
    private String content;
    private int rating;
    private String user_name;
    private String comment_date;

    public ProductReviews(String content, int rating, String user_name, String comment_date) {
        this.content = content;
        this.rating = rating;
        this.user_name = user_name;
        this.comment_date = comment_date;
    }

    public ProductReviews(){}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }
}
