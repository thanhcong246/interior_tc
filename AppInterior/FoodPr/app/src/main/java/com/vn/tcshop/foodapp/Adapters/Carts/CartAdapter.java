package com.vn.tcshop.foodapp.Adapters.Carts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vn.tcshop.foodapp.Retrofits.Apis.Images_Api;
import com.vn.tcshop.foodapp.Models.Cart;
import com.vn.tcshop.foodapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<Cart> cartList;
    private OnDeleteClickListener onDeleteClickListener;
    private OnRemoveClickListener onRemoveClickListener;
    private OnAddClickListener onAddClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int productId);
    }

    public interface OnRemoveClickListener {
        void onRemoveClick(int productId);
    }

    public interface OnAddClickListener {
        void onAddClick(Cart cart);
    }


    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    public void setOnRemoveClickListener(OnRemoveClickListener listener) {
        this.onRemoveClickListener = listener;
    }

    public void setOnAddClickListener(OnAddClickListener listener) {
        this.onAddClickListener = listener;
    }

    public CartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public void setCartList(List<Cart> mCartList) {
        this.cartList = mCartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout_recycle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.cart_product_id_item.setText(String.valueOf(cart.getProduct_id()));
        holder.cart_email_item.setText(cart.getEmail());
        holder.cart_sum_product_id_price_item.setText(String.valueOf(cart.getSum_product_id_price()));
        holder.name_cart_product_id_item.setText(cart.getCart_name());
        holder.quantity_cart_product_id_item.setText(String.valueOf(cart.getQuantity_product_id()));
        // Sử dụng Picasso để tải ảnh từ URL vào ImageView
        String imageUrl = Images_Api.getImageUrl(cart.getCart_img());
        Picasso.get().load(imageUrl).into(holder.cart_img_product_id_item);
        // Hiển thị giá sản phẩm với định dạng ngăn cách hàng nghìn
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String priceFormatted = decimalFormat.format(cart.getPrice());
        holder.price_cart_product_id_item.setText(priceFormatted + "₫");

        final int productId = cart.getProduct_id();

        holder.delete_cart_product_id_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(productId);
                }
            }
        });

        holder.remove_cart_product_id_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRemoveClickListener != null) {
                    onRemoveClickListener.onRemoveClick(productId);
                }
            }
        });

        holder.add_cart_product_id_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAddClickListener != null) {
                    onAddClickListener.onAddClick(cart);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cart_product_id_item;
        private TextView cart_email_item;
        private TextView cart_sum_product_id_price_item;
        private ImageView cart_img_product_id_item;
        private TextView name_cart_product_id_item;
        private TextView price_cart_product_id_item;
        private TextView quantity_cart_product_id_item;
        private ImageView delete_cart_product_id_item;
        private ImageView remove_cart_product_id_item;
        private ImageView add_cart_product_id_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_product_id_item = itemView.findViewById(R.id.cart_product_id_item);
            cart_email_item = itemView.findViewById(R.id.cart_email_item);
            cart_sum_product_id_price_item = itemView.findViewById(R.id.cart_sum_product_id_price_item);
            cart_img_product_id_item = itemView.findViewById(R.id.cart_img_product_id_item);
            name_cart_product_id_item = itemView.findViewById(R.id.name_cart_product_id_item);
            price_cart_product_id_item = itemView.findViewById(R.id.price_cart_product_id_item);
            quantity_cart_product_id_item = itemView.findViewById(R.id.quantity_cart_product_id_item);
            delete_cart_product_id_item = itemView.findViewById(R.id.delete_cart_product_id_item);
            remove_cart_product_id_item = itemView.findViewById(R.id.remove_cart_product_id_item);
            add_cart_product_id_item = itemView.findViewById(R.id.add_cart_product_id_item);
        }
    }
}
