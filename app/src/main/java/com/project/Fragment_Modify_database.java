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
import android.widget.Button;

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
        buttonAdd = (Button) view.findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddWindowActivity.class) ;
                getActivity().startActivity(intent);
            }
        });

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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        AdapterAdmin adapter = new AdapterAdmin(getActivity(), listOfTickets, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), TicketView.class);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        intent.putExtra("DEPART", listOfTickets.get(position).getTicket_depart());
        intent.putExtra("ARRIVAL", listOfTickets.get(position).getTicket_arrival());
        intent.putExtra("DATE_DEPART", listOfTickets.get(position).getTicket_date_depart());
        intent.putExtra("DATE_ARRIVAL", listOfTickets.get(position).getTicket_date_arrival());
        intent.putExtra("CLASS", listOfTickets.get(position).getTicket_class());
        //intent.putExtra("COMPANY", listOfTickets.get(position).getTicket_company());
        //intent.putExtra("PRICE", listOfTickets.get(position).getTicket_price());
        intent.putExtra("FLIGHT_ID", Integer.toString(listOfTickets.get(position).getFlightID()));
        intent.putExtra("PERSON", listOfTickets.get(position).getTicket_person());
        intent.putExtra("USAGE", true);
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
                ResultSet rs = st.executeQuery("SELECT flights.*, airlines.AirlineName FROM flights JOIN airlines ON flights.AirlineID = airlines.AirlineID");
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

//
//                    String flyingClass;
//                    if(price.equals(rs.getInt(18)))
//                        flyingClass = "Economy";
//                    else if(price.equals(rs.getString(17)))
//                        flyingClass = "Business";
//                    else flyingClass = "First";
                    int id = rs.getInt(1);
                    listOfTickets.add(new Ticket(depart,arrive, dateDepart, dateArrive, "some", company, "randomNum", "", false, id));
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
    public void buttons(View view){

    }
}