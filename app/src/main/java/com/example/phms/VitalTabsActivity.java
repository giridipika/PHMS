package com.example.phms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;


public class VitalTabsActivity extends AppCompatActivity {


    static int position;
    TabLayout tabLayout;
    private Toolbar toolbar;
    public ArrayAdapter<String> bpaArrayAdapter;
    ArrayAdapter<String> glaArrayAdapter;
    ArrayAdapter<String> chlArrayAdapter;
    ArrayList<String> arrayL;
    public String[] names = {"BPA","GLA","CHL"};
    public String[] units = {"mm Hg","mg/dL","mg/dL"};
    public static ArrayList<String> bpa = new ArrayList<>();
    public static ArrayList<String> gla = new ArrayList<>();
    public static ArrayList<String> chl = new ArrayList<>();
    public static ArrayList<String> temp = new ArrayList<>();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //set up menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_vital_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        final EditText input = new EditText(this);
        temp.clear();
        switch(position){
            case(0): temp.addAll(bpa);break;
            case(1): temp.addAll(gla);break;
            case(2): temp.addAll(chl);break;
        }
        if(item.getItemId() == R.id.add_vital){
            AlertDialog show = new AlertDialog.Builder(VitalTabsActivity.this)
                    .setTitle("Enter value")
                    .setView(input)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i ) {
                                    String url = input.getText().toString();
                                    String use = "com.example."+names[position];
                                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(use, Context.MODE_PRIVATE);
                                    HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet(names[position], null);
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                    Date date = new Date();
                                    String msg = formatter.format(date);
                                    for (int j = 0; j < 10; j++){ msg = msg + " ";}
                                    msg = msg + url;
                                    msg = msg + units[position];
                                    switch(position){
                                        case(0): if(set!=null){bpa = new ArrayList<>(set);}bpa.add(msg); set = new HashSet<>(bpa);break;
                                        case(1): if(set!=null){gla = new ArrayList<>(set);}gla.add(msg); set = new HashSet<>(gla);break;
                                        case(2): if(set!=null){chl = new ArrayList<>(set);}chl.add(msg); set = new HashSet<>(chl);break;
                                    }
                                    temp.add(msg);
                                    switch(position){
                                        case(0): bpaArrayAdapter.clear();bpaArrayAdapter.addAll(temp);bpaArrayAdapter.notifyDataSetChanged();break;
                                        case(1): glaArrayAdapter.clear();glaArrayAdapter.addAll(temp);glaArrayAdapter.notifyDataSetChanged();break;
                                        case(2): chlArrayAdapter.clear();chlArrayAdapter.addAll(temp);chlArrayAdapter.notifyDataSetChanged();break;
                                    }
                                    sharedPreferences.edit().putStringSet(names[position], set).apply();
                                }
                            }
                    )
                    .setMessage("("+units[position]+")")
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_tabs);

        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);

        position = 0;

        tabLayout = findViewById(R.id.tab_layout);

        SharedPreferences glaSharedPreferences = getApplicationContext().getSharedPreferences("com.example.GLA", Context.MODE_PRIVATE);
        HashSet<String> glaSet = (HashSet<String>) glaSharedPreferences.getStringSet("GLA", null);
        if ( glaSet == null) {}
        else { gla = new ArrayList<>(glaSet); }
        glaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gla);

        SharedPreferences bpaSharedPreferences = getApplicationContext().getSharedPreferences("com.example.BPA", Context.MODE_PRIVATE);
        HashSet<String> bpaSet = (HashSet<String>) bpaSharedPreferences.getStringSet("BPA", null);
        if ( bpaSet == null) {}
        else { bpa = new ArrayList<>(bpaSet); }
        bpaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bpa);

        SharedPreferences chlSharedPreferences = getApplicationContext().getSharedPreferences("com.example.CHL", Context.MODE_PRIVATE);
        HashSet<String> chlSet = (HashSet<String>) chlSharedPreferences.getStringSet("CHL", null);
        if ( chlSet == null) {}
        else { chl = new ArrayList<>(chlSet); }
        chlArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chl);

        tabLayout.addTab(tabLayout.newTab().setText("BPA"));
        tabLayout.addTab(tabLayout.newTab().setText("GLA"));
        tabLayout.addTab(tabLayout.newTab().setText("CHL"));

        ListView listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(bpaArrayAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                switch(position){
                    case(0): listView.setAdapter(bpaArrayAdapter);  break;
                    case(1): listView.setAdapter(glaArrayAdapter);  break;
                    case(2): listView.setAdapter(chlArrayAdapter);  break;
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) { }
            @Override public void onTabReselected(TabLayout.Tab tab) { }
        });

    }
}