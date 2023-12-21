package com.project;

import java.util.Date;

//TICKET CLASS THAT HOLDS INFORMATION ABOUT THE TICKET
public class Ticket {
    String ticket_depart;
    String ticket_arrival;
    String ticket_date_depart;
    String ticket_date_arrival;
    String ticket_duration;
    String ticket_company;
    String ticket_price;

    //===== NEEDED FOR SQL! ====//
    private int flightID;

    public int getFlightID() {
        return flightID;
    }
    //===== NEEDED FOR SQL! ====//
    public Ticket(String ticket_depart, String ticket_arrival, String ticket_date_depart, String ticket_date_arrival, String ticket_duration, String ticket_company, String ticket_price) {
        this.ticket_depart = ticket_depart;
        this.ticket_arrival = ticket_arrival;
        this.ticket_date_depart = ticket_date_depart;
        this.ticket_date_arrival = ticket_date_arrival;
        this.ticket_duration = ticket_duration;
        this.ticket_company = ticket_company;
        this.ticket_price = ticket_price;
    }
    public Ticket(String ticket_depart, String ticket_arrival, String ticket_date_depart, String ticket_date_arrival, String ticket_duration, String ticket_company, String ticket_price, int idFlight) {
        this.ticket_depart = ticket_depart;
        this.ticket_arrival = ticket_arrival;
        this.ticket_date_depart = ticket_date_depart;
        this.ticket_date_arrival = ticket_date_arrival;
        this.ticket_duration = ticket_duration;
        this.ticket_company = ticket_company;
        this.ticket_price = ticket_price;
        this.flightID = idFlight;
    }

    public String getTicket_depart() {
        return ticket_depart;
    }

    public void setTicket_depart(String ticket_depart) {
        this.ticket_depart = ticket_depart;
    }

    public String getTicket_arrival() {
        return ticket_arrival;
    }

    public void setTicket_arrival(String ticket_arrival) {
        this.ticket_arrival = ticket_arrival;
    }

    public String getTicket_date_depart() {
        return ticket_date_depart;
    }

    public void setTicket_date_depart(String ticket_date_depart) {
        this.ticket_date_depart = ticket_date_depart;
    }

    public String getTicket_date_arrival() {
        return ticket_date_arrival;
    }

    public void setTicket_date_arrival(String ticket_date_arrival) {
        this.ticket_date_arrival = ticket_date_arrival;
    }

    public String getTicket_duration() {
        return ticket_duration;
    }

    public void setTicket_duration(String ticket_duration) {
        this.ticket_duration = ticket_duration;
    }

    public String getTicket_company() {
        return ticket_company;
    }

    public void setTicket_company(String ticket_company) {
        this.ticket_company = ticket_company;
    }

    public String getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(String ticket_price) {
        this.ticket_price = ticket_price;
    }
}
