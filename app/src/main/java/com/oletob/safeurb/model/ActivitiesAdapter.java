package com.oletob.safeurb.model;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.oletob.safeurb.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by evolquez on 8/29/17.
 */

public class ActivitiesAdapter extends RecyclerView.Adapter<ReportHolder> {

    private final ArrayList<Report> entries;
    private final Context context;
    private final Location currentLocation;

    public ActivitiesAdapter(Context context, ArrayList<Report> entries, Location location){
        this.context            = context;
        this.entries            = entries;
        this.currentLocation    = location;

    }
    @Override
    public ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_activity_report, parent, false);

        return new ReportHolder(view);

    }

    @Override
    public void onBindViewHolder(ReportHolder holder, int position) {
        Report r = this.entries.get(position);

        int textColor = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            textColor = (r.type.equals("assault")) ? this.context.getColor(R.color.assault_color) : this.context.getColor(R.color.theif_color);
        }else{
            textColor = (r.type.equals("assault")) ? this.context.getResources().getColor(R.color.assault_color) : this.context.getResources().getColor(R.color.theif_color);
        }

        holder.type.setText(( r.type.equals("assault")) ? "Asalto" : "Robo");
        holder.type.setTextColor(textColor);

        holder.fullDescription.setText(r.description); // Set full description to show on details

        if(r.description.length() > Util.CARD_DESCRIPTION_LENGTH)
            r.description = r.description.substring(0, Util.CARD_DESCRIPTION_LENGTH - 1)+" ...";

        holder.description.setText(r.description);

        double[] distanceAndUnits = Util.calcDistance(new LatLng(currentLocation.getLatitude(),
                                                                 currentLocation.getLongitude()),
                                            new LatLng(r.latitude, r.longitude), true);

        String unit = (distanceAndUnits[1] > 0) ? " m" : " km";
        String text = distanceAndUnits[0] + unit;

        holder.distance.setText(text);

        String prettyTime = Util.getInstance().prettyDateDiff(Calendar.getInstance().getTime(), r.reportDate);

        holder.time.setText(prettyTime);


    }

    @Override
    public int getItemCount() {
        return this.entries.size();
    }
}
