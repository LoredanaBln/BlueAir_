package com.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Login extends AppCompatActivity {
    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName +"?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;

    TextView btn;
    private TextView inputEmail2, inputPassword2;
    Button loginButton;
    Button test;
    private static String password;
    public static  String email, accountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn = findViewById(R.id.signUp);
        inputEmail2 = findViewById(R.id.inputEmail2);
        inputPassword2 = findViewById(R.id.inputPassword2);
        loginButton = findViewById(R.id.buttonLogin);
        test = findViewById(R.id.test);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));

            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Ticket_List.class));
            }
        });

    }
    private void checkCredentials(){
        String email = inputEmail2.getText().toString();
        String password = inputPassword2.getText().toString();

        if(email.isEmpty() || !email.contains("@")){
            showError((TextView)inputEmail2, "Invalid email!");
        }
        if(password.isEmpty())
            showError((TextView)inputPassword2, "This field cannot be empty!");
        else{
            Toast.makeText(this, "All good!", Toast.LENGTH_SHORT).show();
            this.email = inputEmail2.getText().toString();
            this.password = inputPassword2.getText().toString();
            Login.ConnectMySql connectMySql = new Login.ConnectMySql();
            connectMySql.execute("");
        }
    }

    private void showError(TextView input, String s){
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
                System.out.println(url);
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Databaseection success");

                String result = "";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * from users where Email = \"" + email + "\" and Password = \"" + password + "\"");
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    result = rs.getString(5).toString() + "\n";
                }
                res = result;
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result != ""){
                accountName = result;
                System.out.println("Logged in with accout" + accountName);
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
            }
            else{
                showError((TextView)inputPassword2, "Wrong password or email.");

            }
        }
    }

}