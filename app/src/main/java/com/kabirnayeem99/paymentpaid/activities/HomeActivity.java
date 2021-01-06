package com.kabirnayeem99.paymentpaid.activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kabirnayeem99.paymentpaid.R;

public class HomeActivity extends AppCompatActivity {

    Button worksButton;
    Button paymentButton;
    Button exportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();

        clickWorksButton();
        clickExportButton();
        clickPaymentButton();
    }

    private void clickExportButton() {
        exportButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ExportActivity.class);
            startActivity(intent);
        });
    }

    private void clickPaymentButton() {
        paymentButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PaymentActivity.class);
            startActivity(intent);
        });
    }

    private void clickWorksButton() {
        worksButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, WorksActivity.class);
            startActivity(intent);
        });
    }

    private void initViews() {
        worksButton = findViewById(R.id.works_button);
        paymentButton = findViewById(R.id.payments_button);
        exportButton = findViewById(R.id.exports_button);
    }

}