package com.oletob.safeurb.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by evolquez on 8/27/17.
 */

@IgnoreExtraProperties
public class Report {

    public int id;
    public String description;
    public String type;
    public String picture;
    public LatLng latLng;
    public Date reportDate;
    public Date createdAt;

    public Report() { }

    public Report(int id, String description, String type, LatLng latLng, String picture,
                  Date reportDate, Date createdAt) {
        this.id             = id;
        this.description    = description;
        this.type           = type;
        this.latLng         = latLng;
        this.picture        = picture;
        this.reportDate     = reportDate;
        this.createdAt      = createdAt;
    }

    public Report(String description, String type, LatLng latLng, String picture, Date reportDate,
                  Date createdAt) {
        this.description    = description;
        this.type           = type;
        this.latLng         = latLng;
        this.picture        = picture;
        this.reportDate     = reportDate;
        this.createdAt      = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getType(){
        return type;
    }

    public LatLng getLatLng(){
        return latLng;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setLatLng(LatLng latLng){
        this.latLng = latLng;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
