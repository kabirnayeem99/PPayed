package com.kabirnayeem99.paymentpaid.Activities.Work;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.kabirnayeem99.paymentpaid.Database.DatabaseHelper;
import com.kabirnayeem99.paymentpaid.Database.Work;
import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.Utils;

import java.util.Date;
import java.util.Objects;

public class AddNewWorkActivity extends AppCompatActivity {

    TextInputLayout newWorkDialogWorkName, newWorkDialogWorkPayment, newWorkDialogWorkStudentName;
    DatePicker newWorkDialogWorkDate;
    Work work;
    String workName = "";
    String studentName = "";
    String stringDate;
    Date date;
    int payment = 0;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.newWorkDialogWorkCancelButton) {
            onBackPressed();
        } else if (item.getItemId() == R.id.newWorkDialogWorkSubmitButton) {


            workName = Objects.requireNonNull(newWorkDialogWorkName.getEditText()).getText().toString();
            studentName = Objects.requireNonNull(newWorkDialogWorkStudentName.getEditText()).getText().toString();
            payment = Integer.parseInt(Objects.requireNonNull(newWorkDialogWorkPayment.getEditText()).getText().toString());
            stringDate = newWorkDialogWorkDate.toString();
            newWorkDialogWorkDate.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
                String stringDate = String.format("%s-%s-%s", year, monthOfYear, dayOfMonth);
                date = Utils.formatStringToDate(stringDate);
            });

            work = new Work(workName, date, payment, studentName);
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            databaseHelper.addToWork(work);
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}