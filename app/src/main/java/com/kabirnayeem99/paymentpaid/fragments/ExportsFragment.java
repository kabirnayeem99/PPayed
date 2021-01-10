package com.kabirnayeem99.paymentpaid.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kabirnayeem99.paymentpaid.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class ExportsFragment extends Fragment {
    private static final String TAG = "ExportsFragment";


    public ExportsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exports, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button exportButton = view.findViewById(R.id.export_button);
        exportButton.setOnClickListener(v -> exportDB());
        super.onViewCreated(view, savedInstanceState);
    }

// --Commented out by Inspection START (1/9/2021 1:12 PM):
//    //importing database
//    private void importDB() {
//
//
//        try {
//            File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
//            File data = Environment.getDataDirectory();
//
//
//            if (sd.canWrite()) {
//                String currentDBPath = "//data//" + "PackageName"
//                        + "//databases//" + "DatabaseName";
//                String backupDBPath = "/BackupFolder/DatabaseName";
//                File backupDB = new File(data, currentDBPath);
//                File currentDB = new File(sd, backupDBPath);
//
//                FileChannel src = new FileInputStream(currentDB).getChannel();
//                FileChannel dst = new FileOutputStream(backupDB).getChannel();
//
//                Log.d(TAG, "importDB: " + src);
//                dst.transferFrom(src, 0, src.size());
//                src.close();
//                dst.close();
//                Toast.makeText(getActivity(), backupDB.toString(),
//                        Toast.LENGTH_LONG).show();
//
//            }
//        } catch (Exception e) {
//
//            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
//                    .show();
//
//        }
//    }
// --Commented out by Inspection STOP (1/9/2021 1:12 PM)

    //exporting database
    private void exportDB() {

        try {
            File sd;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                sd = Environment.getStorageDirectory();
            } else {
                sd = Environment.getExternalStorageDirectory();
            }
            File data = Environment.getDataDirectory();

            Log.d(TAG, String.format("exportDB: %s %s", sd, data));

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "PackageName"
                        + "//databases//" + "DatabaseName";
                Log.d(TAG, "exportDB: currentDBpath" + currentDBPath);
                String backupDBPath = "/BackupFolder/DatabaseName";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getActivity(), backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getActivity(), "Can't write to the storage", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }

}
