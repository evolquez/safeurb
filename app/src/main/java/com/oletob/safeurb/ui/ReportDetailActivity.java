package com.oletob.safeurb.ui;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oletob.safeurb.R;

import org.w3c.dom.Text;

public class ReportDetailActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mGoogleMap;
    private LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        getSupportActionBar().setTitle(getString(R.string.report_details));

        TextView type        = (TextView)findViewById(R.id.txtReportType);
        TextView description = (TextView)findViewById(R.id.txtReportDescription);
        TextView distance    = (TextView)findViewById(R.id.txtDistance);
        TextView time        = (TextView)findViewById(R.id.txtTime);

        if(getIntent().getStringExtra("type") != null)
            type.setText(getIntent().getStringExtra("type"));
        if(getIntent().getStringExtra("description") != null)
            description.setText(getIntent().getStringExtra("description"));
        if(getIntent().getStringExtra("distance") != null)
            distance.setText(getIntent().getStringExtra("distance"));
        if(getIntent().getStringExtra("time") != null)
            time.setText(getIntent().getStringExtra("time"));

        location = new LatLng(getIntent().getDoubleExtra("lat", 18.4855), getIntent().getDoubleExtra("lng", -69.8731));


        MapView mMapView = (MapView) findViewById(R.id.reportLocation);

        if(mMapView != null){

            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getApplicationContext());

        mGoogleMap = googleMap;

        CameraPosition cp = CameraPosition.builder().target(location).zoom(14).build();

        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));

        mGoogleMap.addMarker(new MarkerOptions().position(location)); // Set marker
    }
}
