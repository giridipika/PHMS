package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    // Declare all variable here
    EditText emailID, password;
    Button btnSignIn;
    Button btnHome;
    TextView tvSignUp;
    String email, pwd;
    private FirebaseAuth userSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userSignIn = FirebaseAuth.getInstance();

        btnSignIn = findViewById(R.id.SignInButton);
        tvSignUp = findViewById(R.id.textViewSignUp);

        btnHome = findViewById(R.id.homeButton);
        btnHome.setOnClickListener(v-> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
    }
}

