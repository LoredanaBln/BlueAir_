package com.project;

public class Hotel {
    private String hotelName, hotelDescription, hotelRating, hotelContact, hotelEmail, hotelWebsite, hotelCheckIn, hotelCheckOut, hotelAmenities, hotelPolicies, hotelCity, hotelCountry;
    private int hotelID;
    public Hotel(int hotelID, String hotelName, String hotelDescription, String hotelRating, String hotelContact, String hotelEmail, String hotelWebsite, String hotelCheckIn, String hotelCheckOut, String hotelAmenities, String hotelPolicies, String hotelCity, String hotelCountry) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.hotelDescription = hotelDescription;
        this.hotelRating = hotelRating;
        this.hotelContact = hotelContact;
        this.hotelEmail = hotelEmail;
        this.hotelWebsite = hotelWebsite;
        this.hotelCheckIn = hotelCheckIn;
        this.hotelCheckOut = hotelCheckOut;
        this.hotelAmenities = hotelAmenities;
        this.hotelPolicies = hotelPolicies;
        this.hotelCity = hotelCity;
        this.hotelCountry = hotelCountry;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getHotelContact() {
        return hotelContact;
    }

    public void setHotelContact(String hotelContact) {
        this.hotelContact = hotelContact;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setHotelDescription(String hotelDescription) {
        this.hotelDescription = hotelDescription;
    }

    public void setHotelRating(String hotelRating) {
        this.hotelRating = hotelRating;
    }

    public void setHotelEmail(String hotelEmail) {
        this.hotelEmail = hotelEmail;
    }

    public void setHotelWebsite(String hotelWebsite) {
        this.hotelWebsite = hotelWebsite;
    }

    public void setHotelCheckIn(String hotelCheckIn) {
        this.hotelCheckIn = hotelCheckIn;
    }

    public void setHotelCheckOut(String hotelCheckOut) {
        this.hotelCheckOut = hotelCheckOut;
    }

    public void setHotelAmenities(String hotelAmenities) {
        this.hotelAmenities = hotelAmenities;
    }

    public void setHotelPolicies(String hotelPolicies) {
        this.hotelPolicies = hotelPolicies;
    }

    public void setHotelCity(String hotelCity) {
        this.hotelCity = hotelCity;
    }

    public void setHotelCountry(String hotelCountry) {
        this.hotelCountry = hotelCountry;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getHotelDescription() {
        return hotelDescription;
    }

    public String getHotelRating() {
        return hotelRating;
    }

    public String getHotelEmail() {
        return hotelEmail;
    }

    public String getHotelWebsite() {
        return hotelWebsite;
    }

    public String getHotelCheckIn() {
        return hotelCheckIn;
    }

    public String getHotelCheckOut() {
        return hotelCheckOut;
    }

    public String getHotelAmenities() {
        return hotelAmenities;
    }

    public String getHotelPolicies() {
        return hotelPolicies;
    }

    public String getHotelCity() {
        return hotelCity;
    }

    public String getHotelCountry() {
        return hotelCountry;
    }
}
