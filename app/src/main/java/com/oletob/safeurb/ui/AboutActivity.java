package com.oletob.safeurb.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.oletob.safeurb.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setTitle(getString(R.string.about));


    }
}
