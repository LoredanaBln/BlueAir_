package com.project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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

/**

 */
public class Fragment_Modify_database extends Fragment {

    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName + "?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;

    RecyclerView recyclerView;
    //COMMENT====== INITIALIZE VARIABLES FOR QUERY =====//
    private String stringDepartLocation, stringArriveLocation, stringDateDepart, stringFlyingClass, stringFlyingClassTicket;
    List<Ticket> listOfTickets;
    public Fragment_Modify_database() {
        listOfTickets = new ArrayList<>();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_main);
        Fragment_Modify_database.ConnectMySql connectMySql = new Fragment_Modify_database.ConnectMySql();
        connectMySql.execute("");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment__modify_database, container, false);
    }

    public void displayTickets() {

        recyclerView.setHasFixedSize(true);
        Adapter adapter = new Adapter(getActivity(), listOfTickets);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));


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
                Statement st = con.createStatement();
                //COMMENT SELECTS ALL THE FLIGHTS WITH PARAMETERS FROM CONSTRUCTOR
                String query = String.format("SELECT flights.*, airlines.AirlineName FROM flights JOIN airlines ON flights.AirlineID = airlines.AirlineID WHERE flights.DepartureLocationCountry = \"%s\" and flights.ArrivalLocationCountry = \"%s\" and flights.DepartureTime like \"%%%s%%\" and %s >0", stringDepartLocation, stringArriveLocation, stringDateDepart, stringFlyingClassTicket);
                Log.i("QUERY", query);
                ResultSet rs = st.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();

                //COMMENT FOR EACH ITEM SEARCH FOR THE FLIGHT AND ADD IT IN THE LIST
                while (rs.next()) {
                    int flightID = rs.getInt(1);
                    Log.i("query", Integer.toString(flightID));
                    // ORAS DEPART
                    String depart = rs.getString(3);
                    // ORAS ARRIVE
                    String arrive = rs.getString(5);
                    // obvious
                    String dateDepart = rs.getString(7);
                    String dateArrive = rs.getString(8);
                    // TICKET PRICES
                    String ticketPrice;
                    if(stringFlyingClass.equals("Economy"))
                        ticketPrice = rs.getString(18);
                    else if(stringFlyingClass.equals("Business"))
                        ticketPrice = rs.getString(17);
                    else
                        ticketPrice = rs.getString(16);
                    String company = rs.getString(20);

                    listOfTickets.add(new Ticket(depart,arrive, dateDepart, dateArrive, stringFlyingClass, company, ticketPrice, flightID, Activity_login.accountName));
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