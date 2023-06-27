package com.vn.tcshop.foodapp.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.vn.tcshop.foodapp.Adapters.Products.ProductDetailReviewAdapter;
import com.vn.tcshop.foodapp.Retrofits.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Retrofits.Configs.Constant;
import com.vn.tcshop.foodapp.Models.ProductReviewRatingSumOverAll;
import com.vn.tcshop.foodapp.Models.ProductReviews;
import com.vn.tcshop.foodapp.Models.Product_detail_reviews;
import com.vn.tcshop.foodapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFragment extends Fragment {
    private TextView product_review_rating_review_overall;
    private TextView product_review_rating_sum;
    private BarChart barChartRating5;
    private BarChart barChartRating4;
    private BarChart barChartRating3;
    private BarChart barChartRating2;
    private BarChart barChartRating1;
    private Button buttonShowDialog;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "login_prefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private Constant constant = new Constant();
    private RecyclerView recyclerView_review;
    private ProductDetailReviewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);

        product_review_rating_review_overall = rootView.findViewById(R.id.product_review_rating_review_overall);
        product_review_rating_sum = rootView.findViewById(R.id.product_review_rating_sum);
        barChartRating5 = rootView.findViewById(R.id.barChart_rating5);
        barChartRating4 = rootView.findViewById(R.id.barChart_rating4);
        barChartRating3 = rootView.findViewById(R.id.barChart_rating3);
        barChartRating2 = rootView.findViewById(R.id.barChart_rating2);
        barChartRating1 = rootView.findViewById(R.id.barChart_rating1);
        buttonShowDialog = rootView.findViewById(R.id.buttonShowDialog_review);
        recyclerView_review = rootView.findViewById(R.id.recyce_rating_review);

        // Khởi tạo adapter và thiết lập RecyclerView
        adapter = new ProductDetailReviewAdapter(new ArrayList<>());
        recyclerView_review.setAdapter(adapter);
        recyclerView_review.setLayoutManager(new LinearLayoutManager(getContext()));

        // Kiểm tra xem arguments có null hay không
        Bundle arguments = getArguments();
        if (arguments != null) {
            int productId = arguments.getInt("productId");
            showDiaLogReviews(productId);
            getProductReviews(productId);
            getProductReviewRatingAllSum(productId);
        }

        return rootView;
    }

    private void getProductReviewRatingAllSum(int productId) {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<ProductReviewRatingSumOverAll> call = retrofitApi.get_product_by_id_review_rating_sum(productId);
        call.enqueue(new Callback<ProductReviewRatingSumOverAll>() {
            @Override
            public void onResponse(Call<ProductReviewRatingSumOverAll> call, Response<ProductReviewRatingSumOverAll> response) {
                if (response.isSuccessful()) {
                    ProductReviewRatingSumOverAll productReviewRatingSumOverAll = response.body();
                    int rt_start1 = productReviewRatingSumOverAll.getRating_start1();
                    int rt_start2 = productReviewRatingSumOverAll.getRating_start2();
                    int rt_start3 = productReviewRatingSumOverAll.getRating_start3();
                    int rt_start4 = productReviewRatingSumOverAll.getRating_start4();
                    int rt_start5 = productReviewRatingSumOverAll.getRating_start5();
                    int rt_sum = productReviewRatingSumOverAll.getRating_sum();
                    int rt_view_overall = productReviewRatingSumOverAll.getRating_review_overall();

                    product_review_rating_review_overall.setText(String.valueOf(rt_view_overall));
                    product_review_rating_sum.setText(String.valueOf(rt_sum));
                    setBarChartData(barChartRating5, 5, rt_sum, rt_start5, ContextCompat.getColor(requireContext(), R.color.start5)); // 5 sao - 6 đánh giá
                    setBarChartData(barChartRating4, 4, rt_sum, rt_start4, ContextCompat.getColor(requireContext(), R.color.start4)); // 4 sao - 10 đánh giá
                    setBarChartData(barChartRating3, 3, rt_sum, rt_start3, ContextCompat.getColor(requireContext(), R.color.start3)); // 3 sao - 4 đánh giá
                    setBarChartData(barChartRating2, 2, rt_sum, rt_start2, ContextCompat.getColor(requireContext(), R.color.start2)); // 2 sao - 1 đánh giá
                    setBarChartData(barChartRating1, 1, rt_sum, rt_start1, ContextCompat.getColor(requireContext(), R.color.start1)); // 1 sao - 5 đánh giá

                }
            }

            @Override
            public void onFailure(Call<ProductReviewRatingSumOverAll> call, Throwable t) {

            }
        });
    }

    private void getProductReviews(int productId) {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<List<ProductReviews>> call = retrofitApi.get_product_by_id_reviews(productId);
        call.enqueue(new Callback<List<ProductReviews>>() {
            @Override
            public void onResponse(Call<List<ProductReviews>> call, Response<List<ProductReviews>> response) {
                if (response.isSuccessful()) {
                    List<ProductReviews> productReviews = response.body();
                    adapter.setProductReviewsList(productReviews);
                    adapter.notifyDataSetChanged();
                } else {
                    // Xử lý khi không thành công
                    Log.e("fale product review", "Failed to get products reviews");
                }
            }

            @Override
            public void onFailure(Call<List<ProductReviews>> call, Throwable t) {

            }
        });
    }

    private void showDiaLogReviews(int productId) {
        buttonShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo đối tượng dialog và hiển thị hộp thoại
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_review, null);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();

                RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar_item_review);
                EditText editTextComment = dialogView.findViewById(R.id.editTextComment_item_review);
                Button buttonSubmit = dialogView.findViewById(R.id.buttonSubmit_item_review);

                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(ContextCompat.getColor(requireContext(), R.color.start5), PorterDuff.Mode.SRC_ATOP);

                // Xử lý sự kiện khi nhấn nút "Đánh giá"
                buttonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
                        if (sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)) {
                            String savedName = sharedPreferences.getString(KEY_NAME, "");
                            // Lấy giá trị rating từ RatingBar
                            float ratingFloat = ratingBar.getRating();
                            // Chuyển đổi giá trị rating thành kiểu int
                            int ratingInt = Math.round(ratingFloat);
                            String comment = editTextComment.getText().toString();

                            if (ratingInt == 0) {
                                Toast.makeText(getActivity(), "Bạn chưa đánh giá", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (TextUtils.isEmpty(comment)) {
                                editTextComment.setError("Không thể để trống");
                                return;
                            }

                            RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                            Call<Product_detail_reviews> call = retrofitApi.add_product_detail_reviews(productId, comment, ratingInt, savedName);
                            call.enqueue(new Callback<Product_detail_reviews>() {
                                @Override
                                public void onResponse(Call<Product_detail_reviews> call, Response<Product_detail_reviews> response) {
                                    if (response.isSuccessful()) {
                                        Product_detail_reviews product_detail_reviews = response.body();
                                        String error_review = product_detail_reviews.getError_product_detail_reviews();
                                        if (Objects.equals(error_review, "000")) {
                                            Toast.makeText(getActivity(), "Đánh giá của bạn đã được ghi lại", Toast.LENGTH_SHORT).show();
                                            // Đóng hộp thoại
                                            dialog.dismiss();
                                            // Cập nhật danh sách đánh giá
                                            getProductReviews(productId);
                                        }
                                        if (Objects.equals(error_review, "111")) {
                                            Log.e("error_product_detail_reviews", "fail add review products");
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Product_detail_reviews> call, Throwable t) {

                                }
                            });
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private void setBarChartData(BarChart barChart, int rating, int rating_sum, int reviewCount, int colors) {
        float total = rating_sum;
        float percentage = (reviewCount / total) * 100;

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(rating, percentage));

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(colors);

        BarData barData = new BarData(dataSet);
        barData.setDrawValues(false); // Ẩn giá trị trên cột

        barChart.setData(barData);

        barChart.setTouchEnabled(false); // Vô hiệu hóa sự kiện chạm vào biểu đồ
        barChart.setClickable(false); // Vô hiệu hóa sự kiện click vào biểu đồ
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);
        barChart.setFitBars(true);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false); // Ẩn trục Y bên trái
        barChart.getAxisRight().setEnabled(false); // Ẩn trục Y bên phải
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // Đặt trục X ở dưới
        barChart.getXAxis().setEnabled(false); // Ẩn trục X
        barChart.getAxisLeft().setAxisMinimum(0f); // Đặt giá trị tối thiểu trên trục Y
        barChart.getAxisLeft().setAxisMaximum(100f); // Đặt giá trị tối đa trên trục Y
        barChart.setScaleX(1.7f); // Thay đổi tỷ lệ độ rộng của biểu đồ
        barChart.setDrawBorders(false); // Ẩn viền
        barChart.invalidate(); // Vẽ biểu đồ
    }
}
