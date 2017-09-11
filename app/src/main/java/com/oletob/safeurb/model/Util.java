package com.oletob.safeurb.model;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Date;

/**
 * Created by evolquez on 9/7/17.
 */

public class Util {

    public static final int CARD_DESCRIPTION_LENGTH = 85;

    private static Util instance = null;


    private Util() {

    }

    /**
     * @return
     */
    public static Util getInstance() {
        if (instance == null)
            instance = new Util();
        return instance;
    }


    /**
     * @param activity
     * @return
     */
    public Location getCurrentLocation(Activity activity) {

        FusedLocationProviderClient flc = LocationServices.getFusedLocationProviderClient(activity);

        final Location[] location = new Location[1];

        if (ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            return null;
        }

        flc.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location loc) {
                location[0] = loc;
            }
        });

        return location[0];
    }

    /**
     * @param start
     * @param end
     * @param toKm
     * @return
     */
    public static double[] calcDistance(LatLng start, LatLng end, boolean toKm){

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(end.latitude - start.latitude);
        double lonDistance = Math.toRadians(end.longitude - start.longitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(start.latitude)) * Math.cos(Math.toRadians(end.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double d = (toKm) ? (R * c) : (R * c * 1000);

        double[] rs = {Math.round(d), 0};

        if(toKm){
            if(Math.round(d) <= 0){
                rs[0] = Math.round((R * c * 1000));
                rs[1] = 1;
            }
        }
        return rs;
    }

    /**
     * @param d1
     * @param d2
     * @return
     */
    public String prettyDateDiff(Date d1, Date d2){

        long dateDiff = (d1.getTime() - d2.getTime()); // difference in milliseconds

        // Declare variables for seconds, minutes, hours, days, weeks, months, years

        long seconds    = ((dateDiff / 1000));
        long minutes    = ((dateDiff / (60 * 1000)));
        long hours      = ((dateDiff / (60 * 60 * 1000)));
        long days       = ((dateDiff / (60 * 60 * 1000 * 24)));
        long weeks      = ((dateDiff / ((60 * 60 * 1000 * 24) * 7)));
        long months     = ((long)(dateDiff / (60 * 60 * 1000 * 24 * 30.41666666)));
        long years      = ((dateDiff / ((long) 60 * 60 * 1000 * 24 * 365)));

        String prettyDate;

        if(seconds < 1)
            prettyDate = "menos de un segundo";
        else if(minutes < 1)
            prettyDate = seconds + ((seconds > 1)? " segundos" : " segundo");
        else if(hours < 1)
            prettyDate = minutes + ((minutes > 1) ? " minutos" : " minuto");
        else if(days < 1)
            prettyDate = hours + ((hours > 1) ? " horas" : " hora");
        else if(weeks < 1)
            prettyDate = days + ((days > 1) ? " días" : " día");
        else if(months < 1)
            prettyDate = weeks + ((weeks > 1) ? " semanas" : " semana");
        else if(years < 1)
            prettyDate = months + ((months > 1) ? " meses" : " mes");
        else
            prettyDate = years + ((years > 1) ? " años" : " año");

        return "Hace "+prettyDate;
    }
}