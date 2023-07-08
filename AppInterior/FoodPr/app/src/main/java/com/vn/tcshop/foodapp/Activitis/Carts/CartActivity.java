package com.vn.tcshop.foodapp.Activitis.Carts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vn.tcshop.foodapp.Adapters.Carts.CartAdapter;
import com.vn.tcshop.foodapp.Retrofits.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Retrofits.Configs.Constant;
import com.vn.tcshop.foodapp.Activitis.HomeActivity;
import com.vn.tcshop.foodapp.Models.Cart;
import com.vn.tcshop.foodapp.Models.CartByPayment;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Models.Responses.CartByIdResponse;
import com.vn.tcshop.foodapp.Models.Responses.CartDeleteAllResponse;
import com.vn.tcshop.foodapp.Models.Responses.CartRemoveProductId;
import com.vn.tcshop.foodapp.Models.Responses.CartRemoveProductIdAll;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private CartAdapter adapter;
    private RecyclerView recyclerView;
    private Constant constant = new Constant();
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "login_prefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private View btn_close_cart;
    private View cartRelativeLayout_10000;
    private TextView null_product;
    private TextView cart_total_quantity;
    private TextView cart_total_price;
    private TextView cart_transportation_costs;
    private TextView cart_total_payment;
    private Button delete_all_cart_product_id;
    private ConstraintLayout constraintLayout_10000;
    private Button proceed_to_checkout_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.list_recyc_cart);
        btn_close_cart = findViewById(R.id.btn_close_cart);
        cartRelativeLayout_10000 = findViewById(R.id.cartRelativeLayout_10000);
        null_product = findViewById(R.id.null_product);
        cart_total_quantity = findViewById(R.id.cart_total_quantity);
        cart_total_price = findViewById(R.id.cart_total_price);
        cart_transportation_costs = findViewById(R.id.cart_transportation_costs);
        cart_total_payment = findViewById(R.id.cart_total_payment);
        delete_all_cart_product_id = findViewById(R.id.delete_all_cart_product_id);
        constraintLayout_10000 = findViewById(R.id.constraintLayout_10000);
        proceed_to_checkout_cart = findViewById(R.id.proceed_to_checkout_cart);

        // Khởi tạo adapter và thiết lập RecyclerView
        adapter = new CartAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)) {
            String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");
            getCarts(savedEmail);
            getTotalCartPayment(savedEmail);
            deleteAllCartProductId(savedEmail);
            deleteCartProductId(savedEmail);
            removerCartProductId(savedEmail);
            addCartProductId(savedEmail);
        }

        closeCart();
        getCartDetail();
    }

    private void getCartDetail() {
        proceed_to_checkout_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this,DetailCartActivity.class);
                intent.putExtra("case",1);
                startActivity(intent);
            }
        });
    }

    private void addCartProductId(String savedEmail) {
        adapter.setOnAddClickListener(new CartAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(Cart cart) {
                int cartProductId = cart.getProduct_id();
                int cartPrice = cart.getPrice();
                String cartName = cart.getCart_name();
                String cartImg = cart.getCart_img();

                RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                Call<CartByIdResponse> call = retrofitApi.add_cart_by_id(cartProductId, savedEmail, cartPrice, cartName, cartImg);
                call.enqueue(new Callback<CartByIdResponse>() {
                    @Override
                    public void onResponse(Call<CartByIdResponse> call, Response<CartByIdResponse> response) {
                        if (response.isSuccessful()) {
                            CartByIdResponse cartByIdResponse = response.body();
                            String error_cart_add = cartByIdResponse.getError_cart_add();
                            if (error_cart_add.equals("000")) {
                                getCarts(savedEmail);
                                getTotalCartPayment(savedEmail);
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

    private void removerCartProductId(String savedEmail) {
        adapter.setOnRemoveClickListener(new CartAdapter.OnRemoveClickListener() {
            @Override
            public void onRemoveClick(int productId) {
                RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                Call<CartRemoveProductId> call = retrofitApi.delete_cart_by_id(savedEmail, productId);
                call.enqueue(new Callback<CartRemoveProductId>() {
                    @Override
                    public void onResponse(Call<CartRemoveProductId> call, Response<CartRemoveProductId> response) {
                        if (response.isSuccessful()) {
                            CartRemoveProductId cartRemoveProductId = response.body();
                            String error_remove_cart_by_id = cartRemoveProductId.getError_remove_cart_by_id();
                            if (error_remove_cart_by_id.equals("000")) {
                                getCarts(savedEmail);
                                getTotalCartPayment(savedEmail);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartRemoveProductId> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void deleteCartProductId(String savedEmail) {
        adapter.setOnDeleteClickListener(new CartAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int productId) {
                RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                Call<CartRemoveProductIdAll> call = retrofitApi.delete_cart_by_id_all(savedEmail, productId);
                call.enqueue(new Callback<CartRemoveProductIdAll>() {
                    @Override
                    public void onResponse(Call<CartRemoveProductIdAll> call, Response<CartRemoveProductIdAll> response) {
                        if (response.isSuccessful()) {
                            CartRemoveProductIdAll cartRemoveProductIdAll = response.body();
                            String error_remove_cart_by_id_all = cartRemoveProductIdAll.getError_remove_cart_by_id_all();
                            if (error_remove_cart_by_id_all.equals("000")) {
                                getCarts(savedEmail);
                                getTotalCartPayment(savedEmail);
                                Log.d("error_remove_cart_by_id_all", error_remove_cart_by_id_all);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartRemoveProductIdAll> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void deleteAllCartProductId(String savedEmail) {
        delete_all_cart_product_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Thông báo"); // Tiêu đề của hộp thoại
                builder.setMessage("Bạn có muốn xóa hết sản phẩm trong giỏ hàng?"); // Nội dung của hộp thoại

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                        Call<CartDeleteAllResponse> call = retrofitApi.delete_all_cart(savedEmail);
                        call.enqueue(new Callback<CartDeleteAllResponse>() {
                            @Override
                            public void onResponse(Call<CartDeleteAllResponse> call, Response<CartDeleteAllResponse> response) {
                                if (response.isSuccessful()) {
                                    CartDeleteAllResponse cartDeleteAllResponse = response.body();
                                    String error_all_cart_by_product_id = cartDeleteAllResponse.getError_all_cart_by_product_id();
                                    if (Objects.equals(error_all_cart_by_product_id, "000")) {
                                        getCarts(savedEmail);
                                        getTotalCartPayment(savedEmail);
                                        Toast.makeText(getApplicationContext(), "Đã xóa hết sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<CartDeleteAllResponse> call, Throwable t) {

                            }
                        });
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
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
                    int transportation_costs = cartByPayment.getTransportation_costs();
                    int total_price = cartByPayment.getTotal_price();
                    int total_quantity = cartByPayment.getTotal_quantity();
                    int total_payment = cartByPayment.getTotal_payment();

                    // lưu thông tin thông báo product
                    editor.putInt("total_quantity_product", total_quantity);
                    editor.apply();

                    // Hiển thị giá sản phẩm với định dạng ngăn cách hàng nghìn
                    DecimalFormat decimalFormat = new DecimalFormat("#.###");
                    String total_price_format = decimalFormat.format(total_price);
                    String transportation_costs_format = decimalFormat.format(transportation_costs);
                    String total_payment_format = decimalFormat.format(total_payment);

                    cart_total_quantity.setText(String.valueOf(total_quantity));
                    cart_total_price.setText(total_price_format + "₫");
                    cart_transportation_costs.setText(transportation_costs_format + "₫");
                    cart_total_payment.setText(total_payment_format + "₫");
                }
            }

            @Override
            public void onFailure(Call<CartByPayment> call, Throwable t) {

            }
        });
    }

    private void getCarts(String savedEmail) {
        cartRelativeLayout_10000.setVisibility(View.VISIBLE);
        null_product.setVisibility(View.VISIBLE);
        constraintLayout_10000.setVisibility(View.GONE);
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<List<Cart>> call = retrofitApi.get_cart_by_id(savedEmail);
        call.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if (response.isSuccessful()) {
                    List<Cart> cartList = response.body();
                    if (cartList != null && !cartList.isEmpty()) {
                        adapter.setCartList(cartList);
                        adapter.notifyDataSetChanged();
                        constraintLayout_10000.setVisibility(View.VISIBLE);
                        cartRelativeLayout_10000.setVisibility(View.GONE);
                        null_product.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {

            }
        });
    }

    private void closeCart() {
        btn_close_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
}