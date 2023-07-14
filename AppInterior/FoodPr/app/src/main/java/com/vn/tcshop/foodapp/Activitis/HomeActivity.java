package com.vn.tcshop.foodapp.Activitis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.vn.tcshop.foodapp.Activitis.Carts.CartActivity;
import com.vn.tcshop.foodapp.Activitis.Products.CategorisActivity;
import com.vn.tcshop.foodapp.Activitis.Products.ProductDetailActivity;
import com.vn.tcshop.foodapp.Activitis.Products.SearchProductActivity;
import com.vn.tcshop.foodapp.Activitis.Settings.NotificationActivity;
import com.vn.tcshop.foodapp.Adapters.Products.ProductAdapter;
import com.vn.tcshop.foodapp.Adapters.Products.ProductDiscountAdapter;
import com.vn.tcshop.foodapp.Adapters.Products.ProductHotAdapter;
import com.vn.tcshop.foodapp.Adapters.Products.ProductRandomAdapter;
import com.vn.tcshop.foodapp.Adapters.Products.SliderAdapter;
import com.vn.tcshop.foodapp.Fragments.MenuFragment;
import com.vn.tcshop.foodapp.Activitis.Products.ProductActivity;
import com.vn.tcshop.foodapp.Models.Product;
import com.vn.tcshop.foodapp.Models.Product_detail;
import com.vn.tcshop.foodapp.Models.Slider;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Activitis.Settings.SettingActivity;
import com.vn.tcshop.foodapp.Retrofits.Apis.Images_Api;
import com.vn.tcshop.foodapp.Retrofits.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Retrofits.Configs.Constant;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private MenuFragment fragmentMenu;
    private ImageView menuButton;
    private SharedPreferences sharedPreferences;
    private TextView notification_product_cart;
    private TextView notifi_payment;
    private RelativeLayout relativelayout_1;
    private RelativeLayout relativelayout_10101;
    private ImageView home_search_btn;
    private ImageView home_notification_btn;

    private ViewPager2 viewPager;
    private SliderAdapter sliderAdapter;
    private LinearLayout indicatorLayout;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private String[] imageUrls = {};
    private int currentPage = 0;
    private List<ImageView> indicators = new ArrayList<>();
    private Constant constant = new Constant();
    private ProductHotAdapter adapter;
    private ProductDiscountAdapter adapterDiscountProduct;
    private ProductRandomAdapter adapterRandomProduct;
    private RecyclerView recyclerView, recyclerViewDiscountProduct, recyclerViewRandomProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        fragmentMenu = new MenuFragment();
        menuButton = findViewById(R.id.home_menu_btn);
        relativelayout_1 = findViewById(R.id.relativelayout_1);
        notification_product_cart = findViewById(R.id.notification_product_cart);
        home_search_btn = findViewById(R.id.home_search_btn);
        notifi_payment = findViewById(R.id.notifi_payment);
        relativelayout_10101 = findViewById(R.id.relativelayout_10101);
        home_notification_btn = findViewById(R.id.home_notification_btn);
        recyclerView = findViewById(R.id.slider_recycle_home);
        recyclerViewDiscountProduct = findViewById(R.id.slider_recycle_home_discount_product);
        recyclerViewRandomProduct = findViewById(R.id.slider_recycle_home_random_product);

        // hiển thị thông báo product
        sharedPreferences = getSharedPreferences("notification_quantity_product", Context.MODE_PRIVATE);
        int savedTotalQuantity = sharedPreferences.getInt("total_quantity_product", 0);
        if (savedTotalQuantity == 0) {
            relativelayout_1.setVisibility(View.GONE);
        } else {
            relativelayout_1.setVisibility(View.VISIBLE);
            notification_product_cart.setText(String.valueOf(savedTotalQuantity));
        }

        // hiển thị thông báo product
        sharedPreferences = getSharedPreferences("notification_payment", Context.MODE_PRIVATE);
        int savednotifiPayment = sharedPreferences.getInt("notifi", 0);
        if (savednotifiPayment == 0) {
            relativelayout_10101.setVisibility(View.GONE);
        } else {
            relativelayout_10101.setVisibility(View.VISIBLE);
            notifi_payment.setText(String.valueOf(savednotifiPayment));
        }
        // Khởi tạo adapter và thiết lập RecyclerView
        adapter = new ProductHotAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterDiscountProduct = new ProductDiscountAdapter(new ArrayList<>());
        recyclerViewDiscountProduct.setAdapter(adapterDiscountProduct);
        recyclerViewDiscountProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterRandomProduct = new ProductRandomAdapter(new ArrayList<>());
        recyclerViewRandomProduct.setAdapter(adapterRandomProduct);
        recyclerViewRandomProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // --------------------------

        bottomNavigationView();
        // Hiển thị Fragment Menu
        displayFragmentMenu();
        showSearchProductBtn();
        getNotifiCations();
        sliderHome();
        click_item_product();
        getSliderProductHot();
        getSliderDiscountProduct();
        getSliderRandomProduct();
    }

    private void getSliderRandomProduct() {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<List<Product>> call = retrofitApi.random_discount_products();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> productList = response.body();
                    // Cập nhật danh sách sản phẩm trong adapter
                    adapterRandomProduct.setProductList(productList);
                    adapterRandomProduct.notifyDataSetChanged();
                } else {
                    // Xử lý khi không thành công
                    Log.e("fale product", "Failed to get products");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void getSliderDiscountProduct() {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<List<Product>> call = retrofitApi.discount_product_home();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> productList = response.body();
                    // Cập nhật danh sách sản phẩm trong adapter
                    adapterDiscountProduct.setProductList(productList);
                    adapterDiscountProduct.notifyDataSetChanged();
                } else {
                    // Xử lý khi không thành công
                    Log.e("fale product", "Failed to get products");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void getSliderProductHot() {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<List<Product>> call = retrofitApi.get_all_product();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> productList = response.body();
                    // Cập nhật danh sách sản phẩm trong adapter
                    adapter.setProductList(productList);
                    adapter.notifyDataSetChanged();
                } else {
                    // Xử lý khi không thành công
                    Log.e("fale product", "Failed to get products");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void click_item_product() {
        ProductHotAdapter.ItemClickListenerHotProduct itemClickListenerHotProduct = new ProductHotAdapter.ItemClickListenerHotProduct() {
            @Override
            public void onItemClickHotProduct(int productId) {
                RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                Call<List<Product_detail>> call = retrofitApi.get_product_by_id(productId);
                call.enqueue(new Callback<List<Product_detail>>() {
                    @Override
                    public void onResponse(Call<List<Product_detail>> call, Response<List<Product_detail>> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                            Product_detail product_detail = response.body().get(0);
                            int product_id_dt = product_detail.getProduct_id();
                            int product_detail_id_dt = product_detail.getProduct_detail_id();
                            String product_name_dt = product_detail.getName();
                            int product_detail_discount_dt = product_detail.getDiscount();
                            String image_url_dt = product_detail.getImage_url();
                            int product_detail_old_price_dt = product_detail.getOld_price();
                            int product_detail_price_dt = product_detail.getPrice();
                            String img_url_one_dt = product_detail.getImg_url_one();
                            String img_url_two_dt = product_detail.getImg_url_two();
                            String img_url_three_dt = product_detail.getImg_url_three();
                            String img_url_four_dt = product_detail.getImg_url_four();

                            // Hiển thị giá sản phẩm với định dạng ngăn cách hàng nghìn
                            DecimalFormat decimalFormat = new DecimalFormat("#.###");
                            String priceFormatted = decimalFormat.format(product_detail_price_dt);
                            String oldPriceFormatted = decimalFormat.format(product_detail_old_price_dt);

                            //Chuyển sang trang chi tiết sản phẩm với ID của sản phẩm được nhấp
                            Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
                            intent.putExtra("productId", product_id_dt);
                            intent.putExtra("product_detail_id", product_detail_id_dt);
                            intent.putExtra("product_name", product_name_dt);
                            intent.putExtra("product_detail_price", priceFormatted);
                            intent.putExtra("product_detail_discount", product_detail_discount_dt);
                            intent.putExtra("image_url", image_url_dt);
                            intent.putExtra("product_detail_old_price", oldPriceFormatted);
                            intent.putExtra("img_url_one", img_url_one_dt);
                            intent.putExtra("img_url_two", img_url_two_dt);
                            intent.putExtra("img_url_three", img_url_three_dt);
                            intent.putExtra("img_url_four", img_url_four_dt);

                            startActivity(intent);
                        } else {
                            // Xử lý khi không có dữ liệu hoặc lỗi trong phản hồi từ API
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product_detail>> call, Throwable t) {
                        // Xử lý khi gọi API thất bại
                    }
                });
            }
        };
        adapter.setItemClickListenerHotProduct(itemClickListenerHotProduct);

        ProductDiscountAdapter.ItemClickListenerDiscountProduct itemClickListenerDiscountProduct = new ProductDiscountAdapter.ItemClickListenerDiscountProduct() {
            @Override
            public void onItemClickDiscountProduct(int productId) {
                RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                Call<List<Product_detail>> call = retrofitApi.get_product_by_id(productId);
                call.enqueue(new Callback<List<Product_detail>>() {
                    @Override
                    public void onResponse(Call<List<Product_detail>> call, Response<List<Product_detail>> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                            Product_detail product_detail = response.body().get(0);
                            int product_id_dt = product_detail.getProduct_id();
                            int product_detail_id_dt = product_detail.getProduct_detail_id();
                            String product_name_dt = product_detail.getName();
                            int product_detail_discount_dt = product_detail.getDiscount();
                            String image_url_dt = product_detail.getImage_url();
                            int product_detail_old_price_dt = product_detail.getOld_price();
                            int product_detail_price_dt = product_detail.getPrice();
                            String img_url_one_dt = product_detail.getImg_url_one();
                            String img_url_two_dt = product_detail.getImg_url_two();
                            String img_url_three_dt = product_detail.getImg_url_three();
                            String img_url_four_dt = product_detail.getImg_url_four();

                            // Hiển thị giá sản phẩm với định dạng ngăn cách hàng nghìn
                            DecimalFormat decimalFormat = new DecimalFormat("#.###");
                            String priceFormatted = decimalFormat.format(product_detail_price_dt);
                            String oldPriceFormatted = decimalFormat.format(product_detail_old_price_dt);

                            //Chuyển sang trang chi tiết sản phẩm với ID của sản phẩm được nhấp
                            Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
                            intent.putExtra("productId", product_id_dt);
                            intent.putExtra("product_detail_id", product_detail_id_dt);
                            intent.putExtra("product_name", product_name_dt);
                            intent.putExtra("product_detail_price", priceFormatted);
                            intent.putExtra("product_detail_discount", product_detail_discount_dt);
                            intent.putExtra("image_url", image_url_dt);
                            intent.putExtra("product_detail_old_price", oldPriceFormatted);
                            intent.putExtra("img_url_one", img_url_one_dt);
                            intent.putExtra("img_url_two", img_url_two_dt);
                            intent.putExtra("img_url_three", img_url_three_dt);
                            intent.putExtra("img_url_four", img_url_four_dt);

                            startActivity(intent);
                        } else {
                            // Xử lý khi không có dữ liệu hoặc lỗi trong phản hồi từ API
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product_detail>> call, Throwable t) {
                        // Xử lý khi gọi API thất bại
                    }
                });
            }
        };
        adapterDiscountProduct.setItemClickListenerHotProduct(itemClickListenerDiscountProduct);

        ProductRandomAdapter.ItemClickListenerRandomProduct itemClickListenerRandomProduct = new ProductRandomAdapter.ItemClickListenerRandomProduct() {
            @Override
            public void onItemClickRandomProduct(int productId) {
                RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                Call<List<Product_detail>> call = retrofitApi.get_product_by_id(productId);
                call.enqueue(new Callback<List<Product_detail>>() {
                    @Override
                    public void onResponse(Call<List<Product_detail>> call, Response<List<Product_detail>> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                            Product_detail product_detail = response.body().get(0);
                            int product_id_dt = product_detail.getProduct_id();
                            int product_detail_id_dt = product_detail.getProduct_detail_id();
                            String product_name_dt = product_detail.getName();
                            int product_detail_discount_dt = product_detail.getDiscount();
                            String image_url_dt = product_detail.getImage_url();
                            int product_detail_old_price_dt = product_detail.getOld_price();
                            int product_detail_price_dt = product_detail.getPrice();
                            String img_url_one_dt = product_detail.getImg_url_one();
                            String img_url_two_dt = product_detail.getImg_url_two();
                            String img_url_three_dt = product_detail.getImg_url_three();
                            String img_url_four_dt = product_detail.getImg_url_four();

                            // Hiển thị giá sản phẩm với định dạng ngăn cách hàng nghìn
                            DecimalFormat decimalFormat = new DecimalFormat("#.###");
                            String priceFormatted = decimalFormat.format(product_detail_price_dt);
                            String oldPriceFormatted = decimalFormat.format(product_detail_old_price_dt);

                            //Chuyển sang trang chi tiết sản phẩm với ID của sản phẩm được nhấp
                            Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
                            intent.putExtra("productId", product_id_dt);
                            intent.putExtra("product_detail_id", product_detail_id_dt);
                            intent.putExtra("product_name", product_name_dt);
                            intent.putExtra("product_detail_price", priceFormatted);
                            intent.putExtra("product_detail_discount", product_detail_discount_dt);
                            intent.putExtra("image_url", image_url_dt);
                            intent.putExtra("product_detail_old_price", oldPriceFormatted);
                            intent.putExtra("img_url_one", img_url_one_dt);
                            intent.putExtra("img_url_two", img_url_two_dt);
                            intent.putExtra("img_url_three", img_url_three_dt);
                            intent.putExtra("img_url_four", img_url_four_dt);

                            startActivity(intent);
                        } else {
                            // Xử lý khi không có dữ liệu hoặc lỗi trong phản hồi từ API
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product_detail>> call, Throwable t) {
                        // Xử lý khi gọi API thất bại
                    }
                });
            }
        };
        adapterRandomProduct.setItemClickListenerRandomProduct(itemClickListenerRandomProduct);
    }

    private void sliderHome() {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<List<Slider>> call = retrofitApi.get_all_slider();
        call.enqueue(new Callback<List<Slider>>() {
            @Override
            public void onResponse(Call<List<Slider>> call, Response<List<Slider>> response) {
                if (response.isSuccessful()) {
                    List<Slider> sliders = response.body();
                    Slider slider = sliders.get(0);
                    String img_1 = Images_Api.getImgSlider(slider.getImg_1());
                    String img_2 = Images_Api.getImgSlider(slider.getImg_2());
                    String img_3 = Images_Api.getImgSlider(slider.getImg_3());
                    String img_4 = Images_Api.getImgSlider(slider.getImg_4());
                    String img_5 = Images_Api.getImgSlider(slider.getImg_5());
                    imageUrls = new String[]{img_1, img_2, img_3, img_4, img_5};
                    // ----------------
                    viewPager = findViewById(R.id.viewPagerHome);
                    indicatorLayout = findViewById(R.id.indicatorLayout_01);

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
            }

            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {

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

    private void getNotifiCations() {
        home_notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences saveNotifiPayment = getSharedPreferences("notification_payment", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = saveNotifiPayment.edit();
                editor.putInt("notifi", 0); // Đặt giá trị "notifi" về 0
                editor.apply();
                startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
                finish();
            }
        });
    }

    private void showSearchProductBtn() {
        home_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SearchProductActivity.class));
                finish();
            }
        });
    }

    private void displayFragmentMenu() {
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Áp dụng animation cho Fragment mới
                fragmentTransaction.setCustomAnimations(R.anim.slide_right_to_left, 0, 0, R.anim.slide_left_to_right);

                fragmentTransaction.replace(R.id.fragment_container, fragmentMenu);
                fragmentTransaction.addToBackStack(null); // (Optional) Cho phép quay lại Fragment trước đó
                fragmentTransaction.commit();
            }
        });
    }

    private void bottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Trang chủ":
                        return true;
                    case "Sản phẩm":
                        startActivity(new Intent(HomeActivity.this, CategorisActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case "Giỏ hàng":
                        startActivity(new Intent(HomeActivity.this, CartActivity.class));
                        return true;
                    case "Cài đặt":
                        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}