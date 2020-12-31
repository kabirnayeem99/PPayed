package com.kabirnayeem99.paymentpaid.activities;

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
import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.utils.DatabaseHelper;
import com.kabirnayeem99.paymentpaid.models.Work;

import java.util.Objects;

public class AddNewWorkActivity extends AppCompatActivity {

    TextInputLayout newWorkDialogWorkName, newWorkDialogWorkPayment, newWorkDialogWorkStudentName;
    DatePicker newWorkDialogWorkDate;
    DatabaseHelper databaseHelper;
    Work work;
    String workName = "";
    String studentName = "";
    String date = "2020-12-12";
    int payment;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_work);
        initViews();

        newWorkDialogWorkDate.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth)
                -> date = String.format("%s-%s-%s", year, monthOfYear, dayOfMonth));
    }

    private void initViews() {
        newWorkDialogWorkName = findViewById(R.id.newWorkDialogWorkName);
        newWorkDialogWorkPayment = findViewById(R.id.newWorkDialogWorkPayment);
        newWorkDialogWorkStudentName = findViewById(R.id.newWorkDialogWorkStudentName);
        newWorkDialogWorkDate = findViewById(R.id.newWorkDialogWorkDate);

        newWorkDialogWorkName.setErrorEnabled(true);
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
            if (true) {
                if (saveToNoteDB() > 0) {
                    onBackPressed();
                }
            } else {
                showErrorMessage();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private long saveToNoteDB() {

        // saves added note to the note database

        workName = Objects.requireNonNull(newWorkDialogWorkName.getEditText()).getText().toString();
        studentName = Objects.requireNonNull(newWorkDialogWorkStudentName.getEditText()).getText().toString();
        payment = Integer.parseInt(Objects.requireNonNull(newWorkDialogWorkPayment.getEditText()).getText().toString());

        work = new Work(workName, date, payment, studentName);

        databaseHelper = new DatabaseHelper(this);
        return databaseHelper.addToWork(work);
    }


    private void showErrorMessage() {
        newWorkDialogWorkName.setError("Student_name-WORK_NAME");
        newWorkDialogWorkPayment.setError("2200");
        newWorkDialogWorkStudentName.setError("Naimul Kabir");
        newWorkDialogWorkName.setErrorEnabled(workName.length() > 5);
        newWorkDialogWorkPayment.setErrorEnabled(payment > 5);
        newWorkDialogWorkStudentName.setErrorEnabled(studentName.length() > 3);
    }


}