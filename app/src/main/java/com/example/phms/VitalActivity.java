package com.example.phms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VitalActivity extends AppCompatActivity {
    private TextView tvBP;
    private TextView tvGL;
    private TextView tvCH;
    private Button buttonEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital);

        tvBP = (TextView) findViewById(R.id.tvBP);
        tvGL = (TextView) findViewById(R.id.tvGL);
        tvCH = (TextView) findViewById(R.id.tvCH);
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
}