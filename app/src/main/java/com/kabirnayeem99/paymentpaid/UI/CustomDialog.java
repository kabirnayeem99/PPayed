package com.kabirnayeem99.paymentpaid.UI;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import com.kabirnayeem99.paymentpaid.R;

public class CustomDialog {
    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_new_work);

    }
}
