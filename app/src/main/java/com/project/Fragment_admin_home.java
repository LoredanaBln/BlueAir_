package com.project;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Fragment_admin_home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";
    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName +"?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;

    private TextView adminUsername, adminEmail, adminName, adminPhone, adminAddress;
    public String accEmail, accName, accUsername, accPhone, accAddress;

    // TODO: Rename and change types of parameters

    public Fragment_admin_home() {
        // Required empty public constructor
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        adminUsername = view.findViewById(R.id.adminUsername);
        adminEmail = view.findViewById(R.id.adminEmail);
        adminName = view.findViewById(R.id.adminFullName);
        adminPhone = view.findViewById(R.id.adminPhoneNumber);
        adminAddress = view.findViewById(R.id.adminAddress);
        Fragment_admin_home.ConnectMySql connectMySql = new Fragment_admin_home.ConnectMySql();
        connectMySql.execute("");

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_home, container, false);
    }

    public void displayInfo(){
        if(accName == null) adminName.setText("Full Name");
        else adminName.setText(accName);

        if(accEmail == null) adminEmail.setText("Email");
        else adminEmail.setText(accEmail);

        if(accUsername == null) adminUsername.setText("Username");
        else adminUsername.setText(accUsername);

        if(accAddress == null) adminAddress.setText("Address");
        else adminAddress.setText(accAddress);

        if(accPhone == null) adminPhone.setText("Phone Number");
        else adminPhone.setText(accPhone);
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
                ResultSet rs = st.executeQuery("SELECT * from users where UserId =" + Activity_login.userID);
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    accName =  rs.getString(2).toString();
                    accUsername = rs.getString(5).toString();
                    accEmail  = rs.getString(3).toString();
                    accPhone = rs.getString(6).toString();
                    accAddress = rs.getString(7).toString();

                    result = rs.getString(1);
                }
                rs.close();
                res = result;
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            displayInfo();
        }
    }
}