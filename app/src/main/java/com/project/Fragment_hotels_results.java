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
import android.widget.GridLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_hotels_results#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_hotels_results extends Fragment implements RecyclerViewInterface{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String country, city;
    private List<Hotel> listOfHotels;
    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName +"?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;
    RecyclerView recyclerView;


    public Fragment_hotels_results(){
        // empty constructor
    }
    public Fragment_hotels_results(String country, String city) {
        this.city = city;
        this.country = country;
        listOfHotels = new ArrayList<>();
    }

    public static Fragment_hotels_results newInstance(String param1, String param2) {
        Fragment_hotels_results fragment = new Fragment_hotels_results();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hotels_results, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_hotels);
        Fragment_hotels_results.ConnectMySql connectMySql = new Fragment_hotels_results.ConnectMySql();
        connectMySql.execute("");

    }

    //COMMENT NEEDED FOR RECYCLER VIEW!!
    void displayHotels(){
        recyclerView.setHasFixedSize(true);
        AdapterHotel adapterHotel = new AdapterHotel(getActivity(), listOfHotels, this);
        recyclerView.setAdapter(adapterHotel);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), HotelView.class);
        intent.putExtra("Hotel", listOfHotels.get(position));
        startActivity(intent);
    }
    //COMMENT NEEDED FOR RECYCLER VIEW!!

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
                // SELECTS ALL THE HOTELS FOR GIVEN COUNTRY AND CITY
                String query = String.format("SELECT * FROM hotels join locations on locations.LocationID = hotels.LocationID where locations.City = \"%s\" and locations.Country = \"%s\"", city, country);
                ResultSet rs = st.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();

                // FOR EACH HOTEL ADD IT TO THE LIST
                while (rs.next()) {
                    int hotelID = rs.getInt(1);
                    String hotelName = rs.getString(2);
                    String hotelDesc = rs.getString(4);
                    String hotelRating = rs.getString(5);
                    String hotelNumber = rs.getString(6);
                    String hotelEmail = rs.getString(7);
                    String hotelWeb = rs.getString(8);
                    String checkIn = rs.getString(9);
                    String checkOut = rs.getString(10);
                    String hotelAmenities = rs.getString(11);
                    String hotelPolicies = rs.getString(12);

                    listOfHotels.add(new Hotel(hotelID, hotelName, hotelDesc, hotelRating, hotelNumber, hotelEmail, hotelWeb, checkIn, checkOut, hotelAmenities, hotelPolicies, city, country));
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
            displayHotels();
        }
    }

}