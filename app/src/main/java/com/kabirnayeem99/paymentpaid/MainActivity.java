package com.kabirnayeem99.paymentpaid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button worksButton;
    Button paymentButton;
    Button exportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        clickWorksButton();
        clickPaymentButton();
        clickExportButton();
    }

    private void clickExportButton() {
        exportButton.setOnClickListener(v -> Toast.makeText(MainActivity.this, "Navigates to Export page", Toast.LENGTH_SHORT).show());
    }

    private void clickPaymentButton() {
        paymentButton.setOnClickListener(v -> Toast.makeText(MainActivity.this, "Navigates to Payment page", Toast.LENGTH_SHORT).show());
    }

    private void clickWorksButton() {
        worksButton.setOnClickListener(v -> Toast.makeText(MainActivity.this, "Navigates to Works page", Toast.LENGTH_SHORT).show());
    }

    private void initViews() {
        worksButton = findViewById(R.id.works_button);
        paymentButton = findViewById(R.id.payments_button);
        exportButton = findViewById(R.id.exports_button);
    }
}