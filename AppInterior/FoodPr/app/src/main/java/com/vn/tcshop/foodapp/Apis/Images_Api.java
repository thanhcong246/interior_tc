package com.vn.tcshop.foodapp.Apis;

public class Images_Api {
    private static final String BASE_URL = "http://192.168.1.2:86/interior_app/";

    public static String getImageUrl(String imageName) {
        String imagePath = "uploads/" + imageName;
        return BASE_URL + imagePath;
    }

    public static String getImageProductDetailUrl(String imageName){
        String imagePath = "img_product_details/" + imageName;
        return BASE_URL + imagePath;
    }
}
