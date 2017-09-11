package com.oletob.safeurb.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by evolquez on 9/10/17.
 */

public class ReportDetails {
    private String key;
    private String distance;
    private String date;
    private String imagePath;
    private boolean hasImage;
    private String description;
    private LatLng location;
    private String author;

    public ReportDetails() {}

    public ReportDetails(String key, String distance, String date, String imagePath, boolean hasImage,
                         String description, LatLng location, String author) {

        this.key            = key;
        this.distance       = distance;
        this.date           = date;
        this.imagePath      = imagePath;
        this.hasImage       = hasImage;
        this.description    = description;
        this.location       = location;
        this.author         = author;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}