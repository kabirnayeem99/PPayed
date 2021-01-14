package com.kabirnayeem99.paymentpaid.activities;

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
import com.kabirnayeem99.paymentpaid.WorkViewModel;
import com.kabirnayeem99.paymentpaid.models.Work;
import com.kabirnayeem99.paymentpaid.utils.CustomUtils;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;

public class AddNewWorkActivity extends AppCompatActivity {

    private static final String TAG = "AddNewWorkActivity";

    TextInputLayout tilWorkName;
    TextInputLayout tilPayment;
    TextInputLayout tilStudentName;
    DatePicker dpDate;
    Work work;
    String workName;
    String studentName;
    String date;
    int payment;
    long noteId;

    WorkViewModel workViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_work);
        initViews();

        workViewModel = ViewModelProviders.of(AddNewWorkActivity.this).get(WorkViewModel.class);
        dpDate.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth)
                -> {
            // month is returning a value less than the actual value, so magic number 1 is added
            date = String.format("%s-%s-%s", year, CustomUtils.padMonth(monthOfYear + 1),
                    CustomUtils.padMonth(dayOfMonth));
        });
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
        // parses the integer value from the string
        payment = parseInt(requireNonNull(tilPayment.getEditText()).getText().toString());

        work = new Work(workName, date, payment, studentName);

        Log.d(TAG, "saveToNoteDB: work instance " + work.toString());

        workViewModel.insert(work);

        Toast.makeText(AddNewWorkActivity.this, "Work is saved", Toast.LENGTH_SHORT).show();

    }
}