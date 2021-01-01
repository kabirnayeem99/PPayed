package com.kabirnayeem99.paymentpaid.activities;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.models.Work;
import com.kabirnayeem99.paymentpaid.utils.DatabaseHelper;

import java.util.Objects;

public class AddNewWorkActivity extends AppCompatActivity {

    private static final String TAG = "AddNewWorkActivity";

    TextInputLayout newWorkDialogWorkName, newWorkDialogWorkPayment, newWorkDialogWorkStudentName;
    DatePicker newWorkDialogWorkDate;
    DatabaseHelper databaseHelper;
    Work work;
    String workName = "";
    String studentName = "";
    String date = "2020-12-12";
    int payment;
    long noteId;

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
    protected void onPause() {

        try {
            noteId = saveToNoteDB();
            if (noteId <= 0) {
                super.onResume();
            } else {
                super.onPause();

            }
        } catch (Exception e) {
            Log.d(TAG, "onPause: " + e);
        }
        finally {
            super.onPause();
        }
    }

    private long saveToNoteDB() {

        // saves added note to the note database

        workName = Objects.requireNonNull(newWorkDialogWorkName.getEditText()).getText().toString();
        studentName = Objects.requireNonNull(newWorkDialogWorkStudentName.getEditText()).getText().toString();
        payment = Integer.parseInt(Objects.requireNonNull(newWorkDialogWorkPayment.getEditText()).getText().toString());

        work = new Work(workName, date, payment, studentName);

        Log.d(TAG, "saveToNoteDB: " + work.toString());

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