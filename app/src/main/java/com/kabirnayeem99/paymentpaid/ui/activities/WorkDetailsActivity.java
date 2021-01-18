package com.kabirnayeem99.paymentpaid.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputLayout;
import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.data.db.entities.Work;
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel;
import com.kabirnayeem99.paymentpaid.utils.CustomUtils;

import static java.util.Objects.requireNonNull;

public class WorkDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.kabirnayeem99.paymentpaid.ui.activities.EXTRA_ID";
    public static final String EXTRA_WORK_NAME = "com.kabirnayeem99.paymentpaid.ui.activities.EXTRA_WORK_NAME";
    public static final String EXTRA_PAYMENT = "com.kabirnayeem99.paymentpaid.ui.activities.EXTRA_PAYMENT";
    public static final String EXTRA_STUDENT_NAME = "com.kabirnayeem99.paymentpaid.ui.activities.EXTRA_STUDENT_NAME";
    public static final String EXTRA_DATE = "com.kabirnayeem99.paymentpaid.ui.activities.EXTRA_DATE";
    private static final String TAG = "WorkDetailsActivity";
    TextInputLayout tilWorkName;
    TextInputLayout tilPayment;
    TextInputLayout tilStudentName;
    DatePicker dpDate;
    Work work;
    String workName;
    String studentName;
    String date = String.format("%s-%s-%s", CustomUtils.getCurrentYear(),
            CustomUtils.padMonth(CustomUtils.getCurrentMonth() + 1),
            CustomUtils.padMonth(CustomUtils.getCurrentDay()));
    String paymentString;
    int month = CustomUtils.getCurrentMonth() + 1;
    int year = CustomUtils.getCurrentYear();
    Intent intent;
    int id = -1;
    WorkViewModel workViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_work);
        initViews();

        workViewModel = ViewModelProviders.of(WorkDetailsActivity.this).get(WorkViewModel.class);

        Log.d(TAG, "onCreate: default date" + date);
        assert dpDate != null;
        dpDate.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth)
                -> {
            this.year = year;
            this.month = monthOfYear + 1;
            // month is returning a value less than the actual value, so magic number 1 is added
            date = String.format("%s-%s-%s", year, CustomUtils.padMonth(monthOfYear + 1),
                    CustomUtils.padMonth(dayOfMonth));
        });

        // manipulate views

        intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            requireNonNull(tilWorkName.getEditText()).setText(intent.getStringExtra(EXTRA_WORK_NAME));
            tilWorkName.getEditText().setText(intent.getStringExtra(EXTRA_WORK_NAME));
            requireNonNull(tilPayment.getEditText()).setText(String.valueOf(intent.getIntExtra(EXTRA_PAYMENT, 0)));
            requireNonNull(tilStudentName.getEditText()).setText(intent.getStringExtra(EXTRA_STUDENT_NAME));
            String date = intent.getStringExtra(EXTRA_DATE);
        }
    }

    private void initViews() {
        tilWorkName = findViewById(R.id.til_work_name_add_new_work);
        tilPayment = findViewById(R.id.til_work_payment_add_new_work);
        tilStudentName = findViewById(R.id.til_student_name_add_new_work);
        dpDate = findViewById(R.id.dp_date_add_new_work);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveToNoteDB();
    }

    private void saveToNoteDB() {
        // saves added note to the note database


        // requireNonNull ensures that the field is guaranteed be non-null.
        workName = requireNonNull(tilWorkName.getEditText()).getText().toString();
        studentName = requireNonNull(tilStudentName.getEditText()).getText().toString();
        paymentString = requireNonNull(tilPayment.getEditText()).getText().toString();


        if (!workName.trim().isEmpty() && !paymentString.trim().isEmpty() && !studentName.trim().isEmpty() && !date.trim().isEmpty()) {
            work = new Work(workName, date, month, year, Integer.parseInt(paymentString), studentName);
            id = intent.getIntExtra(EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Work can't be update", Toast.LENGTH_SHORT).show();
                workViewModel.insert(work);
                Toast.makeText(WorkDetailsActivity.this, "Work is saved", Toast.LENGTH_SHORT).show();
            } else {
                work.setId(id);
                workViewModel.update(work);
            }

        } else {
            Toast.makeText(this, "Work was not saved", Toast.LENGTH_SHORT).show();
        }

    }
}