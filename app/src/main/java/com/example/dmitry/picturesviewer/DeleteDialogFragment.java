package com.example.dmitry.picturesviewer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.io.File;

public class DeleteDialogFragment extends DialogFragment {


    public DeleteDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String path = getArguments().getString("path");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("Important message!!!").
                setMessage("Do you want delete this photo?").
                setIcon(R.drawable.ic_warning_black_24dp).
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File file = new File(path);
                        file.delete();
                    }
                }).
                setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();
    }
}
