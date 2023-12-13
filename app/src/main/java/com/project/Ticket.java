package com.project;

public class Ticket {
    String flightStart;
   // String flightDestination;
    String flightDepartureDate;
   /* String flightReturnDate;
    Integer seatNumber;
    String flightClass;
    String passengerEmail;
    String passengerName;*/

    public Ticket(String flightStart, String flightDepartureDate) {
        this.flightStart = flightStart;
        this.flightDepartureDate = flightDepartureDate;
    }

    /*public Ticket(String flightStart, String flightDestination, String flightDepartureDate, String flightReturnDate, Integer seatNumber, String flightClass, String passengerEmail, String passengerName) {
        this.flightStart = flightStart;
        this.flightDestination = flightDestination;
        this.flightDepartureDate = flightDepartureDate;
        this.flightReturnDate = flightReturnDate;
        this.seatNumber = seatNumber;
        this.flightClass = flightClass;
        this.passengerEmail = passengerEmail;
        this.passengerName = passengerName;
    }
*/
    public String getFlightStart() {
        return flightStart;
    }

    public void setFlightStart(String flightStart) {
        this.flightStart = flightStart;
    }

    public String getFlightDepartureDate() {
        return flightDepartureDate;
    }

    public void setFlightDepartureDate(String flightDepartureDate) {
        this.flightDepartureDate = flightDepartureDate;
    }
}
