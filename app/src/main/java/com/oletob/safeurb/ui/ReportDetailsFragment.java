package com.oletob.safeurb.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReportDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportDetailsFragment extends Fragment implements OnMapReadyCallback {

    private OnFragmentInteractionListener mListener;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ReportDetails reportDetails;
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private View mView;
    private ImageView imageIcon;
    private TextView seeImageText;

    public ReportDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static ReportDetailsFragment newInstance(ReportDetails reportDetails) {
        ReportDetailsFragment fragment = new ReportDetailsFragment();
        fragment.reportDetails = reportDetails;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.fragment_report_details, container, false);

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth       = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            mAuth.signInAnonymously().addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        currentUser = mAuth.getCurrentUser();
                        getImageUrl();
                    }
                }
            });
        }else{
            getImageUrl();
        }

        mMapView    = (MapView) mView.findViewById(R.id.reportLocation);
        imageIcon   = (ImageView) mView.findViewById(R.id.imageReport);
        seeImageText= (TextView) mView.findViewById(R.id.txtSeeImageText);

        seeImageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flayout_content, ImageReportFragment.newInstance(reportDetails.getImagePath()));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        ((TextView) mView.findViewById(R.id.txtReportDescription)).setText(reportDetails.getDescription());
        ((TextView) mView.findViewById(R.id.txtDistance)).setText(reportDetails.getDistance());
        ((TextView) mView.findViewById(R.id.txtTime)).setText(reportDetails.getDate());
        //((TextView) mView.findViewById(R.id.txtAuthor)).setText(reportDetails.getAuthor());

        if(mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    private void getImageUrl(){
        mStorageRef.child("reports-images/"+reportDetails.getKey()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                reportDetails.setHasImage(true);
                reportDetails.setImagePath(uri.toString());

                imageIcon.setVisibility(View.VISIBLE);
                seeImageText.setVisibility(View.VISIBLE);
            }
        });
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

        CameraPosition cp = CameraPosition.builder().target(reportDetails.getLocation()).zoom(16).build();

        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));

        mGoogleMap.addMarker(new MarkerOptions().position(reportDetails.getLocation())); // Set marker
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