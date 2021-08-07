package com.example.phms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;



public class NotesEditor extends AppCompatActivity {
    public static EditText titletext;
    private Spinner spinner;

    int noteId;
    int titleId;
    EditText edittext;
    String title;
    String shouldTitle;
    boolean shouldAllowBack = true;
    boolean saved;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //set up menu
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.save_note_menu,menu);
        titletext = (EditText) menu.findItem(R.id.titleText).getActionView();

        //if note is new and not in saved set give title and mark as unsaved
        //else give title from title set and mark as saved
        if(noteId>=NotesActivity.titles.size()){
            titletext.setText("");
            saved = false;
        }else{
            titletext.setText(NotesActivity.titles.get(noteId));
            saved = true;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        //if user presses back space without saving then ask the user if they are sure they want to
        //back out without saving first
        if (shouldAllowBack) {
            super.onBackPressed();
        } else {
            AlertDialog show = new AlertDialog.Builder(NotesEditor.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to exit without saving?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i ) {
                                    NotesEditor.super.onBackPressed();
                                }
                            }
                    )
                    .setNegativeButton("No", null)
                    .show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        //if check mark on menu is selected
        if(item.getItemId() == R.id.done_check){
            //if nothing is in the note content dont save
            if(String.valueOf(edittext.getText()).length() == 0){
                return true;
            }
            //if nothing is in title text then use first 15 character in the note content as title
            if(String.valueOf(titletext.getText()).length() == 0){
                if(String.valueOf(edittext.getText()).length() < 15){
                    shouldTitle = String.valueOf(edittext.getText());
                }
                else{
                    shouldTitle = String.valueOf(edittext.getText()).substring(0,15);
                }
                titletext.setText(shouldTitle);
            }
            //if saved modify entry else add entry
            if(saved){
                NotesActivity.notes.set(noteId, String.valueOf(edittext.getText()));
                NotesActivity.titles.set(noteId, String.valueOf(titletext.getText()));
            }
            else{
                NotesActivity.titles.add(String.valueOf(titletext.getText()));
                NotesActivity.notes.add(String.valueOf(edittext.getText()));
            }
            saved = true;

            //save whats in edit text into note set
            NotesActivity.arrayAdapter.notifyDataSetChanged();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
            HashSet<String> set = new HashSet(NotesActivity.notes);
            sharedPreferences.edit().putStringSet("notes", set).apply();

            //save whats in title bar into title set
            NotesActivity.titlesArrayAdapter.notifyDataSetChanged();
            SharedPreferences titleSharedPreferences = getApplicationContext().getSharedPreferences("com.example.titles", Context.MODE_PRIVATE);
            HashSet<String> set2 = new HashSet(NotesActivity.titles);
            titleSharedPreferences.edit().putStringSet("titles", set2).apply();

            //show "saved" message when note is saved
            shouldAllowBack = true;
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myConstraint), "Saved",
                    Snackbar.LENGTH_SHORT);
            View view = mySnackbar.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.CENTER;
            view.setLayoutParams(params);
            mySnackbar.show();
            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);

        edittext = findViewById(R.id.editText);

        //get note id sent by NotesActivity
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId",-1);

        //fill the note content of prev save notes
        if (noteId != -1) {
            edittext.setText(NotesActivity.notes.get(noteId));
        } else {
            noteId = NotesActivity.notes.size();
        }

        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                shouldAllowBack = false;
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


    }
}