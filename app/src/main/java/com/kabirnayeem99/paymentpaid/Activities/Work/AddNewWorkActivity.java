package com.kabirnayeem99.paymentpaid.Activities.Work;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.kabirnayeem99.paymentpaid.Database.DatabaseHelper;
import com.kabirnayeem99.paymentpaid.Database.Work;
import com.kabirnayeem99.paymentpaid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNewWorkActivity extends AppCompatActivity {

    TextInputLayout newWorkDialogWorkName, newWorkDialogWorkPayment, newWorkDialogWorkStudentName;
    DatePicker newWorkDialogWorkDate;
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

            String workName = "";
            String studentName = "";
            String stringDate = "2020-12-12";
            Date date = new Date();
            int payment = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);

            workName = newWorkDialogWorkName.getEditText().getText().toString();
            studentName = newWorkDialogWorkStudentName.getEditText().getText().toString();
            payment = Integer.parseInt(newWorkDialogWorkPayment.getEditText().getText().toString());
            stringDate = newWorkDialogWorkDate.toString();
            try {
                date = sdf.parse(stringDate);
                System.out.println(String.format("date: %s", date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            work = new Work(workName, date, payment, studentName);
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            databaseHelper.addToWork(work);
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}