package com.oletob.safeurb.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by evolquez on 8/27/17.
 */

@IgnoreExtraProperties
public class Report {

    public String description;
    public String type;
    public double latitude;
    public double longitude;
    public String picture;
    public Date reportDate;
    public Date createdAt;

    public Report() { }

    public Report(String description, String type, double latitude, double longitude, String picture, Date reportDate,
                  Date createdAt) {
        this.description    = description;
        this.type           = type;
        this.latitude       = latitude;
        this.longitude      = longitude;
        this.picture        = picture;
        this.reportDate     = reportDate;
        this.createdAt      = createdAt;
    }

    @Override
    public String toString() {
        return "Report{" +
                "description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", picture='" + picture + '\'' +
                ", reportDate=" + reportDate +
                ", createdAt=" + createdAt +
                '}';
    }
}
