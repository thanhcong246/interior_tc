package com.vn.tcshop.foodapp.Activitis.Products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vn.tcshop.foodapp.Adapters.Products.ProductAdapter;
import com.vn.tcshop.foodapp.Models.Product;
import com.vn.tcshop.foodapp.Models.Product_detail;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Retrofits.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Retrofits.Configs.Constant;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProductActivity extends AppCompatActivity {
    private View closeSearchProduct;
    private RecyclerView recyclerView_product;
    private Constant constant = new Constant();
    private ProductAdapter adapter;
    private EditText edt_searchProduct;
    private Button submit_searchProduct;
    private TextView nullSearchProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        closeSearchProduct = findViewById(R.id.closeSearchProduct);
        recyclerView_product = findViewById(R.id.recyclerView_product);
        recyclerView_product = findViewById(R.id.recyclerView_product);
        edt_searchProduct = findViewById(R.id.edt_searchProduct);
        submit_searchProduct = findViewById(R.id.submit_searchProduct);
        nullSearchProduct = findViewById(R.id.nullSearchProduct);

        // Khởi tạo adapter và thiết lập RecyclerView
        adapter = new ProductAdapter(new ArrayList<>());
        recyclerView_product.setAdapter(adapter);
        recyclerView_product.setLayoutManager(new GridLayoutManager(this, 2));

        closeSearchProductBtn();
        searchProduct();
        click_item_product();
    }

    private void click_item_product() {
        ProductAdapter.ItemClickListener itemClickListener = new ProductAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int productId) {
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
                            DecimalFormat decimalFormat = new DecimalFormat("#,###");
                            String priceFormatted = decimalFormat.format(product_detail_price_dt);
                            String oldPriceFormatted = decimalFormat.format(product_detail_old_price_dt);

                            //Chuyển sang trang chi tiết sản phẩm với ID của sản phẩm được nhấp
                            Intent intent = new Intent(SearchProductActivity.this, ProductDetailActivity.class);
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
        // Thiết lập ItemClickListener cho adapter
        adapter.setItemClickListener(itemClickListener);
    }

    private void searchProduct() {
        submit_searchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nullSearchProduct.setVisibility(View.VISIBLE);
                recyclerView_product.setVisibility(View.GONE);
                String textSearch = edt_searchProduct.getText().toString();
                RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                Call<List<Product>> call = retrofitApi.search_products(textSearch);
                call.enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful()) {
                            List<Product> productList = response.body();
                            if (productList != null) {
                                nullSearchProduct.setVisibility(View.GONE);
                                recyclerView_product.setVisibility(View.VISIBLE);
                                adapter.setProductList(productList);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void closeSearchProductBtn() {
        closeSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchProductActivity.this, CategorisActivity.class));
                finish();
            }
        });
    }
}