package com.kabirnayeem99.paymentpaid.Activities.Work;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kabirnayeem99.paymentpaid.Database.DatabaseHelper;
import com.kabirnayeem99.paymentpaid.Database.Work;
import com.kabirnayeem99.paymentpaid.R;

public class AddNewWorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_work);
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
            Work work = new Work("Maria MSBP",
                    "22-Dec-2020",
                    "3000", "Maria");
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            databaseHelper.addToWork(work);
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}