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

import com.example.phms.ui.login.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    // Declare all variable here
    EditText emailID, password;
    Button btnSignIn;
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

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailID = (EditText) findViewById(R.id.SignInEmail);
                password = (EditText) findViewById(R.id.SignInPassword);

                email = emailID.getText().toString();
                pwd = password.getText().toString();

                try {
                    signin(email, pwd);
                } catch (Exception Easd){
                    Toast.makeText(MainActivity.this,"Log in Failed, try again!",Toast.LENGTH_LONG).show();
                    Log.i("Error at login","Error");
                }
            }
//        } {
//
//            Intent intent = new Intent(this, HomeActivity.class);
//            startActivity(intent);
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }
    private void signin(String email, String password){
        // This means current activity
        userSignIn.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // Login is successful; take the user ahead to the Home Page
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Welcome !",Toast.LENGTH_SHORT).show();
                    // Saving user's email that will be user later to display on profile section; since login screen is the only way user can get into the homepage
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.project_id), Context.MODE_PRIVATE); // To open in private mode, can only be seen
                    // by our application
                    SharedPreferences.Editor editor = sharedPref.edit(); // Opening the file to edit
                    editor.putString("Email",email); // Putting in the string, Now Email keyword in SharedPref is associated with email entered by the user
                    editor.apply(); // Applying the changes
                    openhomepage();
                }
                else{
                    Toast.makeText(MainActivity.this,"Log in Failed. Try again.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void openhomepage(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}


