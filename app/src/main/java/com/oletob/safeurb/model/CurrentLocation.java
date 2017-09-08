package com.oletob.safeurb.model;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.oletob.safeurb.R;

/**
 * Created by evolquez on 9/7/17.
 */

public class CurrentLocation {

    private static CurrentLocation instance = null;

    private CurrentLocation() {

    }

    /**
     * @return
     */
    public static CurrentLocation getInstance() {
        if (instance == null)
            instance = new CurrentLocation();
        return instance;
    }


    public Location get(Activity activity) {

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
}