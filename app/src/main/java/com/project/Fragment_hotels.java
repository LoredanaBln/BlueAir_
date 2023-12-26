package com.project;

import android.health.connect.datatypes.SexualActivityRecord;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_hotels#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_hotels extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName +"?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;

    List<Ticket> listOfTickets;
    Map<String, List<String>> citiesOfCountry;
    Spinner spinnerCountry, spinnerCity;
    Button searchHotels;
    Adapter adapter;
    public Fragment_hotels() {

        listOfTickets = new ArrayList<>();
        citiesOfCountry = new HashMap<String, List<String>>();

    }


    public static Fragment_hotels newInstance(String param1, String param2) {
        Fragment_hotels fragment = new Fragment_hotels();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotels, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        createSpinners(view);
        createButtons(view);
        Fragment_hotels.ConnectMySql connectMySql = new Fragment_hotels.ConnectMySql();
        connectMySql.execute("CREATE_SPINNER");

    }


    public void createButtons(View view){
        searchHotels = view.findViewById(R.id.buttonSearchHotels);
        searchHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = spinnerCity.getSelectedItem().toString();
                String country = spinnerCountry.getSelectedItem().toString();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_hotels_results(country, city)).commit();

            }
        });
    }
    public void createSpinners(View view){
        // INITIALISE SPINNERS
        spinnerCountry = (Spinner) view.findViewById(R.id.spinnerHotelCountry);
        spinnerCity = (Spinner) view.findViewById(R.id.spinnerHotelCity);
        // DEFINE ADAPTERS
        ArrayAdapter countryAdapter;
        countryAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.coutries, android.R.layout.simple_spinner_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String country = spinnerCountry.getSelectedItem().toString();
                if(citiesOfCountry.size() != 0) {
                    ArrayAdapter cityAdapter;
                    cityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, citiesOfCountry.get(country));
                    countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCity.setAdapter(cityAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        //CONNECT ADAPTERS
        spinnerCountry.setAdapter(countryAdapter);
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
                if(params[0] == "CREATE_SPINNER") {
                    Statement st = con.createStatement();
                    // SELECTS ALL THE COUNTRY AND CITY PAIR
                    ResultSet rs = st.executeQuery("SELECT distinct locations.Country, locations.city FROM project.locations order by locations.city");
                    ResultSetMetaData rsmd = rs.getMetaData();

                    // FOR EACH COUNTRY ADD CITY TO LIST
                    while (rs.next()) {
                        String country = rs.getString(1);
                        String city = rs.getString(2);

                        if (!citiesOfCountry.containsKey(country)) {
                            citiesOfCountry.put(country, new ArrayList<String>());
                        }
                        citiesOfCountry.get(country).add(city);
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

        }
    }
}