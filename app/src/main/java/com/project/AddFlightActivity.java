package com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class AddFlightActivity extends AppCompatActivity {

    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName + "?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;
    Button buttonAdd;
    private TextView inputFlightNumber, inputDepartureLocationCity, inputDepartureLocationCountry, inputArrivalLocationCity, inputArrivalLocationCountry, inputDepartureTime, inputArrivalTime, inputAvailableSeats, inputTotalSeatsFirstClass, inputTotalSeatsBusinessClass, inputTotalSeatsEconomyClass, inputAvailableSeatsFirstClass, inputAvailableSeatsBusinessClass, inputAvailableSeatsEconomyClass, inputTicketPriceFirst, inputTicketPriceBusiness, inputTicketPriceEconomy, inputAirlineID;
    private String FlightNumber, DepartureLocationCity, DepartureLocationCountry, ArrivalLocationCity, ArrivalLocationCountry, DepartureTime, ArrivalTime, AvailableSeats, TotalSeatsFirstClass, TotalSeatsBusinessClass, TotalSeatsEconomyClass, AvailableSeatsFirstClass, AvailableSeatsBusinessClass, AvailableSeatsEconomyClass, TicketPriceFirst, TicketPriceBusiness, TicketPriceEconomy, AirlineID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_window);
        buttonAdd = findViewById(R.id.buttonAddFlight);
        inputFlightNumber = findViewById(R.id.flightNumber);
        inputDepartureLocationCity = findViewById(R.id.flightDepartureLocationCity);
        inputDepartureLocationCountry = findViewById(R.id.flightDepartureLocationCountry);
        inputArrivalLocationCity = findViewById(R.id.flightArrivalLocationCity);
        inputArrivalLocationCountry = findViewById(R.id.flightArrivalLocationCountry);
        inputDepartureTime = findViewById(R.id.flightDepartureTime);
        inputArrivalTime = findViewById(R.id.flightArrivalTime);
        inputAvailableSeats = findViewById(R.id.flightAvailableSeats);
        inputTotalSeatsFirstClass = findViewById(R.id.flightTotalSeatsFirstClass);
        inputTotalSeatsBusinessClass = findViewById(R.id.flightTotalSeatsBusinessClass);
        inputTotalSeatsEconomyClass = findViewById(R.id.flightTotalSeatsEconomyClass);
        inputAvailableSeatsFirstClass = findViewById(R.id.flightAvailableSeatsFirstClass);
        inputAvailableSeatsBusinessClass = findViewById(R.id.flightAvailableSeatsBusinessClass);
        inputAvailableSeatsEconomyClass = findViewById(R.id.flightAvailableSeatsEconomyClass);
        inputTicketPriceFirst = findViewById(R.id.flightTicketPriceFirst);
        inputTicketPriceBusiness = findViewById(R.id.flightTicketPriceBusiness);
        inputTicketPriceEconomy = findViewById(R.id.flightTicketPriceEconomy);
        inputAirlineID = findViewById(R.id.flightAirlineID);
        ;


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }

    private void check() {

        String flightNum = inputFlightNumber.getText().toString();
        String depCity = inputDepartureLocationCity.getText().toString();
        String depCountry = inputDepartureLocationCountry.getText().toString();
        String arCity = inputArrivalLocationCity.getText().toString();
        String arCountry = inputArrivalLocationCountry.getText().toString();
        String depTime = inputDepartureTime.getText().toString();
        String arTime = inputArrivalTime.getText().toString();
        String avSeats = inputAvailableSeats.getText().toString();
        String totAvFirst = inputTotalSeatsFirstClass.getText().toString();
        String totAvBusiness = inputTotalSeatsBusinessClass.getText().toString();
        String totAvEconomy = inputTotalSeatsEconomyClass.getText().toString();
        String avFirst = inputAvailableSeatsFirstClass.getText().toString();
        String avBusiness = inputAvailableSeatsBusinessClass.getText().toString();
        String avEconomy = inputAvailableSeatsEconomyClass.getText().toString();
        String tickPriceFirst = inputTicketPriceFirst.getText().toString();
        String tickPriceBusiness = inputTicketPriceBusiness.getText().toString();
        String tickPriceEconomy = inputTicketPriceEconomy.getText().toString();
        String airlineId = inputAirlineID.getText().toString();

        if (flightNum.isEmpty()) {
            showError((EditText) inputFlightNumber, "Empty field");
        } else if (depCity.isEmpty()) {
            showError((EditText) inputDepartureLocationCity, "Empty field");
        } else if (depCountry.isEmpty()) {
            showError((EditText) inputDepartureLocationCountry, "Empty field");
        } else if (arCity.isEmpty()) {
            showError((EditText) inputArrivalLocationCity, "Empty field");
        } else if (arCountry.isEmpty()) {
            showError((EditText) inputArrivalLocationCountry, "Empty field");
        } else if (depTime.isEmpty()) {
            showError((EditText) inputDepartureTime, "Empty field");
        } else if (arTime.isEmpty()) {
            showError((EditText) inputArrivalTime, "Empty field");
        } else if (avSeats.isEmpty()) {
            showError((EditText) inputAvailableSeats, "Empty field");
        } else if (totAvFirst.isEmpty()) {
            showError((EditText) inputTotalSeatsFirstClass, "Empty field");
        } else if (totAvBusiness.isEmpty()) {
            showError((EditText) inputTotalSeatsBusinessClass, "Empty field");
        } else if (totAvEconomy.isEmpty()) {
            showError((EditText) inputTotalSeatsEconomyClass, "Empty field");
        } else if (avFirst.isEmpty()) {
            showError((EditText) inputAvailableSeatsFirstClass, "Empty field");
        } else if (avBusiness.isEmpty()) {
            showError((EditText) inputAvailableSeatsBusinessClass, "Empty field");
        } else if (avEconomy.isEmpty()) {
            showError((EditText) inputAvailableSeatsEconomyClass, "Empty field");
        } else if (tickPriceFirst.isEmpty()) {
            showError((EditText) inputTicketPriceFirst, "Empty field");
        } else if (tickPriceBusiness.isEmpty()) {
            showError((EditText) inputTicketPriceBusiness, "Empty field");
        } else if (tickPriceEconomy.isEmpty()) {
            showError((EditText) inputTicketPriceEconomy, "Empty field");
        } else if (airlineId.isEmpty()) {
            showError((EditText) inputAirlineID, "Empty field");
        }
        else {
            Toast.makeText(this, "Adding Flight!", Toast.LENGTH_SHORT).show();
            FlightNumber = flightNum;
            DepartureLocationCity = depCity;
            DepartureLocationCountry = depCountry;
            ArrivalLocationCity = arCity;
            ArrivalLocationCountry = arCountry;
            DepartureTime = depTime;
            ArrivalTime = arTime;
            AvailableSeats = avSeats;
            TotalSeatsFirstClass = totAvFirst;
            TotalSeatsBusinessClass = totAvBusiness;
            TotalSeatsEconomyClass = totAvEconomy;
            AvailableSeatsFirstClass = avFirst;
            AvailableSeatsBusinessClass = avBusiness;
            AvailableSeatsEconomyClass = avEconomy;
            TicketPriceFirst = tickPriceFirst;
            TicketPriceBusiness = tickPriceBusiness;
            TicketPriceEconomy = tickPriceEconomy;
            AirlineID = airlineId;


            AddFlightActivity.ConnectMySql connectMySql = new AddFlightActivity.ConnectMySql();
            connectMySql.execute("");


        }
//
    }

    private void showError(EditText input, String s) {
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
                System.out.println("Database connection success");

                String result = "";
                Statement st = con.createStatement();
                CallableStatement execProc = con.prepareCall("CALL InsertUniqueFlightProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                execProc.setString(1, FlightNumber);
                execProc.setString(2, DepartureLocationCity);
                execProc.setString(3, DepartureLocationCountry);
                execProc.setString(4, ArrivalLocationCity);
                execProc.setString(5, ArrivalLocationCountry);
                execProc.setString(6, DepartureTime);
                execProc.setString(7, ArrivalTime);
                execProc.setString(8, AvailableSeats);
                execProc.setString(9, TotalSeatsFirstClass);
                execProc.setString(10, TotalSeatsBusinessClass);
                execProc.setString(11, TotalSeatsEconomyClass);
                execProc.setString(12, AvailableSeatsFirstClass);
                execProc.setString(13,AvailableSeatsBusinessClass );
                execProc.setString(14,AvailableSeatsEconomyClass );
                execProc.setString(15, TicketPriceFirst);
                execProc.setString(16, TicketPriceBusiness);
                execProc.setString(17, TicketPriceEconomy);
                execProc.setString(18, AirlineID);
                execProc.execute();

                res = result;
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
           // startActivity(new Intent(Activity_register.this, Activity_login.class));
        }
    }

}