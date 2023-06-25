package com.vn.tcshop.foodapp.Products;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.vn.tcshop.foodapp.Adapters.Products.SliderAdapter;
import com.vn.tcshop.foodapp.Apis.Images_Api;
import com.vn.tcshop.foodapp.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Configs.Constant;
import com.vn.tcshop.foodapp.Fragments.DescriptionFragment;
import com.vn.tcshop.foodapp.Fragments.ReviewFragment;
import com.vn.tcshop.foodapp.Fragments.SpecificationFragment;
import com.vn.tcshop.foodapp.Models.CartByPayment;
import com.vn.tcshop.foodapp.Models.ProductReviewRatingSumOverAll;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Responses.CartByIdResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private SliderAdapter sliderAdapter;
    private LinearLayout indicatorLayout;
    private List<ImageView> indicators = new ArrayList<>();
    private int currentPage = 0;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private TextView product_detail_descripton, product_detail_specification, product_detail_review;
    private TextView product_detail_id, product_id, product_detail_name, product_detail_price;
    private TextView product_detail_old_price, product_detail_discount;
    private String[] imageUrls = {};
    private ImageView close_product_detail;
    private Constant constant = new Constant();
    private TextView reviews_product_detail;
    private ImageView star1, star2, star3, star4, star5;
    private ImageView btn_add_cart;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "login_prefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_REMEMBER_ME = "remember_me";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        product_detail_descripton = findViewById(R.id.product_detail_description);
        product_detail_specification = findViewById(R.id.product_detail_specifications);
        product_detail_review = findViewById(R.id.product_detail_reviews);
        product_id = findViewById(R.id.product_id);
        product_detail_name = findViewById(R.id.product_detail_name);
        product_detail_id = findViewById(R.id.product_detail_id);
        product_detail_price = findViewById(R.id.product_detail_price);
        product_detail_discount = findViewById(R.id.product_detail_discount);
        product_detail_old_price = findViewById(R.id.product_detail_old_price);
        reviews_product_detail = findViewById(R.id.reviews_product_detail);
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);
        close_product_detail = findViewById(R.id.close_product_detail);
        btn_add_cart = findViewById(R.id.btn_add_cart);

        // hiển thị fragment mặc định
        int productId_dt = getIntent().getIntExtra("productId", 0);
        DescriptionFragment descriptionFragment = new DescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("productId", productId_dt);
        descriptionFragment.setArguments(bundle);
        // Hiển thị Fragment mặc định và truyền dữ liệu
        displayFragment(descriptionFragment);
        product_detail_descripton.setTextColor(Color.parseColor("#000000"));

        onClickButtonDescripton();
        onClickButtonSpecification();
        onClickButtonReview();
        get_product_detail();
        close_product_detail_btn();


    }

    private void close_product_detail_btn() {
        close_product_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetailActivity.this, ProductActivity.class));
                finish();
            }
        });
    }

    private void get_product_detail() {

        int productId_dt = getIntent().getIntExtra("productId", 0);
        int product_detail_id_dt = getIntent().getIntExtra("product_detail_id", 0);
        String product_name_dt = getIntent().getStringExtra("product_name");
        String product_detail_price_dt = getIntent().getStringExtra("product_detail_price");
        int product_detail_discount_dt = getIntent().getIntExtra("product_detail_discount", 0);
        String product_detail_old_price_dt = getIntent().getStringExtra("product_detail_old_price");
        String image_url_dt = getIntent().getStringExtra("image_url");
        String img_url_one_dt = getIntent().getStringExtra("img_url_one");
        String img_url_two_dt = getIntent().getStringExtra("img_url_two");
        String img_url_three_dt = getIntent().getStringExtra("img_url_three");
        String img_url_four_dt = getIntent().getStringExtra("img_url_four");
        String img_one = Images_Api.getImageProductDetailUrl(img_url_one_dt);
        String img_two = Images_Api.getImageProductDetailUrl(img_url_two_dt);
        String img_three = Images_Api.getImageProductDetailUrl(img_url_three_dt);
        String img_four = Images_Api.getImageProductDetailUrl(img_url_four_dt);

        imageUrls = new String[]{img_one, img_two, img_three, img_four};

        product_id.setText(String.valueOf(productId_dt));
        product_detail_id.setText(String.valueOf(product_detail_id_dt));
        product_detail_name.setText(product_name_dt);
        product_detail_price.setText(product_detail_price_dt + "đ");
        product_detail_discount.setText(product_detail_discount_dt + "%");

        String currencySymbol = "đ";
        String text = product_detail_old_price_dt + currencySymbol;

        SpannableString spannableString = new SpannableString(text);
        int end = text.length() - currencySymbol.length();
        spannableString.setSpan(new StrikethroughSpan(), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        product_detail_old_price.setText(spannableString);

        getProductReview(productId_dt);

        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)) {
            String saveEmail = sharedPreferences.getString(KEY_EMAIL, "");
            addCartById(productId_dt, saveEmail, product_detail_price_dt, product_name_dt, image_url_dt);
        }

        // ----------------
        viewPager = findViewById(R.id.viewPager);
        indicatorLayout = findViewById(R.id.indicatorLayout);

        // Khởi tạo và thiết lập adapter cho ViewPager2
        sliderAdapter = new SliderAdapter(Arrays.asList(imageUrls));
        viewPager.setAdapter(sliderAdapter);

        createIndicators();
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                updateIndicators();
            }
        });

        // Chạy tự động chuyển ảnh
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == imageUrls.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 6000);
            }
        };
        handler.postDelayed(runnable, 3000);

        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reverseSlider();
            }
        });

        // -----------
    }

    private void addCartById(int productId_dt, String saveEmail, String product_detail_price_dt_cart, String product_name_dt, String image_url_dt) {
        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cleanInput = product_detail_price_dt_cart.replaceAll(",", "");
                int price_cart = Integer.parseInt(cleanInput);
                RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                Call<CartByIdResponse> call = retrofitApi.add_cart_by_id(productId_dt, saveEmail, price_cart, product_name_dt, image_url_dt);
                call.enqueue(new Callback<CartByIdResponse>() {
                    @Override
                    public void onResponse(Call<CartByIdResponse> call, Response<CartByIdResponse> response) {
                        if (response.isSuccessful()) {
                            CartByIdResponse cartByIdResponse = response.body();
                            String error_cart_add = cartByIdResponse.getError_cart_add();
                            if (Objects.equals(error_cart_add, "000")) {
                                Toast.makeText(ProductDetailActivity.this, "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                                getTotalCartPayment(saveEmail);
                            }
                            if (Objects.equals(error_cart_add, "111")) {
                                Log.e("error_cart_add", "111");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartByIdResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void getTotalCartPayment(String savedEmail) {
        SharedPreferences saveQuantityProduct = getSharedPreferences("notification_quantity_product", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = saveQuantityProduct.edit();
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<CartByPayment> call = retrofitApi.total_price_and_quantity_cart_by_id(savedEmail);
        call.enqueue(new Callback<CartByPayment>() {
            @Override
            public void onResponse(Call<CartByPayment> call, Response<CartByPayment> response) {
                if (response.isSuccessful()) {
                    CartByPayment cartByPayment = response.body();
                    int total_quantity = cartByPayment.getTotal_quantity();
                    // lưu thông tin thông báo product
                    editor.putInt("total_quantity_product", total_quantity);
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<CartByPayment> call, Throwable t) {

            }
        });
    }

    // quay lại trang slider đầu khi mà chạy hết
    private void reverseSlider() {
        if (currentPage == 0) {
            currentPage = imageUrls.length - 1;
        } else {
            currentPage = 0;
        }
        viewPager.setCurrentItem(currentPage, true);
    }

    private void getProductReview(int productId) {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<ProductReviewRatingSumOverAll> call = retrofitApi.get_product_by_id_review_rating_sum(productId);
        call.enqueue(new Callback<ProductReviewRatingSumOverAll>() {
            @Override
            public void onResponse(Call<ProductReviewRatingSumOverAll> call, Response<ProductReviewRatingSumOverAll> response) {
                if (response.isSuccessful()) {
                    ProductReviewRatingSumOverAll productReviewRatingSumOverAll = response.body();
                    int rating_sum_dt = productReviewRatingSumOverAll.getRating_sum();
                    int rating_review_overall_dt = productReviewRatingSumOverAll.getRating_review_overall();
                    reviews_product_detail.setText(String.valueOf(rating_sum_dt));

                    star1.setImageResource(R.drawable.ic_star_filled);
                    star2.setImageResource(R.drawable.ic_star_filled);
                    star3.setImageResource(R.drawable.ic_star_filled);
                    star4.setImageResource(R.drawable.ic_star_filled);
                    star5.setImageResource(R.drawable.ic_star_filled);

                    // Thiết lập số lượng hình ảnh sao tương ứng với giá trị rating
                    if (rating_review_overall_dt >= 1) {
                        star1.setImageResource(R.drawable.ic_star_empty);
                    }
                    if (rating_review_overall_dt >= 2) {
                        star2.setImageResource(R.drawable.ic_star_empty);
                    }
                    if (rating_review_overall_dt >= 3) {
                        star3.setImageResource(R.drawable.ic_star_empty);
                    }
                    if (rating_review_overall_dt >= 4) {
                        star4.setImageResource(R.drawable.ic_star_empty);
                    }
                    if (rating_review_overall_dt >= 5) {
                        star5.setImageResource(R.drawable.ic_star_empty);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductReviewRatingSumOverAll> call, Throwable t) {

            }
        });
    }


    private void onClickButtonDescripton() {
        product_detail_descripton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_detail_descripton.setTextColor(Color.parseColor("#000000"));
                product_detail_specification.setTextColor(Color.parseColor("#969696"));
                product_detail_review.setTextColor(Color.parseColor("#969696"));
                int productId_dt = getIntent().getIntExtra("productId", 0);
                DescriptionFragment descriptonFragment = new DescriptionFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("productId", productId_dt);
                descriptonFragment.setArguments(bundle);
                displayFragment(descriptonFragment);
            }
        });
    }

    private void onClickButtonSpecification() {
        product_detail_specification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                product_detail_descripton.setTextColor(Color.parseColor("#969696"));
                product_detail_specification.setTextColor(Color.parseColor("#000000"));
                product_detail_review.setTextColor(Color.parseColor("#969696"));

                int productId_dt = getIntent().getIntExtra("productId", 0);
                SpecificationFragment specificationFragment = new SpecificationFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("productId", productId_dt);
                specificationFragment.setArguments(bundle);
                displayFragment(specificationFragment);
            }
        });
    }

    private void onClickButtonReview() {
        product_detail_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                product_detail_descripton.setTextColor(Color.parseColor("#969696"));
                product_detail_specification.setTextColor(Color.parseColor("#969696"));
                product_detail_review.setTextColor(Color.parseColor("#000000"));

                int productId_dt = getIntent().getIntExtra("productId", 0);
                ReviewFragment reviewFragment = new ReviewFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("productId", productId_dt);
                reviewFragment.setArguments(bundle);
                displayFragment(reviewFragment);
            }
        });
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.product_detail_fragment_container, fragment)
                .commit();
    }

    private void createIndicators() {
        for (int i = 0; i < imageUrls.length; i++) {
            ImageView indicator = (ImageView) LayoutInflater.from(this).inflate(R.layout.indicator, indicatorLayout, false);
            indicators.add(indicator);
            indicatorLayout.addView(indicator);
        }
    }

    private void updateIndicators() {
        for (int i = 0; i < indicators.size(); i++) {
            if (i == currentPage % indicators.size()) {
                indicators.get(i).setImageResource(R.drawable.ic_indicator_selected);
            } else {
                indicators.get(i).setImageResource(R.drawable.ic_indicator_unselected);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Dừng chạy tự động khi Activity bị hủy
        handler.removeCallbacks(runnable);
    }
}
