package com.kabirnayeem99.paymentpaid.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.utils.DatabaseHelper;

public class PaymentActivity extends AppCompatActivity {

    TextView totalPaymentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initViews();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        totalPaymentTextView.setText(databaseHelper.getTotalPayment());

    }

    private void initViews() {
        totalPaymentTextView = findViewById(R.id.totalPaymentTextView);
    }
}