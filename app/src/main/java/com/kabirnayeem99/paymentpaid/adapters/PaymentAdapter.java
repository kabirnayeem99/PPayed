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

import java.util.Map;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    private static final String TAG = "PaymentAdapter";

    private final Map<Integer, Integer> paymentListByMonth;

    public PaymentAdapter(Map<Integer, Integer> paymentListByMonth) {
        this.paymentListByMonth = paymentListByMonth;
    }

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

        // gets the data from the database, i.e. work title, submission date and payment amount.
        Integer paymentAmount = paymentListByMonth.get(position + 1);
        Log.d(TAG, "onBindViewHolder: position of adapter" + position);
        Log.d(TAG, "onBindViewHolder: \n" + paymentListByMonth.toString());

        // binds the data for each of the work got from the db to the existing adapter based on the
        // screen time of the lists item.
        String[] monthArrayList = new String[]{"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"};

        holder.paymentListMonthNameTextView.setText(String.format("%s %s", monthArrayList[position],
                CustomUtils.getCurrentYear()));

        assert paymentAmount != null;
        holder.paymentListPaymentAmountTextView.setText(String.format("%s",
                CustomUtils.formatNumber(paymentAmount.toString())));
    }

    @Override
    public int getItemCount() {
        // the size of the work list
        Log.d(TAG, "getItemCount: " + paymentListByMonth.size());
        return paymentListByMonth.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView paymentListMonthNameTextView;
        final TextView paymentListPaymentAmountTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            paymentListMonthNameTextView = itemView.findViewById(R.id.paymentListMonthNameTextView);
            paymentListPaymentAmountTextView = itemView.findViewById(R.id.paymentListPaymentAmountTextView);
        }
    }
}
