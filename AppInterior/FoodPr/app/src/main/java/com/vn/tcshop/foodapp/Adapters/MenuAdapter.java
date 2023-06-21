package com.vn.tcshop.foodapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.tcshop.foodapp.Models.MenuItemModel;
import com.vn.tcshop.foodapp.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<MenuItemModel> menuItems;

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(MenuItemModel menuItem);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public MenuAdapter(List<MenuItemModel> menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItemModel menuItem = menuItems.get(position);
        holder.bind(menuItem);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private ImageView iconImageView;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            iconImageView = itemView.findViewById(R.id.iconImageView);

            // Gọi phương thức onItemClick khi itemView được click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                        MenuItemModel menuItem = menuItems.get(position);
                        itemClickListener.onItemClick(menuItem);
                    }
                }
            });
        }

        public void bind(MenuItemModel menuItem) {
            titleTextView.setText(menuItem.getTitle());
            iconImageView.setImageDrawable(menuItem.getIcon());
        }
    }
}
