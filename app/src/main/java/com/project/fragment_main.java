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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_main#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_main extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String url = "jdbc:mysql://192.168.183.48:3306/test?characterEncoding=latin1";
    private static final String user = "android";
    private static final String pass = "Octombrie-14";

    private Spinner spinnerFrom, spinnerTo, spinnerClass;
    private TextView titleReturn;
    private Button buttonRoundTrip, buttonOneway, textDepart, textReturn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public fragment_main() {
        // Required empty public constructor
    }

    public static fragment_main newInstance(String param1, String param2) {
        fragment_main fragment = new fragment_main();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmentmain, container, false);
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
        textDepart = (Button) view.findViewById(R.id.textDepart);
        textReturn = (Button) view.findViewById(R.id.textDepart2);
        titleReturn = (TextView) view.findViewById(R.id.textFrom4);
        buttonRoundTrip = (Button) view.findViewById(R.id.buttonRoundTrip);
        buttonOneway = (Button) view.findViewById(R.id.buttonRoundTrip2);

        buttonRoundTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonRoundTrip.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.rounded_corner_blue, null));
                buttonRoundTrip.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                buttonOneway.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_corner_white, null));
                buttonOneway.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
                textReturn.setEnabled(true);
                titleReturn.setEnabled(true);
                textReturn.setVisibility(View.VISIBLE);
                titleReturn.setVisibility(View.VISIBLE);

                System.out.println("Clicked");
                ConnectMySql connectMySql = new ConnectMySql();
                connectMySql.execute("");

            }
        });

        buttonOneway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOneway.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.rounded_corner_blue, null));
                buttonOneway.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                buttonRoundTrip.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_corner_white, null));
                buttonRoundTrip.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
                textReturn.setEnabled(false);
                titleReturn.setEnabled(false);
                textReturn.setVisibility(View.INVISIBLE);
                titleReturn.setVisibility(View.INVISIBLE);

            }
        });

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
                                textDepart.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
        textReturn.setOnClickListener(new View.OnClickListener() {
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
                                textReturn.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_SHORT)
                    .show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Databaseection success");

                String result = "Database Connection Successful\n";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM flight where flightcol1 = 123");
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    result += rs.getString(2).toString() + "\n";
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
            buttonRoundTrip.setText(result);
        }
    }
}