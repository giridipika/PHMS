package com.example.phms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class sundayActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;
    String time = "";
    Button timebutton,abutton;
    int hour, minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunday);

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);
        timebutton = findViewById(R.id.timebutton);
        abutton = findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                additem();

            }
        });

        abutton = findViewById(R.id.button1);
        abutton.setOnClickListener(v-> {
            Intent intent = new Intent(this, com.example.phms.ExerciseActivity.class);
            startActivity(intent);
        });


        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();

    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long a) {
                Context context = getApplicationContext();
                Toast.makeText(context,"Item removed",Toast.LENGTH_LONG).show();

                items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }


    private void additem() {
        EditText input = findViewById(R.id.editText2);
        String itemText = input.getText().toString();
        String msg="";
        if(!(itemText.equals(""))) {
            if(!(time.equals(""))){
                msg = time + "   ";
                msg = msg + input.getText().toString();
                itemsAdapter.add(msg);
            }
            else{
                Toast.makeText(getApplicationContext(),"please enter select time...", Toast.LENGTH_LONG).show();
            }
            //itemsAdapter.add(itemText);
            //input.setText("");
        }
        else{
            Toast.makeText(getApplicationContext(),"please enter text...", Toast.LENGTH_LONG).show();
        }




    }


    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timepicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timebutton.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
                time = String.format(Locale.getDefault(),"%02d:%02d",hour,minute);

            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,style, onTimeSetListener,hour,minute,true);
        timePickerDialog.setTitle("Select time");
        timePickerDialog.show();
    }
}