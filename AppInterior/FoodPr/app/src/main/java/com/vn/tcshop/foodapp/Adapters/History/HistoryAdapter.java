package com.vn.tcshop.foodapp.Adapters.History;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.tcshop.foodapp.Adapters.Products.ProductAdapter;
import com.vn.tcshop.foodapp.Models.Payment;
import com.vn.tcshop.foodapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<Payment> paymentsList;
    private ItemClickListener onClickItem;

    public void setItemClickListener(ItemClickListener onClickItem) {
        this.onClickItem = onClickItem;
    }

    public HistoryAdapter(List<Payment> paymentsList) {
        this.paymentsList = paymentsList;
    }

    public void setPaymentsList(List<Payment> paymentsList) {
        this.paymentsList = paymentsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Payment payment = paymentsList.get(position);
        holder.bind(payment);
    }

    @Override
    public int getItemCount() {
        return paymentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date, total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.history_date_item);
            total = itemView.findViewById(R.id.history_total_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION && onClickItem != null) {
                        Payment payment = paymentsList.get(pos);
                        onClickItem.onClickItem(payment.getPayment_id());
                    }
                }
            });
        }

        public void bind(Payment payment) {
            // Hiển thị giá sản phẩm với định dạng ngăn cách hàng nghìn
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String priceFormatted = decimalFormat.format(payment.getTotal());
            date.setText(payment.getPayment_date());
            total.setText(priceFormatted + "đ");
        }
    }

    public interface ItemClickListener {
        void onClickItem(int paymentId);
    }
}
