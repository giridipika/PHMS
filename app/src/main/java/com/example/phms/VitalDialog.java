package com.example.phms;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class VitalDialog extends AppCompatDialogFragment {
    private EditText editBP;
    private EditText editGL;
    private EditText editCH;
    private VitalDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view)
                .setTitle("Edit")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String BP = editBP.getText().toString();
                        String CH = editCH.getText().toString();
                        String GL = editGL.getText().toString();
                        listener.applyTexts(BP, CH, GL);
                    }
                });
        editBP = view.findViewById(R.id.edit_BP);
        editCH = view.findViewById(R.id.edit_CH);
        editGL = view.findViewById(R.id.edit_GL);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (VitalDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement VitalDialogListener");
        }
    }

    public interface VitalDialogListener{
        void applyTexts(String BP, String CH, String GL);
    }
}
