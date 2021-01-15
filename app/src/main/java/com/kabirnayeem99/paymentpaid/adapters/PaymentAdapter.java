package com.kabirnayeem99.paymentpaid.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.utils.CustomUtils;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    private static final String TAG = "PaymentAdapter";


    List<Integer> monthlyPaymentList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // when there is not view holder, it creates a new view holder,
        // which later will be updated from the bound data.

        // Here the layout inflater takes the work list item layout file
        // and turns it into a view
        // later these views are used to draw on screen
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_payment,
                parent, false);

        // a new view holder is created based on the view of work list item.
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // binds the new data to the view holder without creating new view holder to reduce cpu
        // as well as the memory usage.

        if (monthlyPaymentList.size() > 0) {
            holder.paymentListMonthNameTextView.setText(CustomUtils.getCurrentMonthName(position));
            holder.paymentListPaymentAmountTextView.setText(String.format("%s", monthlyPaymentList.get(position)));
        }
        String monthlyPaymentListString = monthlyPaymentList.toString();
        Log.d(TAG, "onBindViewHolder: " + monthlyPaymentListString);
    }

    @Override
    public int getItemCount() {
        return monthlyPaymentList.size();
    }

    public void setMonthlyPaymentList(List<Integer> integers) {
        this.monthlyPaymentList = integers;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView paymentListMonthNameTextView;
        final TextView paymentListPaymentAmountTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            paymentListMonthNameTextView = itemView.findViewById(R.id.tv_payment_month_list_item_payment);
            paymentListPaymentAmountTextView = itemView.findViewById(R.id.tv_payment_amount_list_item_payment);
        }
    }
}
