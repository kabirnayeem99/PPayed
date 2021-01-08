package com.kabirnayeem99.paymentpaid.activities;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.models.Work;
import com.kabirnayeem99.paymentpaid.utils.DatabaseHelper;
import com.kabirnayeem99.paymentpaid.utils.Utils;

import java.util.Objects;

public class AddNewWorkActivity extends AppCompatActivity {

    private static final String TAG = "AddNewWorkActivity";

    TextInputLayout newWorkDialogWorkName;
    TextInputLayout newWorkDialogWorkPayment;
    TextInputLayout newWorkDialogWorkStudentName;
    DatePicker newWorkDialogWorkDate;
    DatabaseHelper databaseHelper;
    Work work;
    String workName;
    String studentName;
    String date;
    int payment;
    long noteId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_work);
        initViews();

        newWorkDialogWorkDate.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth)
                -> {
            // month is returning a value less than the actual value, so magic number 1 is added
            date = String.format("%s-%s-%s", year, Utils.padMonth(monthOfYear + 1),
                    Utils.padMonth(dayOfMonth));
            Log.d(TAG, "onCreate: month " + monthOfYear + 1);
        });
    }

    private void initViews() {
        newWorkDialogWorkName = findViewById(R.id.newWorkDialogWorkName);
        newWorkDialogWorkPayment = findViewById(R.id.newWorkDialogWorkPayment);
        newWorkDialogWorkStudentName = findViewById(R.id.newWorkDialogWorkStudentName);
        newWorkDialogWorkDate = findViewById(R.id.newWorkDialogWorkDate);
    }


    @Override
    protected void onPause() {
        try {
            noteId = saveToNoteDB();
        } catch (Exception e) {
            Log.d(TAG, "onPause: " + e);
        } finally {
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
}