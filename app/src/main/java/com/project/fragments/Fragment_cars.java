package com.project.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.DBConnectionCredentials;
import com.project.R;
import com.project.viewmodel.Ticket;
import com.project.activities.Activity_login;
import com.project.adapters.Adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_cars#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_cars extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName +"?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;

    RecyclerView recyclerView;
    List<Ticket> listOfTickets;
    Adapter adapter;
    public Fragment_cars() {
        listOfTickets = new ArrayList<>();
    }

    public static Fragment_cars newInstance(String param1, String param2) {
        Fragment_cars fragment = new Fragment_cars();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cars, container, false);
    }

    public void displayTickets(){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));

        adapter = new Adapter(getActivity(), listOfTickets);
        recyclerView.setAdapter(adapter);
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
                ResultSet rs = st.executeQuery("SELECT flights.* FROM flights INNER JOIN cart ON flights.flightID = cart.idFlight WHERE cart.iduser = " + Activity_login.userID);
                ResultSetMetaData rsmd = rs.getMetaData();

                // FOR EACH ITEM SEARCH FOR THE FLIGHT AND ADD IT IN THE LIST
                while (rs.next()) {
                    String depart = rs.getString(3);
                    String arrive = rs.getString(5);
                    String dateDepart = rs.getString(7);
                    String dateArrive = rs.getString(8);
                    listOfTickets.add(new Ticket(depart,arrive, dateDepart, dateDepart, "3h", "Wizz", "30"));

                    dateDepart = rs.getString(7).replaceAll("\\s.*", "");
                    dateArrive = rs.getString(8).replaceAll("\\s.*", "");
                    listOfTickets.add(new Ticket(depart,arrive, dateDepart, dateArrive, "3h", "Wizz", "22"));
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