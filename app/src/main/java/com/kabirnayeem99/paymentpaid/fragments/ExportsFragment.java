package com.kabirnayeem99.paymentpaid.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.kabirnayeem99.paymentpaid.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults.length > 0) {
            for (int i : grantResults) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(requireActivity(), "You are granted the" + permissions[0], Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onRequestPermissionsResult: " + Arrays.toString(permissions));
                }
            }
        }
    }

    //importing database
    private void importDB() {
        if (checkRevokedReadStoragePermission()) Toast.makeText(requireActivity(),
                "You should enable the write storage permission",
                Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(requireActivity(),
                    "You can import the data",
                    Toast.LENGTH_SHORT).show();

        try {
            File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File data = Environment.getDataDirectory();


            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "PackageName"
                        + "//databases//" + "DatabaseName";
                String backupDBPath = "/BackupFolder/DatabaseName";
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();

                Log.d(TAG, "importDB: " + src);
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getActivity(), backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }


    //exporting database
    private void exportDB() {
        if (checkRevokedWriteStoragePermission()) {
            requestForPermission();
            Toast.makeText(requireActivity(),
                    "You should enable the write storage permission",
                    Toast.LENGTH_SHORT).show();
        }

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

    private boolean checkRevokedWriteStoragePermission() {
        return ActivityCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkRevokedReadStoragePermission() {
        return ActivityCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    private void requestForPermission() {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        if (checkRevokedWriteStoragePermission()) {
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (checkRevokedReadStoragePermission()) {
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        String[] array = new String[permissionsToRequest.size()];
        for (int j = 0; j < permissionsToRequest.size(); j++) {
            array[j] = permissionsToRequest.get(j);
        }

        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(requireActivity(), array,
                    PackageManager.PERMISSION_GRANTED);
        }
    }

}
