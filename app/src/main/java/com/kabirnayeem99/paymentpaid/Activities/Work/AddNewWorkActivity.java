package com.kabirnayeem99.paymentpaid.Activities.Work;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.kabirnayeem99.paymentpaid.Database.DatabaseHelper;
import com.kabirnayeem99.paymentpaid.Database.Work;
import com.kabirnayeem99.paymentpaid.R;

public class AddNewWorkActivity extends AppCompatActivity {

    TextInputLayout newWorkDialogWorkName, newWorkDialogWorkPayment, newWorkDialogWorkStudentName,
            newWorkDialogWorkDate;
    Work work;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_work);
        initViews();
    }

    private void initViews() {
        newWorkDialogWorkName = findViewById(R.id.newWorkDialogWorkName);
        newWorkDialogWorkPayment = findViewById(R.id.newWorkDialogWorkPayment);
        newWorkDialogWorkStudentName = findViewById(R.id.newWorkDialogWorkStudentName);
        newWorkDialogWorkDate = findViewById(R.id.newWorkDialogWorkDate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_new_work_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.newWorkDialogWorkCancelButton) {
            onBackPressed();
        } else if (item.getItemId() == R.id.newWorkDialogWorkSubmitButton) {

            String workName, studentName, date;
            int payment;

            workName = newWorkDialogWorkName.getEditText().getText().toString();
            studentName = newWorkDialogWorkStudentName.getEditText().getText().toString();
            payment = newWorkDialogWorkPayment.getEditText().getText().toString() == null ? 2 : Integer.parseInt(newWorkDialogWorkPayment.getEditText().getText().toString());
            date = newWorkDialogWorkDate.getEditText().getText().toString();

            work = new Work(workName, date, payment, studentName);
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            databaseHelper.addToWork(work);
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}