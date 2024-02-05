package com.example.alpaca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {
    private int noteId;

    @Override
    protected void onResume() {
        super.onResume();


        // Retrieve the color values from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        String purple500Value = sharedPreferences.getString("Main Color", "");
        String purple700Value = sharedPreferences.getString("Secondary Color", "");
        int purple500 = Color.parseColor(purple500Value);
        int purple700 = Color.parseColor(purple700Value);

        // Change the color of the ActionBar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(purple500));

        // Change the color of the StatusBar
        getWindow().setStatusBarColor(purple700);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = findViewById(R.id.editText);

        // Fetch the data passed from MainActivity
        Intent intent = getIntent();

        // Access the data using the key "noteId" and default value -1
        noteId = intent.getIntExtra("noteId", -1);
        if (noteId != -1) {
            // If noteId is valid, set the text in the EditText to the corresponding note
            editText.setText(MainActivity.notes.get(noteId));
        } else {
            // If noteId is -1 (indicating a new note), add an empty note to the list and set noteId accordingly
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called before the text is changed
                // Add your code here if needed
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called when the text is being changed
                // Update the corresponding note with the new text
                MainActivity.notes.set(noteId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                // Store the updated notes list in SharedPreferences
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This method is called after the text has been changed
                // Add your code here if needed
            }
        });
    }
}