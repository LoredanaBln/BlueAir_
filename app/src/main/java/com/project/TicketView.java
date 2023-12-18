package com.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TicketView extends AppCompatActivity {
    Button close;
    Button addToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_view);
        close = findViewById(R.id.buttonClose);
        addToCart = findViewById(R.id.buttonAddToCart);

        //String name = getIntent().getStringExtra("NAME");
        String depart = getIntent().getStringExtra("DEPART");
        String arrival = getIntent().getStringExtra("ARRIVAL");
        String datDepart = getIntent().getStringExtra("DATE_DEPART");
        String dateArrival = getIntent().getStringExtra("DATE_ARRIVAL");
        String company = getIntent().getStringExtra("COMPANY");
        String price = getIntent().getStringExtra("PRICE");
        //String class_ = getIntent().getStringExtra("CLASS");

        TextView ticketDepart = findViewById(R.id.ticketDeparture);
        TextView ticketArrival = findViewById(R.id.ticketArrival);
        TextView ticketDepartDate = findViewById(R.id.ticketDepartDate);
        TextView ticketArrivalDate = findViewById(R.id.ticketArrivalDate);
        TextView ticketCompany = findViewById(R.id.ticketCompany);
        TextView ticketPrice = findViewById(R.id.ticketPrice);
        //TextView ticketClass = findViewById(R.id.ticketClass);

        ticketDepart.setText(depart);
        ticketArrival.setText(arrival);
        ticketDepartDate.setText(datDepart);
        ticketArrivalDate.setText(dateArrival);
        ticketCompany.setText(company);
        ticketPrice.setText(price);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart.setVisibility(View.GONE);
                close.setVisibility(View.GONE);
                Fragment fragment = new fragment_cart();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).commit();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TicketView.this, Shop.class));
            }
        });


    }


}