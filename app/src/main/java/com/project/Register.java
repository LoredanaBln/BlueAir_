package com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Register extends AppCompatActivity {
    private static final String url = "jdbc:mysql://192.168.201.48:3306/project?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = "admin";
    private static final String pass = "octombrie14";

    TextView btn;
    private TextView inputName, inputEmail, inputPassword, inputConfirmPassword;
    Button registerButton;

    private String accUser, accPass, accEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        btn = findViewById(R.id.alreadyHave);
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        registerButton = findViewById(R.id.buttonReg);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    private void checkCredentials() {
        String username = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        if (username.isEmpty() || username.length() < 7) {
            showError((EditText) inputName, "Invalid username");
        }
        else
        if (email.isEmpty() || !email.contains("@")) {
            showError((EditText) inputEmail, "Invalid email");
        }
        else
        if (password.isEmpty()) {
            showError((EditText) inputPassword, "This field cannot be empty");
        }
        else
        if (password.length() < 7 && !password.isEmpty()) {
            showError((EditText) inputPassword, "Password must have at least 7 characters");
        }
        else
        if (!password.contains("@") && !password.contains("#") && !password.contains("%") && !password.contains("_")) {
            showError((EditText) inputPassword, "Password must contain at least one special character: @#%_");
        }
        else
        if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            showError((EditText) inputConfirmPassword, "Password doesn't match");
        }
        else {
            Toast.makeText(this, "Creating Account!", Toast.LENGTH_SHORT).show();
            accEmail = email;
            accPass = password;
            accUser = username;
            Register.ConnectMySql connectMySql = new Register.ConnectMySql();
            connectMySql.execute("");

        }

    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();

    }

    private class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Database connection success");

                String result = "";
                Statement st = con.createStatement();
                CallableStatement execProc = con.prepareCall("CALL AddUser(?,?,?,?,?,?)");
                execProc.setString(1, accUser);
                execProc.setString(2, accEmail);
                execProc.setString(3, accPass);
                execProc.setString(4, accUser);
                execProc.setString(5, "");
                execProc.setString(6, "");
                execProc.execute();

                res = result;
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            startActivity(new Intent(Register.this, Login.class));
        }
    }

}