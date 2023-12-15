package com.project;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Ticket_List extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Ticket> listOfTickets;
    Adapter adapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tickets_list);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        recyclerView = findViewById(R.id.recycler_main);

        displayTickets();
    }

    public void displayTickets(){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        listOfTickets = new ArrayList<>();
        listOfTickets.add(new Ticket("Romania","Spain", "10.02.2024", "11.02.2024","10h"));
        listOfTickets.add(new Ticket("France","Germany", "11.02.2024", "11.02.2024","2h"));

        adapter = new Adapter(this, listOfTickets);
        recyclerView.setAdapter(adapter);
    }
}
