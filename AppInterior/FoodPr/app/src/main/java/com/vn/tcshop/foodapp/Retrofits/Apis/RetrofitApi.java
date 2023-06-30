package com.vn.tcshop.foodapp.Retrofits.Apis;

import com.vn.tcshop.foodapp.Models.Cart;
import com.vn.tcshop.foodapp.Models.CartByPayment;
import com.vn.tcshop.foodapp.Models.Category;
import com.vn.tcshop.foodapp.Models.City;
import com.vn.tcshop.foodapp.Models.District;
import com.vn.tcshop.foodapp.Models.Payments;
import com.vn.tcshop.foodapp.Models.Product;
import com.vn.tcshop.foodapp.Models.ProductReviewRatingSumOverAll;
import com.vn.tcshop.foodapp.Models.ProductReviews;
import com.vn.tcshop.foodapp.Models.Product_detail;
import com.vn.tcshop.foodapp.Models.Product_detail_desc;
import com.vn.tcshop.foodapp.Models.Product_detail_reviews;
import com.vn.tcshop.foodapp.Models.Product_detail_specifications;
import com.vn.tcshop.foodapp.Models.Province;
import com.vn.tcshop.foodapp.Models.Ward;
import com.vn.tcshop.foodapp.Models.Responses.CartByIdResponse;
import com.vn.tcshop.foodapp.Models.Responses.CartDeleteAllResponse;
import com.vn.tcshop.foodapp.Models.Responses.CartRemoveProductId;
import com.vn.tcshop.foodapp.Models.Responses.CartRemoveProductIdAll;
import com.vn.tcshop.foodapp.Models.Responses.LoginResponse;
import com.vn.tcshop.foodapp.Models.Responses.RecoverCodePesponse;
import com.vn.tcshop.foodapp.Models.Responses.RecoverCodeToPesponse;
import com.vn.tcshop.foodapp.Models.Responses.RecoverEmailResponse;
import com.vn.tcshop.foodapp.Models.Responses.RecoverPesponse;
import com.vn.tcshop.foodapp.Models.Responses.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitApi {
    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("recover_send_mail")
    Call<RecoverEmailResponse> recoverEmail(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("recover_send_mail_code")
    Call<RecoverCodePesponse> recoverCode(
            @Field("email") String email,
            @Field("login_info") String login_info
    );

    @FormUrlEncoded
    @POST("recover_send_mail_code_to")
    Call<RecoverCodeToPesponse> recoverCodeTo(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("recover")
    Call<RecoverPesponse> recover(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("search_products")
    Call<List<Product>> search_products(
            @Field("search_product") String search_product
    );

    @GET("get_all_product")
    Call<List<Product>> get_all_product();

    @GET("get_all_category")
    Call<List<Category>> get_all_category();

    @FormUrlEncoded
    @POST("get_product_by_id")
    Call<List<Product_detail>> get_product_by_id(
            @Field("product_id") int product_id
    );

    @FormUrlEncoded
    @POST("get_all_product_by_category_id")
    Call<List<Product>> get_all_product_by_category_id(
            @Field("category_id") int category_id
    );

    @FormUrlEncoded
    @POST("get_product_by_id_description")
    Call<List<Product_detail_desc>> get_product_by_id_description(
            @Field("product_id") int product_id
    );

    @FormUrlEncoded
    @POST("get_product_by_id_specification")
    Call<List<Product_detail_specifications>> get_product_by_id_specification(
            @Field("product_id") int product_id
    );

    @FormUrlEncoded
    @POST("add_product_detail_reviews")
    Call<Product_detail_reviews> add_product_detail_reviews(
            @Field("product_id") int product_id,
            @Field("content") String content,
            @Field("rating") int rating,
            @Field("user_name") String user_name
    );

    @FormUrlEncoded
    @POST("get_product_by_id_reviews")
    Call<List<ProductReviews>> get_product_by_id_reviews(
            @Field("product_id") int product_id
    );

    @FormUrlEncoded
    @POST("get_product_by_id_review_rating_sum")
    Call<ProductReviewRatingSumOverAll> get_product_by_id_review_rating_sum(
            @Field("product_id") int product_id
    );

    @FormUrlEncoded
    @POST("add_cart_by_id")
    Call<CartByIdResponse> add_cart_by_id(
            @Field("product_id") int product_id,
            @Field("email") String email,
            @Field("price") int price,
            @Field("cart_name") String cart_name,
            @Field("cart_img") String cart_img
    );

    @FormUrlEncoded
    @POST("add_cart_payment_by_id")
    Call<CartByIdResponse> add_cart_payment_by_id(
            @Field("product_id") int product_id,
            @Field("email") String email,
            @Field("price") int price,
            @Field("cart_name") String cart_name,
            @Field("cart_img") String cart_img
    );

    @FormUrlEncoded
    @POST("get_cart_by_id")
    Call<List<Cart>> get_cart_by_id(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("get_cart_payment_by_id")
    Call<List<Cart>> get_cart_payment_by_id(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("total_price_and_quantity_cart_by_id")
    Call<CartByPayment> total_price_and_quantity_cart_by_id(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("total_price_and_quantity_cart_payment_by_id")
    Call<CartByPayment> total_price_and_quantity_cart_payment_by_id(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("delete_all_cart")
    Call<CartDeleteAllResponse> delete_all_cart(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("delete_all_cart_payment")
    Call<CartDeleteAllResponse> delete_all_cart_payment(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("delete_cart_by_id_all")
    Call<CartRemoveProductIdAll> delete_cart_by_id_all(
            @Field("email") String email,
            @Field("product_id") int product_id
    );

    @FormUrlEncoded
    @POST("delete_cart_payment_by_id_all")
    Call<CartRemoveProductIdAll> delete_cart_payment_by_id_all(
            @Field("email") String email,
            @Field("product_id") int product_id
    );

    @FormUrlEncoded
    @POST("delete_cart_by_id")
    Call<CartRemoveProductId> delete_cart_by_id(
            @Field("email") String email,
            @Field("product_id") int product_id
    );

    @FormUrlEncoded
    @POST("delete_cart_payment_by_id")
    Call<CartRemoveProductId> delete_cart_payment_by_id(
            @Field("email") String email,
            @Field("product_id") int product_id
    );

    //tỉnh thành
    @GET("p/")
    Call<List<Province>> getProvinces();

    @GET("p/{provinceCode}?depth=2")
    Call<City> getDistricts(@Path("provinceCode") int provinceCode);

    @GET("d/{districtCode}?depth=2")
    Call<District> getWards(@Path("districtCode") int districtCode);
    //

    @FormUrlEncoded
    @POST("add_payments")
    Call<Payments> add_payments(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("apartment_number") String apartment_number,
            @Field("ward") String ward,
            @Field("district") String district,
            @Field("province") String province,
            @Field("note") String note,
            @Field("quantityProduct") int quantityProduct,
            @Field("total") int total
    );
}
