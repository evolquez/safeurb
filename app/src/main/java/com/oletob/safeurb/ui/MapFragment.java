package com.oletob.safeurb.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oletob.safeurb.R;
import com.oletob.safeurb.model.LocationListener;
import com.oletob.safeurb.model.Report;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback{

    private static final String TAG = "MapFragment";
    private DatabaseReference mReportsReference;

    private OnFragmentInteractionListener mListener;
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private View mView;
    public LocationListener locationInterface;
    private Animation fabOpen, fabClose;

    private boolean isOpen = false;

    private FloatingActionButton fabActionMenu, fabAssault, fabTheif;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(LocationListener listener) {
        MapFragment fragment        = new MapFragment();
        fragment.locationInterface  = listener;

        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);

        fabActionMenu   = (FloatingActionButton) mView.findViewById(R.id.fab_action_menu);
        fabAssault      = (FloatingActionButton) mView.findViewById(R.id.fab_assault);
        fabTheif        = (FloatingActionButton) mView.findViewById(R.id.fab_theif);
        fabOpen         = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fabClose        = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);

        // Set firebase reference

        mReportsReference = FirebaseDatabase.getInstance().getReference().child("reports");

        fabActionMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            if(isOpen){

                fabAssault.startAnimation(fabClose);
                fabTheif.startAnimation(fabClose);

                fabAssault.setClickable(false);
                fabTheif.setClickable(false);

                isOpen = false;

            }else{
                fabAssault.startAnimation(fabOpen);
                fabTheif.startAnimation(fabOpen);

                fabAssault.setClickable(true);
                fabTheif.setClickable(true);

                isOpen = true;
            }
            }
        });

        fabAssault.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startReportActivity("assault");
            }
        });

        fabTheif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startReportActivity("theif");
            }
        });

        return mView;
    }

    private void startReportActivity(String type) {
        Intent i = new Intent(getActivity(), PublishReportActivity.class);

        i.putExtra("type", type);
        i.putExtra("lat", this.locationInterface.getLastLocation().getLatitude());
        i.putExtra("lng", this.locationInterface.getLastLocation().getLongitude());

        startActivity(i);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.safeurb_map);

        if(mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;

        LatLng current = new LatLng(18.4855, -69.8731);

        if(this.locationInterface != null){
            if(this.locationInterface.getLastLocation() != null){

                current = new LatLng(
                    this.locationInterface.getLastLocation().getLatitude(),
                    this.locationInterface.getLastLocation().getLongitude()
                );
            }
        }

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mGoogleMap.setMyLocationEnabled(true);

        CameraPosition cp = CameraPosition.builder().target(current).zoom(8).build();

        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));

        this.addMapMarkers();
        this.addChildListener();
    }

    /**
     *  This method depends on mGoogleMap not tu be null, so thats why its called after map initialized.
     */
    private void addMapMarkers(){

        mReportsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Report report = child.getValue(Report.class);
                    mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(report.latitude,
                                                                        report.longitude)).title(report.description));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Add child listener, so everytime a new report is added, firebase will notify all devices
     */
    private void addChildListener(){
        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Report report = dataSnapshot.getValue(Report.class);

                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(report.latitude,
                                                        report.longitude)).title(report.description));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mReportsReference.addChildEventListener(listener);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}