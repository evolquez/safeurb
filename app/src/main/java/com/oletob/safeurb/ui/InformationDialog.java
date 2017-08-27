package com.oletob.safeurb.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by evolquez on 8/26/17.
 */

public class InformationDialog extends DialogFragment {

    public static InformationDialog newInstance(String title, String message, boolean dataSaved){
        InformationDialog dialog = new InformationDialog();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        args.putBoolean("data_saved", dataSaved);

        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("");
        builder.setMessage("");

        if (getArguments() != null) {
            builder.setTitle(getArguments().getString("title"));
            builder.setMessage(getArguments().getString("message"));
        }

        // Button, just one. The button to close the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(getArguments().getBoolean("data_saved")){

                    startActivity(new Intent(getActivity(), HomeActivity.class));
                }

                InformationDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
