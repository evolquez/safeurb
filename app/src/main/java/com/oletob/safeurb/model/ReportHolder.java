package com.oletob.safeurb.model;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oletob.safeurb.R;
import com.oletob.safeurb.ui.HomeActivity;
import com.oletob.safeurb.ui.ReportDetailActivity;

/**
 * Created by evolquez on 8/29/17.
 */

public class ReportHolder extends RecyclerView.ViewHolder{

    public TextView type;
    public TextView description;
    public TextView distance;
    public TextView time;

    private Report report;

    public ReportHolder(final View itemView) {
        super(itemView);

        this.type               = itemView.findViewById(R.id.txtReportType);
        this.description        = itemView.findViewById(R.id.txtReportDescription);
        this.distance           = itemView.findViewById(R.id.txtDistance);
        this.time               = itemView.findViewById(R.id.txtTime);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), ReportDetailActivity.class);

                intent.putExtra("type", ((report.type.equals("assault")) ? "Asalto" : "Robo"));
                intent.putExtra("rid", report.id);
                intent.putExtra("description", report.description);
                intent.putExtra("distance", distance.getText().toString());
                intent.putExtra("time", time.getText().toString());
                intent.putExtra("lat", report.latitude);
                intent.putExtra("lng", report.longitude);

                itemView.getContext().startActivity(intent);
            }
        });
    }

    public void setReport(Report report){
        this.report = report;
    }
}
