package com.vn.tcshop.foodapp.Apis;

import com.vn.tcshop.foodapp.Models.Product;
import com.vn.tcshop.foodapp.Models.ProductReviewRatingSumOverAll;
import com.vn.tcshop.foodapp.Models.ProductReviews;
import com.vn.tcshop.foodapp.Models.Product_detail;
import com.vn.tcshop.foodapp.Models.Product_detail_desc;
import com.vn.tcshop.foodapp.Models.Product_detail_reviews;
import com.vn.tcshop.foodapp.Models.Product_detail_specifications;
import com.vn.tcshop.foodapp.Responses.LoginResponse;
import com.vn.tcshop.foodapp.Responses.RecoverCodePesponse;
import com.vn.tcshop.foodapp.Responses.RecoverCodeToPesponse;
import com.vn.tcshop.foodapp.Responses.RecoverEmailResponse;
import com.vn.tcshop.foodapp.Responses.RecoverPesponse;
import com.vn.tcshop.foodapp.Responses.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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

    @GET("get_all_product")
    Call<List<Product>> get_all_product();

    @FormUrlEncoded
    @POST("get_product_by_id")
    Call<List<Product_detail>> get_product_by_id(
            @Field("product_id") int product_id
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
}
