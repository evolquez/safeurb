package com.oletob.safeurb.ui;

import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.oletob.safeurb.R;

import org.w3c.dom.Text;

import java.io.IOException;

public class ReportDetailActivity extends AppCompatActivity implements OnMapReadyCallback{

    private String reportID;
    private GoogleMap mGoogleMap;
    private LatLng location;
    private ImageView reportImage;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        getSupportActionBar().setTitle(getString(R.string.report_details));

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth       = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        currentUser = mAuth.getCurrentUser();
                    }
                }
            });
        }

        TextView type        = (TextView)findViewById(R.id.txtReportType);
        TextView description = (TextView)findViewById(R.id.txtReportDescription);
        TextView distance    = (TextView)findViewById(R.id.txtDistance);
        TextView time        = (TextView)findViewById(R.id.txtTime);

        reportID = getIntent().getStringExtra("rid");

        if(getIntent().getStringExtra("type") != null)
            type.setText(getIntent().getStringExtra("type"));
        if(getIntent().getStringExtra("description") != null)
            description.setText(getIntent().getStringExtra("description"));
        if(getIntent().getStringExtra("distance") != null)
            distance.setText(getIntent().getStringExtra("distance"));
        if(getIntent().getStringExtra("time") != null)
            time.setText(getIntent().getStringExtra("time"));

        reportImage = (ImageView) findViewById(R.id.reportImage);

        reportImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUser != null){
                    /*mStorageRef.child("reports-images/"+reportID+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(ReportDetailActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ReportDetailActivity.this, "No image found "+reportID, Toast.LENGTH_SHORT).show();
                        }
                    });*/
                }
            }
        });

        location = new LatLng(getIntent().getDoubleExtra("lat", 18.4855),
                                getIntent().getDoubleExtra("lng", -69.8731));

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
