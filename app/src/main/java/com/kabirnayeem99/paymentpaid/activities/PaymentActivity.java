package com.kabirnayeem99.paymentpaid.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.adapters.PaymentAdapter;
import com.kabirnayeem99.paymentpaid.utils.DatabaseHelper;

import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    RecyclerView paymentListByMontRecyclyerView;
    DatabaseHelper databaseHelper;
    TextView paymentListTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initViews();
        databaseHelper = new DatabaseHelper(this);
        paymentListTotal.setText(databaseHelper.getTotalPayment());
        initRecyclerView();
    }

    private void initRecyclerView() {
        Map<Integer, Integer> totalPaymentListByMont = databaseHelper.getTotalPaymentByMonth();
        PaymentAdapter paymentAdapter = new PaymentAdapter(totalPaymentListByMont);
        paymentListByMontRecyclyerView.setLayoutManager(new LinearLayoutManager(this));
        paymentListByMontRecyclyerView.setAdapter(paymentAdapter);
    }

    private void initViews() {
        paymentListByMontRecyclyerView = findViewById(R.id.paymentListByMontRecyclyerView);
        paymentListTotal = findViewById(R.id.paymentListTotal);
    }
}