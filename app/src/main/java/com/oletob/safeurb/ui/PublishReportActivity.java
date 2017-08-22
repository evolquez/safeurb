package com.oletob.safeurb.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.oletob.safeurb.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PublishReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private TextView txtTitleAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_publish_report);

        getSupportActionBar().setTitle(R.string.publish_report_title);

        txtTitleAction = (TextView)findViewById(R.id.txt_title_action);

        // Get data from intent
        String type = getIntent().getStringExtra("type");
        String[] reportTypes = getResources().getStringArray(R.array.report_types);

        txtTitleAction.setText((type.equals("assault")) ? reportTypes[0] : reportTypes[1]);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = new GregorianCalendar(i, i1, i2);
        setDate(calendar);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }

    /**
     * @param view
     */
    public void datePicker(View view){
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "date");
    }

    public void timePicker(View view){

    }

    public void setDate(final Calendar calendar){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.show_date)).setText(dateFormat.format(calendar.getTime()));
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
}