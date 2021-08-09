package com.example.phms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button btnNotes;
    Button btnDiet;
    Button btnExercise;
    Button btnMedication;
    Button btnVital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnNotes = findViewById(R.id.notesButton);
        btnNotes.setOnClickListener(v-> {
            Intent intent = new Intent(this, NotesActivity.class);
            startActivity(intent);
        });

        btnDiet = findViewById(R.id.dietButton);
        btnDiet.setOnClickListener(v-> {
            Intent intent = new Intent(this, DietActivity.class);
            startActivity(intent);
        });

        btnExercise = findViewById(R.id.exerciseButton);
        btnExercise.setOnClickListener(v-> {
            Intent intent = new Intent(this, ExerciseActivity.class);
            startActivity(intent);
        });

        btnMedication = findViewById(R.id.medicationButton);
        btnMedication.setOnClickListener(v-> {
            Intent intent = new Intent(this, MedicationActivity.class);
            startActivity(intent);
        });

        btnVital = findViewById(R.id.vitalsButton);
        btnVital.setOnClickListener(v-> {
            Intent intent = new Intent(this, VitalTabsActivity.class);
            startActivity(intent);
        });
    }
}