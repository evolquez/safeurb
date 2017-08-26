package com.oletob.safeurb.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.oletob.safeurb.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class PublishReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
                                                                        TimePickerDialog.OnTimeSetListener{

    private SimpleDateFormat dateTimeFormat;

    private int[] yearMonthDay = new int[3];

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    private Calendar calendarDate;
    private Calendar calendarTime;
    private TextView txtTitleAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_publish_report);

        getSupportActionBar().setTitle(R.string.publish_report_title);

        txtTitleAction = (TextView)findViewById(R.id.txt_title_action);

        // Get data from intent
        String type             = getIntent().getStringExtra("type");
        String[] reportTypes    = getResources().getStringArray(R.array.report_types);

        txtTitleAction.setText((type.equals("assault")) ? reportTypes[0] : reportTypes[1]);

        dateTimeFormat  = new SimpleDateFormat("dd-MM-yyyy hh:mm a", java.util.Locale.getDefault());
        dateFormat      = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault());
        timeFormat      = new SimpleDateFormat("hh:mm a", java.util.Locale.getDefault());

        setDateTime(dateTimeFormat.format(new Date()));
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
        calendarDate = new GregorianCalendar(i, i1, i2);

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
}