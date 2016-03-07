package com.example.lee.projectrun;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileViewerActivity extends AppCompatActivity {

    private TextView txtProfileFname, txtProfileLname, txtProfileEmail, txtProfileGender, txtProfileAge,
            txtProfileTeaching, txtProfilePractice, txtProfilePersonalInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_viewer);

        txtProfileFname = (TextView) findViewById(R.id.txtProfileFname);
        txtProfileLname = (TextView) findViewById(R.id.txtProfileLname);
        txtProfileEmail = (TextView) findViewById(R.id.txtProfileEmail);
        txtProfileGender = (TextView) findViewById(R.id.txtProfileGender);
        txtProfileTeaching = (TextView) findViewById(R.id.txtProfileTeaching);
        txtProfilePractice = (TextView) findViewById(R.id.txtProfilePractice);
        txtProfilePersonalInterest = (TextView) findViewById(R.id.txtProfilePersonalInterest);
    }

}
