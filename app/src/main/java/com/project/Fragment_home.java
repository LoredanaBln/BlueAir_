package com.project;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Calendar;

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
    public Fragment_home() {
        // Required empty public constructor
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
        createSpinners(view);
        createButtons(view);
    }
    public void createSpinners(View view){
        // INITIALISE SPINNERS
        spinnerFrom = (Spinner) view.findViewById(R.id.spinnerFrom);
        spinnerTo = (Spinner) view.findViewById(R.id.spinnerTo);
        spinnerClass = (Spinner) view.findViewById(R.id.spinnerClass);
        // DEFINE ADAPTERS
        ArrayAdapter countryAdapter, classAdapter;
        countryAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.coutries, android.R.layout.simple_spinner_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
                String flyclass = spinnerClass.getSelectedItem().toString();
                int noTickets = Integer.parseInt(textNumberPassengers.getText().toString());
                System.out.println(depart + arrive + dateDepart + flyclass);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_shop(depart, arrive, dateDepart, flyclass, noTickets)).commit();

            }
        });
    }
}