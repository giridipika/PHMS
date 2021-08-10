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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class DietActivity extends AppCompatActivity {

    String nameIn;
    String calIn;
    String food;
    public static ArrayList<String> temp = new ArrayList<>();
    public static ArrayList<String> foods = new ArrayList<>();
    public static ArrayAdapter foodArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        ListView foodList = (ListView) findViewById(R.id.dietList);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.food", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("food", null);
        //foods =  new ArrayList<>(set);

        if ( set == null){
            foods.add("sandwich    2000");
        } else {
            foods = new ArrayList<>(set);
        }
        foodArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foods);
        foodList.setAdapter(foodArrayAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        LayoutInflater factory = LayoutInflater.from(this);
        //final View textEntryView = factory.inflate(R.layout.text_entry, null);

        final EditText input1 = new EditText(this);
        final EditText input2 = new EditText(this);

        final TextView name = new TextView(this);
        final TextView calories = new TextView(this);

        name.setText("name of food");
        calories.setText("calories");


        input1.setText(" ");
        input2.setText(" ");
        View view = new View(this);
        LinearLayout layout = new LinearLayout(this);

        LinearLayout horiLayout1 = new LinearLayout(this);
        LinearLayout horiLayout2 = new LinearLayout(this);

        horiLayout1.addView(name);
        horiLayout1.addView(input1);

        horiLayout2.addView(calories);
        horiLayout2.addView(input2);


        layout.addView(horiLayout1);
        layout.addView(horiLayout2);



        layout.setOrientation(LinearLayout.VERTICAL);

        temp.clear();
        temp.addAll(foods);


        input1.setText("DefaultValue", TextView.BufferType.EDITABLE);
        input2.setText("DefaultValue", TextView.BufferType.EDITABLE);
        //if add note icon is selected open the Notes editor
        if(item.getItemId() == R.id.add_vital){
            AlertDialog show = new AlertDialog.Builder(DietActivity.this)
                    .setTitle("Enter value")
                    .setView(layout)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i ) {
                                    Log.i("AlertDialog","TextEntry 1 Entered "+input1.getText().toString());
                                    Log.i("AlertDialog","TextEntry 2 Entered "+input2.getText().toString());
                                    food = input1.getText().toString();
                                    food = food + "    ";
                                    food = food + input2.getText().toString();
                                    food = food + "cals";
                                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.food", Context.MODE_PRIVATE);
                                    HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("food", null);
                                    //set.add(food);
                                    if(set==null){
                                        foods.add(food);
                                        set = new HashSet<>(foods);
                                    }else{
                                        foods = new ArrayList<>(set);
                                        foods.add(food);
                                        set = new HashSet<>(foods);
                                    }
                                    //foods = new ArrayList<>(set);
                                    //foods.add(food);
                                    //set = new HashSet<>(foods);
                                    temp.add(food);
                                    foodArrayAdapter.clear();
                                    foodArrayAdapter.addAll(temp);
                                    foodArrayAdapter.notifyDataSetChanged();
                                    sharedPreferences.edit().putStringSet("food", set).apply();
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
