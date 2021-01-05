package com.kabirnayeem99.paymentpaid.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.adapters.PaymentAdapter;
import com.kabirnayeem99.paymentpaid.utils.DatabaseHelper;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    RecyclerView paymentListByMontRecyclyerView;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initViews();
        databaseHelper = new DatabaseHelper(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        ArrayList<Integer> totalPaymentListByMont = databaseHelper.getTotalPaymentByMonth();
        PaymentAdapter paymentAdapter = new PaymentAdapter(totalPaymentListByMont);
        paymentListByMontRecyclyerView.setLayoutManager(new LinearLayoutManager(this));
        paymentListByMontRecyclyerView.setAdapter(paymentAdapter);
    }

    private void initViews() {
        paymentListByMontRecyclyerView = findViewById(R.id.paymentListByMontRecyclyerView);
    }
}