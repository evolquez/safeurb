package com.oletob.safeurb.ui;

import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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
import com.oletob.safeurb.model.ReportDetails;

import org.w3c.dom.Text;

import java.io.IOException;

public class ReportDetailActivity extends AppCompatActivity implements ReportDetailsFragment.OnFragmentInteractionListener,
                                                                        ImageReportFragment.OnFragmentInteractionListener{

    private ReportDetails reportDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        getSupportActionBar().setTitle(getString(R.string.report_details));

        ((TextView)findViewById(R.id.txtReportType)).setText(getIntent().getStringExtra("type"));

        reportDetails = new ReportDetails();

        if(getIntent().getStringExtra("description") != null)
            reportDetails.setDescription(getIntent().getStringExtra("description"));
        if(getIntent().getStringExtra("distance") != null)
            reportDetails.setDistance(getIntent().getStringExtra("distance"));
        if(getIntent().getStringExtra("time") != null)
            reportDetails.setDate(getIntent().getStringExtra("time"));
        if(getIntent().getStringExtra("rid") != null)
            reportDetails.setKey(getIntent().getStringExtra("rid"));

        reportDetails.setAuthor(getString(R.string.by_anonymous_author));

        reportDetails.setLocation(new LatLng(getIntent().getDoubleExtra("lat", 18.4855),
                                             getIntent().getDoubleExtra("lng", -69.8731)));

        if (savedInstanceState != null) {
            return;
        }

        getSupportFragmentManager().beginTransaction().add(R.id.flayout_content,
                                                ReportDetailsFragment.newInstance(reportDetails)).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}