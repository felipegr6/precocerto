package br.com.fggs1.gs1project.model;

public class Company {

    private long id;
    private String ad;
    private double latitude;
    private double longitude;
    private String name;

    public Company(long id, String ad, double latitude, double longitude, String name) {
        this.id = id;
        this.ad = ad;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getAd() {
        return ad;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }
}
