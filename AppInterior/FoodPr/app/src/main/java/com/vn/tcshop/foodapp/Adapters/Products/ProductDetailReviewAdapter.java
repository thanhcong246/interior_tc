package com.vn.tcshop.foodapp.Adapters.Products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.tcshop.foodapp.Models.ProductReviews;
import com.vn.tcshop.foodapp.R;

import java.util.List;
import java.util.zip.Inflater;

public class ProductDetailReviewAdapter extends RecyclerView.Adapter<ProductDetailReviewAdapter.ViewHolder> {
    private List<ProductReviews> productReviewsList;

    public ProductDetailReviewAdapter(List<ProductReviews> productReviewsList) {
        this.productReviewsList = productReviewsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rating_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductReviews productReviews = productReviewsList.get(position);
        holder.user_comment_review.setText(productReviews.getUser_name());
        holder.date_comment_review_user.setText(productReviews.getComment_date());
        holder.comment_user_tv.setText(productReviews.getContent());
        int rating = productReviews.getRating(); // Lấy giá trị rating từ đối tượng ProductReviews
        // Đặt tất cả ImageView thành hình ảnh sao rỗng ban đầu
        holder.reviewStar1.setImageResource(R.drawable.ic_star_filled);
        holder.reviewStar2.setImageResource(R.drawable.ic_star_filled);
        holder.reviewStar3.setImageResource(R.drawable.ic_star_filled);
        holder.reviewStar4.setImageResource(R.drawable.ic_star_filled);
        holder.reviewStar5.setImageResource(R.drawable.ic_star_filled);

        // Thiết lập số lượng hình ảnh sao tương ứng với giá trị rating
        if (rating >= 1) {
            holder.reviewStar1.setImageResource(R.drawable.ic_star_empty);
        }
        if (rating >= 2) {
            holder.reviewStar2.setImageResource(R.drawable.ic_star_empty);
        }
        if (rating >= 3) {
            holder.reviewStar3.setImageResource(R.drawable.ic_star_empty);
        }
        if (rating >= 4) {
            holder.reviewStar4.setImageResource(R.drawable.ic_star_empty);
        }
        if (rating >= 5) {
            holder.reviewStar5.setImageResource(R.drawable.ic_star_empty);
        }
    }

    @Override
    public int getItemCount() {
        return productReviewsList.size();
    }

    public void setProductReviewsList(List<ProductReviews> productReviewsList1) {
        this.productReviewsList = productReviewsList1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView user_comment_review;
        private TextView date_comment_review_user;
        private TextView comment_user_tv;
        private ImageView reviewStar1;
        private ImageView reviewStar2;
        private ImageView reviewStar3;
        private ImageView reviewStar4;
        private ImageView reviewStar5;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_comment_review = itemView.findViewById(R.id.user_comment_review);
            date_comment_review_user = itemView.findViewById(R.id.date_comment_review_user);
            comment_user_tv = itemView.findViewById(R.id.comment_user_tv);
            reviewStar1 = itemView.findViewById(R.id.user_comment_star1);
            reviewStar2 = itemView.findViewById(R.id.user_comment_star2);
            reviewStar3 = itemView.findViewById(R.id.user_comment_star3);
            reviewStar4 = itemView.findViewById(R.id.user_comment_star4);
            reviewStar5 = itemView.findViewById(R.id.user_comment_star5);
        }
    }
}
