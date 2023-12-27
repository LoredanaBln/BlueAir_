package com.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

//CLASS FOR VIEWING TICKET AFTER CLICKING ON ONE!
public class TicketView extends AppCompatActivity {
    Button addToCart;
    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName + "?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;
    private String flightID;
    //TLDR SAME USAGE FOR SHOP AND CART
    //TLDR 1 = FROM SHOP -> BUTTON ADD TO CART
    //TLDR 0 = FROM CART -> BUTTON REMOVE FROM CART
    private boolean _USAGE_TYPE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_expanded_ticket);
        addToCart = findViewById(R.id.buttonAddToCart);

        //TLDR GET DATA FOR TICKET
        String depart = getIntent().getStringExtra("DEPART");
        String arrival = getIntent().getStringExtra("ARRIVAL");
        String datDepart = getIntent().getStringExtra("DATE_DEPART");
        String dateArrival = getIntent().getStringExtra("DATE_ARRIVAL");
        String company = getIntent().getStringExtra("COMPANY");
        String flyCLass = getIntent().getStringExtra("CLASS");
        String person = getIntent().getStringExtra("PERSON");
        String price = getIntent().getStringExtra("PRICE");
        int numberOfTickets = getIntent().getIntExtra("NUMBER_OF_TICKETS", 1);
        flightID = getIntent().getStringExtra("FLIGHT_ID");
        _USAGE_TYPE = getIntent().getBooleanExtra("USAGE", true);

        TextView ticketDepart = findViewById(R.id.ticketDeparture);
        TextView ticketArrival = findViewById(R.id.ticketArrival);
        TextView ticketDepartDate = findViewById(R.id.ticketDepartDate);
        TextView ticketArrivalDate = findViewById(R.id.ticketArrivalDate);
        TextView ticketCompany = findViewById(R.id.ticketCompany);
        TextView ticketPrice = findViewById(R.id.ticketPrice);
        TextView ticketClass = findViewById(R.id.ticketClass);
        TextView ticketPassenger = findViewById(R.id.ticketPerson);

        ticketDepart.setText(depart);
        ticketArrival.setText(arrival);
        ticketDepartDate.setText(datDepart);
        ticketArrivalDate.setText(dateArrival);
        ticketCompany.setText(company);
        ticketPrice.setText("" + (Double.parseDouble(price) * numberOfTickets));
        ticketClass.setText(flyCLass);
        ticketPassenger.setText(person);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart.setVisibility(View.GONE);
                TicketView.ConnectMySql connectMySql = new TicketView.ConnectMySql();
                connectMySql.execute("");
                Toast.makeText(v.getContext(), "Added to cart!", Toast.LENGTH_LONG);

            }
        });

        if(_USAGE_TYPE == true){
            if(numberOfTickets == 1)
                addToCart.setText("ADD TO CART");
            else
                addToCart.setText("ADD TO CART x" + numberOfTickets);
        }
        else {
            if(numberOfTickets == 1)
                addToCart.setText("REMOVE FROM CART");
            else
                addToCart.setText("REMOVE FROM CART x" + numberOfTickets);

        }

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
                System.out.println("Connection success");

                String result = "";
                CallableStatement execProc;

                if(_USAGE_TYPE == true){
                    execProc = con.prepareCall("CALL InsertIntoCart(?,?,?,?)");
                    execProc.setString(1, Integer.toString(Activity_login.userID));
                    execProc.setString(2, flightID);
                    execProc.setString(3, getIntent().getStringExtra("PRICE"));
                    execProc.setInt(4, getIntent().getIntExtra("NUMBER_OF_TICKETS", 1));
                }
                else{
                    execProc = con.prepareCall("CALL RemoveFromCart(?,?)");
                    execProc.setString(1, Integer.toString(Activity_login.userID));
                    execProc.setString(2, flightID);
                }
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
            finish();
        }
    }
}