package com.example.android.quakereport;

import java.util.Date;

/**
 * Created by Cavid on 5/29/2017.
 */

public class EarthQuake {
    private String mUrl;
    private double magnitude;
    private String City;
    private String mDate;
    private long mTimeInMilliSeconds;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public long getTimeInMilliSeconds() {
        return mTimeInMilliSeconds;
    }

    public void setTimeInMilliSeconds(long timeInMilliSeconds) {
        mTimeInMilliSeconds = timeInMilliSeconds;
    }

    public EarthQuake(double magnitude, String city, long timeInMilliSeconds, String url) {
        this.mUrl = url;
        this.magnitude = magnitude;
        City = city;
        mTimeInMilliSeconds = timeInMilliSeconds;
    }





    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

}
