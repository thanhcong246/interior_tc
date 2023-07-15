package com.vn.tcshop.foodapp.Adapters.Products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vn.tcshop.foodapp.Models.Product;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Retrofits.Apis.Images_Api;

import java.text.DecimalFormat;
import java.util.List;

public class ProductHotAdapter extends RecyclerView.Adapter<ProductHotAdapter.ViewHolder> {
    private List<Product> productList;
    private ItemClickListenerHotProduct itemClickListenerHotProduct;

    public ProductHotAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void setItemClickListenerHotProduct(ItemClickListenerHotProduct itemClickListenerHotProduct) {
        this.itemClickListenerHotProduct = itemClickListenerHotProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameProduct.setText(product.getName());
        // Hiển thị giá sản phẩm với định dạng ngăn cách hàng nghìn
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String priceFormatted = decimalFormat.format(product.getPrice());
        holder.priceProduct.setText(priceFormatted + "đ");
        holder.discountProduct.setText(product.getDiscount() + "%");
        String urlImg = Images_Api.getImageUrl(product.getImage());
        Picasso.get().load(urlImg).into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView discountProduct;
        private ImageView imgProduct;
        private TextView nameProduct, priceProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            discountProduct = itemView.findViewById(R.id.discount_home_item);
            imgProduct = itemView.findViewById(R.id.img_home_item);
            nameProduct = itemView.findViewById(R.id.name_home_item);
            priceProduct = itemView.findViewById(R.id.price_home_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListenerHotProduct != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Product product = productList.get(position);
                            itemClickListenerHotProduct.onItemClickHotProduct(product.getProduct_id());
                        }
                    }
                }
            });
        }
    }

    public interface ItemClickListenerHotProduct {
        void onItemClickHotProduct(int productId);
    }
}
