package com.oletob.safeurb.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.oletob.safeurb.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setTitle(getString(R.string.about));


        (findViewById(R.id.txtAuthor)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent linkedIn = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.author_linkedin_profile)));
                startActivity(linkedIn);
            }
        });
    }
}
