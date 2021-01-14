package com.kabirnayeem99.paymentpaid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.WorkViewModel;
import com.kabirnayeem99.paymentpaid.activities.AddNewWorkActivity;
import com.kabirnayeem99.paymentpaid.adapters.WorkAdapter;


public class WorksFragment extends Fragment {
    private static final String TAG = "WorksFragment";

    RecyclerView rvWorkList;
    WorkAdapter workAdapter;
    FloatingActionButton fabAddNewWork;

    public WorksFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_works, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        initRecyclerView();

        fabAddNewWork.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddNewWorkActivity.class);
            startActivity(intent);
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(View view) {
        rvWorkList = view.findViewById(R.id.rv_work_list_works);
        fabAddNewWork = view.findViewById(R.id.fab_add_new_work_works);
    }

    private void initRecyclerView() {
        workAdapter = new WorkAdapter();
        WorkViewModel workViewModel = ViewModelProviders.of(this).get(WorkViewModel.class);
        workViewModel.getAllWorks().observe(requireActivity(), works -> {
            Log.d(TAG, "initRecyclerView: " + works.toString());
            workAdapter.setWorkList(works);
        });
        rvWorkList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvWorkList.setAdapter(workAdapter);
    }
}