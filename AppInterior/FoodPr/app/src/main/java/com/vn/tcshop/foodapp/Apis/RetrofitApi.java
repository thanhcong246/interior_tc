package com.vn.tcshop.foodapp.Apis;

import com.vn.tcshop.foodapp.Models.Product;
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
}
