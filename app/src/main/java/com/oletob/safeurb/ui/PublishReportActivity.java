package com.oletob.safeurb.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oletob.safeurb.R;
import com.oletob.safeurb.model.Report;
import com.oletob.safeurb.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PublishReportActivity extends AppCompatActivity implements View.OnClickListener,
                                                                        DatePickerDialog.OnDateSetListener,
                                                                        TimePickerDialog.OnTimeSetListener{

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private int[] yearMonthDay = new int[3];

    private SimpleDateFormat dateTimeFormat;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    private Calendar calendarDate;
    private Calendar calendarTime;

    private Button btnPublish;
    private TextView txtTitleAction;
    private ImageButton btnTakePhoto;
    private ImageView imageTaken;

    private EditText situation;
    private String date;
    private String time;
    private String type;

    private Bitmap imageReportBitmap;

    private LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_publish_report);

        getSupportActionBar().setTitle(R.string.publish_report_title);

        // Get data from intent
        type                    = getIntent().getStringExtra("type");
        String[] reportTypes    = getResources().getStringArray(R.array.report_types);
        this.currentLocation    = new LatLng(getIntent().getDoubleExtra("lat", 18.5001),
                                    getIntent().getDoubleExtra("lng", -69.9886));

        dateTimeFormat  = new SimpleDateFormat("dd-MM-yyyy hh:mm a", java.util.Locale.getDefault());
        dateFormat      = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault());
        timeFormat      = new SimpleDateFormat("hh:mm a", java.util.Locale.getDefault());

        // Instantiate views
        btnPublish      = (Button)findViewById(R.id.btnPublish);
        btnTakePhoto    = (ImageButton)findViewById(R.id.btnTakePicture);
        imageTaken      = (ImageView)findViewById(R.id.imageTaken);
        txtTitleAction  = (TextView)findViewById(R.id.txt_title_action);
        situation       = (EditText)findViewById(R.id.report_description);

        // Set Onclick listener to views

        btnTakePhoto.setOnClickListener(this);
        imageTaken.setOnClickListener(this);
        btnPublish.setOnClickListener(this);

        setDateTime(dateTimeFormat.format(new Date()));

        txtTitleAction.setText((type.equals("assault")) ? reportTypes[0] : reportTypes[1]);

        calendarDate = Calendar.getInstance();
        calendarTime = Calendar.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnPublish:
                if((situation.getText().toString().length() > 0 && situation.getText().toString() != "")
                        && calendarTime != null && calendarDate != null){

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                    String date         = df.format(calendarDate.getTime());
                    df                  = new SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault());
                    date                += " "+df.format(calendarTime.getTime());

                    df              = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
                    Date dateTime   = null;

                    try {
                        dateTime = df.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(dateTime != null){

                        Report report = new Report(situation.getText().toString(), type,
                                    this.currentLocation.latitude, this.currentLocation.longitude, "picture", dateTime, new Date());

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("reports");
                        DatabaseReference newReport = mDatabase.push();

                        newReport.setValue(report, new DatabaseReference.CompletionListener(){
                            @Override
                            public void onComplete(DatabaseError databaseError,
                                                   DatabaseReference databaseReference) {

                                String message = getString(R.string.thanks_for_reporting);

                                if(databaseError != null) {
                                    message = "Los datos no se guardaron, código de error: "+databaseError.getCode();
                                }

                                InformationDialog dialog = InformationDialog.newInstance("Notificación", message, true);
                                dialog.show(getFragmentManager(), "dialog");
                            }
                        });
                    }

                }else{
                    InformationDialog dialog = InformationDialog.newInstance(getString(R.string.form_valitation_title),
                                                                             getString(R.string.form_validation_message), false);
                    dialog.show(getFragmentManager(), "dialog");
                }
                break;
            case R.id.btnTakePicture:
                this.dispatchTakePictureIntent();
                break;
            case R.id.imageTaken:
                Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * @param dateTime
     */
    private void setDateTime(String dateTime){
        ((TextView) findViewById(R.id.show_date)).setText(dateTime);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        timePicker();

        calendarDate    = new GregorianCalendar(i, i1, i2);
        yearMonthDay[0] = i;
        yearMonthDay[1] = i1;
        yearMonthDay[2] = i2;

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

        calendarTime = new GregorianCalendar(yearMonthDay[0], yearMonthDay[1], yearMonthDay[2],
                                hourOfDay, minute, 0);
        String s = dateFormat.format(calendarDate.getTime());
        s += " "+timeFormat.format(calendarTime.getTime());

        setDateTime(s);
    }

    /**
     * @param view
     */
    public void datePicker(View view){
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "date");
    }

    public void timePicker(){
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.show(getFragmentManager(), "time");
    }

    public static class DatePickerFragment extends DialogFragment{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar calendar = Calendar.getInstance();

            int year    = calendar.get(Calendar.YEAR);
            int month   = calendar.get(Calendar.MONTH);
            int day     = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)getActivity(), year, month, day);
        }
    }

    public static class TimePickerFragment extends DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c    = Calendar.getInstance();
            int hour            = c.get(Calendar.HOUR_OF_DAY);
            int minute          = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener)getActivity(), hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras       = data.getExtras();
            imageReportBitmap  = (Bitmap) extras.get("data");

            ((ImageView)findViewById(R.id.imageTaken)).setImageBitmap(imageReportBitmap);
        }
    }

}