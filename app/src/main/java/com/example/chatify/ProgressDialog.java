package com.example.chatify;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.TextView;

import com.redmatrix.chatify.R;

public class ProgressDialog {
    Activity activity;
    AlertDialog dialog;

    public ProgressDialog(Activity activity) {
        this.activity = activity;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(R.layout.progress);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setMessage(String message) {
        TextView textView = dialog.findViewById(R.id.textviewwait);
        textView.setText(message);
    }
}
