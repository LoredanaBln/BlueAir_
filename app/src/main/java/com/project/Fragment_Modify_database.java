package com.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Fragment_Modify_database extends Fragment implements RecyclerViewInterface{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName + "?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;
    private Button buttonDelete, buttonAdd;
    boolean isChecked;
    AdapterAdmin adapter;

    RecyclerView recyclerView;
    //COMMENT====== INITIALIZE VARIABLES FOR QUERY =====//
    //private String stringDepartLocation, stringArriveLocation, stringDateDepart, stringFlyingClass, stringFlyingClassTicket;
    List<Ticket> listOfTickets;
    public Fragment_Modify_database() {listOfTickets = new ArrayList<>();}

    public static Fragment_Modify_database newInstance(String param1, String param2) {
        Fragment_Modify_database fragment = new Fragment_Modify_database();
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

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_admin);
        buttonDelete = (Button) view.findViewById(R.id.buttonDelete);
        initButtons();
        Fragment_Modify_database.ConnectMySql connectMySql = new Fragment_Modify_database.ConnectMySql();
        connectMySql.execute("INIT_DB");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment__modify_database, container, false);
    }

    public void initButtons(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Delete");
                builder.setMessage("Confirm deletion of selected flights.");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Fragment_Modify_database.ConnectMySql connectMySql = new Fragment_Modify_database.ConnectMySql();
                                connectMySql.execute("DELETE_ENTRY");

                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    public void displayTickets() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter = new AdapterAdmin(getActivity(), listOfTickets, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {
        listOfTickets.get(position).setSelected(!listOfTickets.get(position).getSelected());
        Log.i("Select", "" + listOfTickets.get(position).getSelected());
        adapter.notifyItemChanged(position);

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
                if(params[0] == "INIT_DB") {
                    Statement st = con.createStatement();
                    // SELECTS ALL THE ITEMS IN THE USERS CART
                    ResultSet rs = st.executeQuery("SELECT flights.*, airlines.AirlineName FROM flights JOIN airlines ON flights.AirlineID = airlines.AirlineID order by flights.DepartureLocationCountry");
                    ResultSetMetaData rsmd = rs.getMetaData();

                    // FOR EACH ITEM SEARCH FOR THE FLIGHT AND ADD IT IN THE LIST
                    while (rs.next()) {
                        String depart = rs.getString(3);
                        String arrive = rs.getString(5);

                        String dateDepart = rs.getString(7);
                        String dateArrive = rs.getString(8);
                        String company = rs.getString(20);
                        String priceF = rs.getString(16);
                        String priceB = rs.getString(17);
                        String priceE = rs.getString(18);
                        int id = rs.getInt(1);
                        String flightNumber = rs.getString(2);
                        Ticket ticket = new Ticket(depart, arrive, dateDepart, dateArrive, "universal", company, priceE, id, Activity_login.accountName);
                        ticket.setFlightNumber(flightNumber);

                        listOfTickets.add(ticket);
                    }
                }
                else if(params[0] == "DELETE_ENTRY"){
                    for (Ticket t : listOfTickets){
                        if(t.getSelected() == true){
                            listOfTickets.remove(t);
                            CallableStatement execProc;
                            execProc = con.prepareCall("CALL DeleteFlight(?)");
                            execProc.setInt(1,t.getFlightID());
                            execProc.execute();
                        }
                    }

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