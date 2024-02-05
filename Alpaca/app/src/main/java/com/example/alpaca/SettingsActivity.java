package com.example.alpaca;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private EditText purple500EditText;
    private EditText purple700EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        purple500EditText = findViewById(R.id.editTextPurple500);
        purple700EditText = findViewById(R.id.editTextPurple700);

        Button applyButton = findViewById(R.id.buttonApply);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateColors();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        String purple500Value = sharedPreferences.getString("Main Color", "");
        String purple700Value = sharedPreferences.getString("Secondary Color", "");

        int purple500;
        int purple700;

        if (purple500Value.isEmpty()) {
            // Set a default value for purple500 if it is empty
            purple500 = Color.parseColor("#673AB7");
        } else {
            purple500 = Color.parseColor(purple500Value);
        }

        if (purple700Value.isEmpty()) {
            // Set a default value for purple700 if it is empty
            purple700 = Color.parseColor("#8194FF");
        } else {
            purple700 = Color.parseColor(purple700Value);
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(purple500));
        getWindow().setStatusBarColor(purple700);
    }

    private void updateColors() {
        String purple500Value = purple500EditText.getText().toString();
        String purple700Value = purple700EditText.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Main Color", purple500Value);
        editor.putString("Secondary Color", purple700Value);
        editor.apply();

        int purple500 = Color.parseColor(purple500Value);
        int purple700 = Color.parseColor(purple700Value);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(purple500));
        getWindow().setStatusBarColor(purple700);

        Button applyButton = findViewById(R.id.buttonApply);
        applyButton.setBackgroundColor(purple500);
    }
}