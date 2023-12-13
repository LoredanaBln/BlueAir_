package com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Forgot_Password extends AppCompatActivity {

    TextView emailText;
    Button resetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailText = findViewById(R.id.emailForgotPassword);
        resetPassword = findViewById(R.id.buttonForgotPassword);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();
                new ForgotPasswordTask().execute(email);
            }
        });
    }

}