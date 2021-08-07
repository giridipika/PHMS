package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

   public class NotesActivity extends AppCompatActivity {
   public static ArrayList<String> notes = new ArrayList<>();
   public static ArrayList<String> titles = new ArrayList<>();
   public static  ArrayAdapter arrayAdapter;
   public static  ArrayAdapter titlesArrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //set up menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        //if add note icon is selected open the Notes editor
        if(item.getItemId() == R.id.add_note){
            Intent intent = new Intent(getApplicationContext(), com.example.phms.NotesEditor.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        ListView listView = (ListView) findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        SharedPreferences titleSharedPreferences = getApplicationContext().getSharedPreferences("com.example.titles", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);
        HashSet<String> set2 = (HashSet<String>) titleSharedPreferences.getStringSet("titles", null);

        if ( set == null){
            notes.add("Example notes");
        } else {
            notes = new ArrayList<>(set);
        }
        if ( set2 == null){
            titles.add("Example title");
        } else {
            titles = new ArrayList<>(set2);
        }

        //prev versions of app didnt have title set this is meant to add titles to notes that dont have one
        int notesSize = notes.size();
        int titlesSize = titles.size();
        for(int i = 0; i < notesSize - titlesSize; i++) {
            titles.add("untitled");
            titlesArrayAdapter.notifyDataSetChanged();
            titleSharedPreferences.edit().putStringSet("titles", set2).apply();
        }
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        titlesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);

        //list note titles on notes activity page
        listView.setAdapter(titlesArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //if a title is selected then open the notes content in note editor
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(), com.example.phms.NotesEditor.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
                //if title is long clicked then prompt user if they would like to delete a note
                //if they do then delete entry from note and title set
                final int todelete;
                AlertDialog show = new AlertDialog.Builder(NotesActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int todelete ) {
                                        notes.remove(i);
                                        titles.remove(i);
                                        arrayAdapter.notifyDataSetChanged();
                                        titlesArrayAdapter.notifyDataSetChanged();
                                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                                        SharedPreferences titleSharedPreferences = getApplicationContext().getSharedPreferences("com.example.titles", Context.MODE_PRIVATE);
                                        HashSet<String> set = new HashSet(NotesActivity.notes);
                                        HashSet<String> set2 = new HashSet(NotesActivity.titles);
                                        sharedPreferences.edit().putStringSet("notes", set).apply();
                                        titleSharedPreferences.edit().putStringSet("titles", set2).apply();
                                    }
                                }
                        )
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

    }
}