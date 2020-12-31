package com.kabirnayeem99.paymentpaid.activities.work;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.database.DatabaseHelper;
import com.kabirnayeem99.paymentpaid.database.Work;

import java.util.Objects;

public class AddNewWorkActivity extends AppCompatActivity {

    TextInputLayout newWorkDialogWorkName, newWorkDialogWorkPayment, newWorkDialogWorkStudentName;
    DatePicker newWorkDialogWorkDate;
    Work work;


    String workName = "";
    String studentName = "";
    String date;
    int payment = 0;

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
            saveToNoteDB();
        }
        return super.onOptionsItemSelected(item);
    }


    private void saveToNoteDB() {

        // saves added note to the note database

        workName = Objects.requireNonNull(newWorkDialogWorkName.getEditText()).getText().toString();
        studentName = Objects.requireNonNull(newWorkDialogWorkStudentName.getEditText()).getText().toString();
        payment = Integer.parseInt(Objects.requireNonNull(newWorkDialogWorkPayment.getEditText()).getText().toString());


        work = new Work(workName, date, payment, studentName);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.addToWork(work);
        onBackPressed();
    }

}