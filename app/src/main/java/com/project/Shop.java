package com.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Shop extends AppCompatActivity implements RecyclerViewInterface {
    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName + "?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;

    RecyclerView recyclerView;
    List<Ticket> listOfTickets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        recyclerView = findViewById(R.id.recycler_main);
        displayTickets();

        AdapterShop adapterShop = new AdapterShop(this, listOfTickets, this);
        recyclerView.setAdapter(adapterShop);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }

    public void displayTickets() {

        recyclerView.setHasFixedSize(true);
        listOfTickets = new ArrayList<>();
        listOfTickets.add(new Ticket("Romania", "Spain", "10.02.2024", "11.02.2024", "10h", "WIZZ"));
        listOfTickets.add(new Ticket("France", "Germany", "11.02.2024", "11.02.2024", "2h", "WIZZ"));
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Shop.this, TicketView.class);
        intent.putExtra("DEPART", listOfTickets.get(position).getTicket_depart());
        intent.putExtra("ARRIVAL", listOfTickets.get(position).getTicket_arrival());
        //intent.putExtra("CLASS", listOfTickets.get(position).getClass());
        intent.putExtra("DATE_DEPART", listOfTickets.get(position).getTicket_date_depart());
        intent.putExtra("DATE_ARRIVAL", listOfTickets.get(position).getTicket_date_arrival());
        intent.putExtra("COMPANY", listOfTickets.get(position).getTicket_company());
        //intent.putExtra("PRICE", listOfTickets.get(position).getTicket_price());


        startActivity(intent);
    }
}