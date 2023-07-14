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

import java.util.List;

public class ProductRandomAdapter extends RecyclerView.Adapter<ProductRandomAdapter.ViewHolder> {
    private List<Product> productList;
    private ItemClickListenerRandomProduct itemClickListenerRandomProduct;

    public ProductRandomAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void setItemClickListenerRandomProduct(ItemClickListenerRandomProduct itemClickListenerRandomProduct) {
        this.itemClickListenerRandomProduct = itemClickListenerRandomProduct;
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
        holder.priceProduct.setText(product.getPrice() + "đ");
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
                    if (itemClickListenerRandomProduct != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Product product = productList.get(position);
                            itemClickListenerRandomProduct.onItemClickRandomProduct(product.getProduct_id());
                        }
                    }
                }
            });
        }
    }

    public interface ItemClickListenerRandomProduct {
        void onItemClickRandomProduct(int productId);
    }
}
