package com.oletob.safeurb.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oletob.safeurb.R;

import java.util.ArrayList;

/**
 * Created by evolquez on 8/29/17.
 */

public class ActivitiesAdapter extends RecyclerView.Adapter<ReportHolder> {

    private final ArrayList<Report> entries;
    private final Context context;

    public ActivitiesAdapter(Context context, ArrayList<Report> entries){
        this.context = context;
        this.entries = entries;
    }
    @Override
    public ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_activity_report, parent, false);

        return new ReportHolder(view);

    }

    @Override
    public void onBindViewHolder(ReportHolder holder, int position) {
        Report r = this.entries.get(position);

        int bgColor = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            bgColor = (r.type.equals("assault")) ? this.context.getColor(R.color.assault_color) : this.context.getColor(R.color.theif_color);
        }else{
            bgColor = (r.type.equals("assault")) ? this.context.getResources().getColor(R.color.assault_color) : this.context.getResources().getColor(R.color.theif_color);
        }

        holder.type.setText(( r.type.equals("assault")) ? "Asalto" : "Robo");
        holder.type.setBackgroundColor(bgColor);

        holder.description.setText(r.description);
    }

    @Override
    public int getItemCount() {
        return this.entries.size();
    }
}
