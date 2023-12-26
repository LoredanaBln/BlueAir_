package com.project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//CLASS FOR VIEWING TICKET AFTER CLICKING ON ONE!
public class HotelView extends AppCompatActivity {
    private static final String url = "jdbc:mysql://" + DBConnectionCredentials.ip + "/" + DBConnectionCredentials.databaseName + "?characterEncoding=latin1&autoReconnect=true&useSSL=false";
    private static final String user = DBConnectionCredentials.username;
    private static final String pass = DBConnectionCredentials.password;
    private Hotel hotel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expanded_hotel_recyler_view);
        hotel = (Hotel) getIntent().getSerializableExtra("Hotel");

        TextView hotelName = findViewById(R.id.hotelName);
        TextView hotelLocation = findViewById(R.id.hotelLocation);
        TextView hotelDescr = findViewById(R.id.hotelDescription);
        TextView hotelCheckin = findViewById(R.id.hotelCheckIn);
        TextView hotelcheckout = findViewById(R.id.hotelCheckOut);
        TextView hotelF = findViewById(R.id.hotelFacilities);
        TextView hotelR = findViewById(R.id.hotelRequests);
        TextView hotelPhone = findViewById(R.id.hotelPhoneNumber);
        TextView hotelEmail = findViewById(R.id.hotelEmail);
        TextView hotelWeb = findViewById(R.id.hotelWebsite);
        TextView hotelRating = findViewById(R.id.hotelRating);

        hotelName.setText(hotel.getHotelName());
        hotelLocation.setText(hotel.getHotelCity() + ", " + hotel.getHotelCountry());
        hotelDescr.setText(hotel.getHotelDescription());
        hotelCheckin.setText(hotel.getHotelCheckIn());
        hotelcheckout.setText(hotel.getHotelCheckOut());
        hotelF.setText(hotel.getHotelAmenities());
        hotelR.setText(hotel.getHotelPolicies());
        hotelPhone.setText(hotel.getHotelContact());
        hotelEmail.setText(hotel.getHotelEmail());
        hotelWeb.setText(hotel.getHotelWebsite());
        hotelRating.setText(hotel.getHotelRating());
    }

}