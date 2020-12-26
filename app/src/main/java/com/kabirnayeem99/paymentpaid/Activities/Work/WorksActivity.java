package com.kabirnayeem99.paymentpaid.Activities.Work;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kabirnayeem99.paymentpaid.Adapters.WorkAdapter;
import com.kabirnayeem99.paymentpaid.Database.Work;
import com.kabirnayeem99.paymentpaid.R;

import java.util.ArrayList;
import java.util.List;

public class WorksActivity extends AppCompatActivity {

    RecyclerView workListRV;
    WorkAdapter workAdapter;
    List<Work> workList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works);

        initViews();

        workList = new ArrayList<>();

        Work work1 = new Work(getResources().getString(R.string.sample_note_title),
                getResources().getString(R.string.sample_date),
                getResources().getString(R.string.sample_payment));
        Work work2 = new Work(getResources().getString(R.string.sample_note_title),
                getResources().getString(R.string.sample_date),
                getResources().getString(R.string.sample_payment));


        workList.add(work1);
        workList.add(work2);
        workAdapter = new WorkAdapter(workList);
        workListRV.setLayoutManager(new LinearLayoutManager(this));
        workListRV.setAdapter(workAdapter);

    }

    private void initViews() {
        workListRV = findViewById(R.id.workListRV);
    }
}