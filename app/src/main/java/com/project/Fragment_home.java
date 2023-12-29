package com.project;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_home extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";
    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName +"?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;

    private Spinner spinnerFrom, spinnerTo, spinnerClass;
    private TextView titleReturn;
    private EditText textNumberPassengers;
    private Button buttonRoundTrip, buttonOneway, textDepart, textReturn;
    private Button buttonSearchFlights;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Map<String, List<String>> countryConnections;

    public Fragment_home() {
        countryConnections = new HashMap<>();
    }

    public static Fragment_home newInstance(String param1, String param2) {
        Fragment_home fragment = new Fragment_home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        Fragment_home.ConnectMySql connectMySql = new Fragment_home.ConnectMySql();
        connectMySql.execute("CREATE_SPINNER");
        createButtons(view);
    }
    public void createSpinners(){
        View view = getView();
        // INITIALISE SPINNERS
        spinnerFrom = (Spinner) view.findViewById(R.id.spinnerFrom);
        spinnerTo = (Spinner) view.findViewById(R.id.spinnerTo);
        spinnerClass = (Spinner) view.findViewById(R.id.spinnerClass);
        // DEFINE ADAPTERS
        ArrayAdapter countryAdapter;
        List<String> countries = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : countryConnections.entrySet()){
            countries.add(entry.getKey());
        }
        Collections.sort(countries);
        countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String country = spinnerFrom.getSelectedItem().toString();
                if(countryConnections.get(country) != null && countryConnections.get(country).size() != 0) {
                    ArrayAdapter cityAdapter;
                    cityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, countryConnections.get(country));
                    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTo.setAdapter(cityAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });


        ArrayAdapter classAdapter;
        classAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.flightClasses, android.R.layout.simple_spinner_item);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //CONNECT ADAPTERS
        spinnerFrom.setAdapter(countryAdapter);
        spinnerTo.setAdapter(countryAdapter);
        spinnerClass.setAdapter(classAdapter);
    }

    public void createButtons(View view){
        textNumberPassengers = (EditText) view.findViewById(R.id.ticketNumber);
        textDepart = (Button) view.findViewById(R.id.textDepart);
        buttonSearchFlights = (Button)view.findViewById(R.id.buttonSearchFlight);
        textDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                String res = Integer.toString(year);
                                if(monthOfYear < 10)
                                    res = res + "-0" + (monthOfYear + 1);
                                else
                                    res = res + "-" + (monthOfYear + 1);

                                if(dayOfMonth < 10)
                                    res = res + "-0" + (dayOfMonth);
                                else
                                    res = res + "-" + (dayOfMonth);
                                textDepart.setText(res);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        buttonSearchFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String depart = spinnerFrom.getSelectedItem().toString();
                // ORAS ARRIVE
                String arrive = spinnerTo.getSelectedItem().toString();
                // obvious
                String dateDepart = textDepart.getText().toString();
                if(textDepart.getText().toString().equals("")){
                    showError((Button) textDepart, "Please select a valid date.");
                    return;
                }

                String flyclass = spinnerClass.getSelectedItem().toString();
                if(textNumberPassengers.getText().toString().equals("")){
                    showError((TextView) textNumberPassengers, "Please select number of passengers.");
                    return;
                }
                int noTickets = Integer.parseInt(textNumberPassengers.getText().toString());
                System.out.println(depart + arrive + dateDepart + flyclass);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_shop(depart, arrive, dateDepart, flyclass, noTickets)).commit();

            }
        });
    }
    private void showError(TextView input, String s){
        input.setError(s);
        input.requestFocus();
    }
    private void showError(Button input, String s){
        input.setError(s);
        input.requestFocus();
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
                if(params[0] == "CREATE_SPINNER") {
                    Statement st = con.createStatement();
                    // SELECTS ALL THE COUNTRY AND CITY PAIR
                    ResultSet rs = st.executeQuery("select distinct flights.DepartureLocationCountry, flights.ArrivalLocationCountry from flights order by flights.DepartureLocationCountry");
                    ResultSetMetaData rsmd = rs.getMetaData();

                    // FOR EACH COUNTRY ADD CITY TO LIST
                    while (rs.next()) {
                        String country = rs.getString(1);
                        String countryArrive = rs.getString(2);

                        if (!countryConnections.containsKey(country)) {
                            countryConnections.put(country, new ArrayList<String>());
                        }
                        countryConnections.get(country).add(countryArrive);
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
            createSpinners();
        }
    }
}