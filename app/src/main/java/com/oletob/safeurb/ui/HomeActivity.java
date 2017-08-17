package com.oletob.safeurb.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.oletob.safeurb.R;
import com.oletob.safeurb.model.LocationListener;

public class HomeActivity extends AppCompatActivity implements MapFragment.OnFragmentInteractionListener,
                                                                ActivitiesFragment.OnFragmentInteractionListener,
                                                                LocationListener,
                                                                BottomNavigationView.OnNavigationItemSelectedListener{

    public static final int MY_APP_REQUEST_PERMISSION = 0x100;

    private FusedLocationProviderClient mFusedLocationClient;
    private Location lastLocation;
    private BottomNavigationView navView;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navView = (BottomNavigationView)findViewById(R.id.navigation);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        navView.setOnNavigationItemSelectedListener(this);
        mapFragment = MapFragment.newInstance(HomeActivity.this);

        loadLastLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if(requestCode == MY_APP_REQUEST_PERMISSION){
            int i = 0;

            boolean locationEnabled = true;

            for(String permission : permissions){
                if(permission.equals(Manifest.permission.ACCESS_FINE_LOCATION) ||
                        permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)){
                    locationEnabled &= grantResults[i] == PackageManager.PERMISSION_GRANTED;
                }
                i++;
            }

            if(locationEnabled){
                this.loadLastLocation();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public Location getLastLocation() {

        return this.lastLocation;
    }

    private void loadLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, MY_APP_REQUEST_PERMISSION);
            return;
        }

        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.

                if (location != null) {
                    lastLocation = location;
                    setMapView();
                    navView.setSelectedItemId(R.id.navigation_map);
                }
            }
        });
    }

    private void setMapView(){

        getSupportFragmentManager().beginTransaction().
                replace(R.id.frame_layout, mapFragment).
                addToBackStack("Map Fragment").commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_activities:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout, ActivitiesFragment.newInstance("1","2")).
                        addToBackStack("Activities Fragment").commit();
                return true;
            case R.id.navigation_map:
                setMapView();
                return true;
            /*case R.id.navigation_notifications:

                return true;*/
        }
        return false;
    }
}