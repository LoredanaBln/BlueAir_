package com.project;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Fragment_shop extends Fragment implements RecyclerViewInterface {
    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName + "?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;

    RecyclerView recyclerView;
    List<Ticket> listOfTickets;
    Adapter adapter;
    public Fragment_shop() {
        //whatever
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_main);
        displayTickets();

        AdapterShop adapterShop = new AdapterShop(getContext(), listOfTickets, this);
        recyclerView.setAdapter(adapterShop);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_shop, container, false);
    }

    public void displayTickets() {

        recyclerView.setHasFixedSize(true);
        listOfTickets = new ArrayList<>();

        listOfTickets.add(new Ticket("Romania", "Spain", "10.02.2024", "11.02.2024", "10h", "WIZZ", "32"));
        listOfTickets.add(new Ticket("France", "Germany", "11.02.2024", "11.02.2024", "2h", "WIZZ", "32"));
        adapter = new Adapter(getActivity(), listOfTickets);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), TicketView.class);
        intent.putExtra("DEPART", listOfTickets.get(position).getTicket_depart());
        intent.putExtra("ARRIVAL", listOfTickets.get(position).getTicket_arrival());
        //intent.putExtra("CLASS", listOfTickets.get(position).getClass());
        intent.putExtra("DATE_DEPART", listOfTickets.get(position).getTicket_date_depart());
        intent.putExtra("DATE_ARRIVAL", listOfTickets.get(position).getTicket_date_arrival());
        intent.putExtra("COMPANY", listOfTickets.get(position).getTicket_company());
        intent.putExtra("PRICE", listOfTickets.get(position).getTicket_price());

        intent.putExtra("TICKET_PRICE", "32");


        startActivity(intent);
    }
}