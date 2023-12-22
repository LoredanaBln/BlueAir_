package com.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Main_screen_Activity extends AppCompatActivity {

    Button buttonUser, buttonAdmin;
    TextView signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        buttonUser = findViewById(R.id.buttonUser);
        buttonAdmin =findViewById(R.id.buttonAdmin);

        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_screen_Activity.this, MainActivity.class));
            }
        });

        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Main_screen_Activity.this, Activity_Admin.class));
            }
        });
    }
}