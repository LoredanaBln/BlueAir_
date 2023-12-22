package com.project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class fragment_cart extends Fragment implements RecyclerViewInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName +"?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;


    RecyclerView recyclerView;
    List<Ticket> listOfTickets;
    Adapter adapter;
    public fragment_cart() {
        listOfTickets = new ArrayList<>();
    }

    public static fragment_cart newInstance(String param1, String param2) {
        fragment_cart fragment = new fragment_cart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_cart);
        fragment_cart.ConnectMySql connectMySql = new fragment_cart.ConnectMySql();
        connectMySql.execute("");

    }
    public void displayTickets(){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        AdapterShop adapterShop = new AdapterShop(getActivity(), listOfTickets, this);
        recyclerView.setAdapter(adapterShop);

    }
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), TicketView.class);
        //TLDR ADD ITEMS NEEDED FOR DISPLAY
        intent.putExtra("DEPART", listOfTickets.get(position).getTicket_depart());
        intent.putExtra("ARRIVAL", listOfTickets.get(position).getTicket_arrival());
        intent.putExtra("DATE_DEPART", listOfTickets.get(position).getTicket_date_depart());
        intent.putExtra("DATE_ARRIVAL", listOfTickets.get(position).getTicket_date_arrival());
        intent.putExtra("CLASS", listOfTickets.get(position).getClass());
        intent.putExtra("COMPANY", listOfTickets.get(position).getTicket_company());
        intent.putExtra("PRICE", listOfTickets.get(position).getTicket_price());
        intent.putExtra("FLIGHT_ID", Integer.toString(listOfTickets.get(position).getFlightID()));
        intent.putExtra("USAGE", false);
        startActivity(intent);
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
                System.out.println("Databaseection success");

                String result = "";
                Statement st = con.createStatement();
                // SELECTS ALL THE ITEMS IN THE USERS CART
                ResultSet rs = st.executeQuery("SELECT flights.*, airlines.AirlineName, cart.price FROM flights INNER JOIN cart ON flights.flightID = cart.idFlight JOIN airlines ON flights.AirlineID = airlines.AirlineID WHERE cart.iduser = " + Activity_login.userID);
                ResultSetMetaData rsmd = rs.getMetaData();

                // FOR EACH ITEM SEARCH FOR THE FLIGHT AND ADD IT IN THE LIST
                while (rs.next()) {
                    String depart = rs.getString(3);
                    String arrive = rs.getString(5);

                    String dateDepart = rs.getString(7);
                    String dateArrive = rs.getString(8);
                    String company = rs.getString(20);
                    String price = rs.getString(21);
                    String flyingClass;
                    if(price.equals(rs.getString(18)))
                        flyingClass = "Economy";
                    else if(price.equals(rs.getString(17)))
                        flyingClass = "Business";
                    else flyingClass = "First";
                    int id = rs.getInt(1);
                    listOfTickets.add(new Ticket(depart,arrive, dateDepart, dateArrive, flyingClass, company, price, id, Activity_login.accountName));
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
            displayTickets();

        }
    }

}