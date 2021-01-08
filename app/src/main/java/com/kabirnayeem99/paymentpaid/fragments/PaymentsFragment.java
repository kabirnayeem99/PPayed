package com.kabirnayeem99.paymentpaid.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.adapters.PaymentAdapter;
import com.kabirnayeem99.paymentpaid.utils.DatabaseHelper;
import com.kabirnayeem99.paymentpaid.utils.Utils;

import java.util.Map;


public class PaymentsFragment extends Fragment {
    private static final String TAG = "PaymentsFragment";
    RecyclerView paymentListByMontRecyclerView;
    DatabaseHelper databaseHelper;
    TextView paymentListTotal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initViews(view);

        databaseHelper = new DatabaseHelper(getActivity());
        paymentListTotal.setText(Utils.formatNumber(databaseHelper.getTotalPayment()));

        initRecyclerView(databaseHelper);

        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(@NonNull View view) {
        paymentListByMontRecyclerView = view.findViewById(R.id.paymentListByMontRecyclerView);
        paymentListTotal = view.findViewById(R.id.paymentListTotal);
    }

    private void initRecyclerView(DatabaseHelper databaseHelper) {
        Map<Integer, Integer> totalPaymentListByMont = databaseHelper.getTotalPaymentByMonth();

        Log.d(TAG, "initRecyclerView: " + totalPaymentListByMont);

        PaymentAdapter paymentAdapter = new PaymentAdapter(totalPaymentListByMont);

        paymentListByMontRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        paymentListByMontRecyclerView.setAdapter(paymentAdapter);
    }

}