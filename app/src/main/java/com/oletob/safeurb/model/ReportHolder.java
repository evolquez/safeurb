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
    public TextView fullDescription;
    public TextView distance;
    public TextView time;

    public ReportHolder(final View itemView) {
        super(itemView);

        this.type               = itemView.findViewById(R.id.txtReportType);
        this.description        = itemView.findViewById(R.id.txtReportDescription);
        this.fullDescription    = itemView.findViewById(R.id.txtFullReportDescription);
        this.distance           = itemView.findViewById(R.id.txtDistance);
        this.time               = itemView.findViewById(R.id.txtTime);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), ReportDetailActivity.class);

                intent.putExtra("type", type.getText().toString());
                intent.putExtra("description", fullDescription.getText().toString());
                intent.putExtra("distance", distance.getText().toString());
                intent.putExtra("time", time.getText().toString());

                itemView.getContext().startActivity(intent);
            }
        });
    }
}
