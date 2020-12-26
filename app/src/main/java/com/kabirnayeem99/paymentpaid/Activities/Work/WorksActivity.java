package com.kabirnayeem99.paymentpaid.Activities.Work;

import android.os.Bundle;

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

    private void initViews() {
        workListRV = findViewById(R.id.workListRV);
        databaseHelper = new DatabaseHelper(WorksActivity.this);
        workList = databaseHelper.getWorkList();

        workAdapter = new WorkAdapter(workList);
        workListRV.setLayoutManager(new LinearLayoutManager(this));
        workListRV.setAdapter(workAdapter);
    }
}