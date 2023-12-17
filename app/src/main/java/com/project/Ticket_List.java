package com.project;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Spinner;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Ticket_List extends AppCompatActivity {

    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName + "?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;

    RecyclerView recyclerView;
    List<Ticket> listOfTickets;
    Adapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tickets_list);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        recyclerView = findViewById(R.id.recycler_main);

        displayTickets();
    }

    public void displayTickets() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        listOfTickets = new ArrayList<>();

        listOfTickets.add(new Ticket("Romania", "Spain", "10.02.2024", "11.02.2024", "10h", "Wizz"));
        listOfTickets.add(new Ticket("France", "Germany", "11.02.2024", "11.02.2024", "2h", "Wizz"));

        adapter = new Adapter(this, listOfTickets);
        recyclerView.setAdapter(adapter);

    }

    }
