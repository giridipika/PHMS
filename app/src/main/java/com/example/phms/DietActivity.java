package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class DietActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.text_entry, null);

        final EditText input1 = new EditText(this);
        final EditText input2 = new EditText(this);


        input1.setText("DefaultValue", TextView.BufferType.EDITABLE);
        input2.setText("DefaultValue", TextView.BufferType.EDITABLE);
        //if add note icon is selected open the Notes editor
        if(item.getItemId() == R.id.add_vital){
            AlertDialog show = new AlertDialog.Builder(DietActivity.this)
                    .setTitle("Enter value")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i ) {
                                    Log.i("AlertDialog","TextEntry 1 Entered "+input1.getText().toString());
                                    Log.i("AlertDialog","TextEntry 2 Entered "+input2.getText().toString());
                                }
                            }
                    )
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //set up menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_vital_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
