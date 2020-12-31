package com.kabirnayeem99.paymentpaid.activities.work;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.adapters.WorkAdapter;
import com.kabirnayeem99.paymentpaid.database.DatabaseHelper;
import com.kabirnayeem99.paymentpaid.database.Work;

import java.util.List;

public class WorksActivity extends AppCompatActivity {

    RecyclerView workListRV;
    WorkAdapter workAdapter;
    List<Work> workList;
    DatabaseHelper databaseHelper;
    FloatingActionButton fabAddNewWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works);
        initViews();

        fabAddNewWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorksActivity.this, AddNewWorkActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        workListRV = findViewById(R.id.workListRV);
        fabAddNewWork = findViewById(R.id.fabAddNewWork);

        databaseHelper = new DatabaseHelper(WorksActivity.this);
        workList = databaseHelper.getWorkListSortedByMonth(11);

        workAdapter = new WorkAdapter(workList);
        workListRV.setLayoutManager(new LinearLayoutManager(this));
        workListRV.setAdapter(workAdapter);

    }
}