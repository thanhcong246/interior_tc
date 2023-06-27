package com.vn.tcshop.foodapp.Adapters.Products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vn.tcshop.foodapp.Retrofits.Apis.Images_Api;
import com.vn.tcshop.foodapp.Models.Product;
import com.vn.tcshop.foodapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList;
    private ItemClickListener itemClickListener;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.idProduct.setText(String.valueOf(product.getProduct_id()));
        holder.nameTextView.setText(product.getName());

        // Hiển thị giá sản phẩm với định dạng ngăn cách hàng nghìn
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String priceFormatted = decimalFormat.format(product.getPrice());
        holder.priceTextView.setText(priceFormatted + "₫");

        // Sử dụng Picasso để tải ảnh từ URL vào ImageView
        String imageUrl = Images_Api.getImageUrl(product.getImage());
        Picasso.get().load(imageUrl).into(holder.productImageView);
//        Log.d("imgurl", imageUrl);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idProduct;
        public ImageView productImageView;
        public TextView nameTextView;
        public TextView priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idProduct = itemView.findViewById(R.id.product_item_adapter_id);
            productImageView = itemView.findViewById(R.id.product_item_adapter_img);
            nameTextView = itemView.findViewById(R.id.product_item_adapter_name);
            priceTextView = itemView.findViewById(R.id.product_item_adapter_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Product product = productList.get(position);
                            itemClickListener.onItemClick(product.getProduct_id());
                        }
                    }
                }
            });
        }
    }

    public interface ItemClickListener {
        void onItemClick(int productId);
    }
}
