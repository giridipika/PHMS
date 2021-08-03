package com.example.phms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VitalActivity extends AppCompatActivity implements VitalDialog.VitalDialogListener {
    private TextView tvBP;
    private TextView tvGL;
    private TextView tvCH;
    private TextView tvBPA;
    private TextView tvGLA;
    private TextView tvCHA;
    private Button buttonEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital);

        tvBPA = (TextView) findViewById(R.id.tvBPA);
        tvGLA = (TextView) findViewById(R.id.tvGLA);
        tvCHA = (TextView) findViewById(R.id.tvCHA);
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }
    public void openDialog(){
        VitalDialog vitaldialog = new VitalDialog();
        vitaldialog.show(getSupportFragmentManager(), "vital dialog");
    }

    @Override
    public void applyTexts(String BP, String CH, String GL) {
        tvBPA.setText(BP);
        tvCHA.setText(CH);
        tvGLA.setText(GL);
    }
}