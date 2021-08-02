package com.example.phms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NotesEditor extends AppCompatActivity {
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);
        EditText edittext = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId",-1);

        if (noteId != -1) {
            edittext.setText(NotesActivity.notes.get(noteId));
        } else {
            NotesActivity.notes.add("");
            noteId = NotesActivity.notes.size() -1;
            NotesActivity.arrayAdapter.notifyDataSetChanged();
        }
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                NotesActivity.notes.set(noteId,String.valueOf(charSequence));
                NotesActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(NotesActivity.notes);

                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}