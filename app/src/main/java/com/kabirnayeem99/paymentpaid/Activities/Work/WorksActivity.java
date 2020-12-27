package com.kabirnayeem99.paymentpaid.Activities.Work;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kabirnayeem99.paymentpaid.Adapters.WorkAdapter;
import com.kabirnayeem99.paymentpaid.Database.DatabaseHelper;
import com.kabirnayeem99.paymentpaid.Database.Work;
import com.kabirnayeem99.paymentpaid.R;

import java.util.List;

public class WorksActivity extends AppCompatActivity {

    RecyclerView workListRV;
    WorkAdapter workAdapter;
    List<Work> workList;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works);
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // creates new add new work option menu in the work activity
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.work_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        workListRV = findViewById(R.id.workListRV);
        databaseHelper = new DatabaseHelper(WorksActivity.this);
        workList = databaseHelper.getWorkList();

        workAdapter = new WorkAdapter(workList);
        workListRV.setLayoutManager(new LinearLayoutManager(this));
        workListRV.setAdapter(workAdapter);
    }
}