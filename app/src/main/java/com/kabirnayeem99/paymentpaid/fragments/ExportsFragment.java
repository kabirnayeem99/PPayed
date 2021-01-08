package com.kabirnayeem99.paymentpaid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kabirnayeem99.paymentpaid.R;

public class ExportsFragment extends Fragment {


    public ExportsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exports, container, false);
    }
}