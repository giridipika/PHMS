package com.example.phms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ExerciseActivity extends AppCompatActivity {
    Button abutton, bbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        abutton = findViewById(R.id.button2);
        bbutton = findViewById(R.id.button9);

        abutton.setOnClickListener(v-> {
            Intent intent = new Intent(this, sundayActivity.class);
            startActivity(intent);
        });



        bbutton.setOnClickListener(v-> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
    }
}