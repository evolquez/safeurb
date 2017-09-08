package com.oletob.safeurb.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by evolquez on 8/27/17.
 */

@IgnoreExtraProperties
public class Report {

    public String id;
    public String description;
    public String type;
    public double latitude;
    public double longitude;
    public Date reportDate;
    public Date createdAt;

    public Report() { }

    public Report(String description, String type, double latitude, double longitude, Date reportDate,
                  Date createdAt) {
        this.description    = description;
        this.type           = type;
        this.latitude       = latitude;
        this.longitude      = longitude;
        this.reportDate     = reportDate;
        this.createdAt      = createdAt;
    }

    public void setId(String id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Report{" +
                "description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", reportDate=" + reportDate +
                ", createdAt=" + createdAt +
                '}';
    }
}
