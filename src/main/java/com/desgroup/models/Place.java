package com.desgroup.models;

public class Place {
    private int idPlace;
    private String country;
    private String city;
    private String placeName;
    private String address;
    private int capacity;

    public Place(String country, String city, String placeName, String address, int capacity) {
        this.country = country;
        this.city = city;
        this.placeName = placeName;
        this.address = address;
        this.capacity = capacity;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}
