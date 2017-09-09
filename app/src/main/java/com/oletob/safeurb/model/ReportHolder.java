package com.oletob.safeurb.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oletob.safeurb.R;

/**
 * Created by evolquez on 8/29/17.
 */

public class ReportHolder extends RecyclerView.ViewHolder{
    public TextView type;
    public TextView description;
    public TextView distance;
    public TextView time;

    public ReportHolder(View itemView) {
        super(itemView);

        this.type           = itemView.findViewById(R.id.txtReportType);
        this.description    = itemView.findViewById(R.id.txtReportDescription);
        this.distance       = itemView.findViewById(R.id.txtDistance);
        this.time           = itemView.findViewById(R.id.txtTime);

    }
}
