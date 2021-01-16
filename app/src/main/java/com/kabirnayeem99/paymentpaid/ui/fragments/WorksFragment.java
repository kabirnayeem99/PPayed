package com.kabirnayeem99.paymentpaid.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.adapters.WorkAdapter;
import com.kabirnayeem99.paymentpaid.data.db.entities.Work;
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel;
import com.kabirnayeem99.paymentpaid.ui.activities.WorkDetailsActivity;


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
            Intent intent = new Intent(getActivity(), WorkDetailsActivity.class);
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                workViewModel.delete(workAdapter.getWorkByPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(requireActivity(), "This note has been deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rvWorkList);

        workAdapter.setOnClickListener(new WorkAdapter.OnClickListener() {
            @Override
            public void onItemClick(Work work) {
                Intent intent = new Intent(requireActivity(), WorkDetailsActivity.class);
                intent.putExtra(WorkDetailsActivity.EXTRA_ID, work.getId());
                intent.putExtra(WorkDetailsActivity.EXTRA_WORK_NAME, work.getName());
                intent.putExtra(WorkDetailsActivity.EXTRA_STUDENT_NAME, work.getStudentName());
                intent.putExtra(WorkDetailsActivity.EXTRA_DATE, work.getDate());
                intent.putExtra(WorkDetailsActivity.EXTRA_PAYMENT, work.getPayment());
                startActivity(intent);
            }
        });
    }
}