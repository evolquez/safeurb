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

/**
 * Created by evolquez on 9/7/17.
 */

public class Util {

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
}