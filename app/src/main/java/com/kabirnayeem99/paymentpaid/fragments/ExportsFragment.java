package com.kabirnayeem99.paymentpaid.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.WorkViewModel;
import com.kabirnayeem99.paymentpaid.utils.CustomUtils;

import java.util.ArrayList;
import java.util.List;

public class ExportsFragment extends Fragment {




    public ExportsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exports, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        WorkViewModel workViewModel = ViewModelProviders.of(requireActivity()).get(WorkViewModel.class);

        workViewModel.getTotalPaymentByMonth(CustomUtils.getCurrentYear()).observe(requireActivity(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
//                new ShowChartAsyncTask().execute(integers);
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }




}
