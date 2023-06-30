package com.vn.tcshop.foodapp.Adapters.Products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vn.tcshop.foodapp.Models.Category;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Retrofits.Apis.Images_Api;

import java.util.List;

public class CategoriAdapter extends RecyclerView.Adapter<CategoriAdapter.ViewHolder> {
    private List<Category> categoryList;
    private ItemClickCategory itemClickCategory;

    public CategoriAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void setItemClickCategory(ItemClickCategory itemClickCategory) {
        this.itemClickCategory = itemClickCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryName_item.setText(category.getName());
        String imageUrl = Images_Api.getImageUrl(category.getCategory_img());
        Picasso.get().load(imageUrl).into(holder.categoryImg_item);

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setCategoryList(List<Category> categoryList){
        this.categoryList = categoryList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName_item;
        private ImageView categoryImg_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImg_item = itemView.findViewById(R.id.categoryImg_item);
            categoryName_item = itemView.findViewById(R.id.categoryName_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickCategory != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            Category category = categoryList.get(pos);
                            itemClickCategory.onClickCategory(category.getCategory_id());
                        }
                    }
                }
            });
        }
    }

    public interface ItemClickCategory {
        void onClickCategory(int category_id);
    }

}
