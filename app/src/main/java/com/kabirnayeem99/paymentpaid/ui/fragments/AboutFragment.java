package com.kabirnayeem99.paymentpaid.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel;
import com.kabirnayeem99.paymentpaid.utils.CustomUtils;

import java.util.List;

public class AboutFragment extends Fragment {




    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
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
