package com.example.lee.projectrun;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditUserProfileActivity extends AppCompatActivity {


    private EditText txtEditFirstName, txtEditLastName;
    private EditText txtEditEmailAddress;
    private EditText txtEditPassword;
    private EditText txtEditAge;
    private EditText txtEditPersonalInterest;
    private RadioButton radioBtnMale, radioBtnFemale;
    private RadioGroup radioGroupGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        txtEditFirstName = (EditText) findViewById(R.id.txtEditFirstName);
        txtEditLastName = (EditText) findViewById(R.id.txtEditLastName);
        txtEditEmailAddress = (EditText) findViewById(R.id.txtEditEmailAddress);
        txtEditPassword = (EditText) findViewById(R.id.txtEditPassword);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        radioBtnMale = (RadioButton) findViewById(R.id.radioBtnMale);
        radioBtnFemale = (RadioButton) findViewById(R.id.radioBtnFemale);
        txtEditAge = (EditText) findViewById(R.id.txtEditAge);
        txtEditPersonalInterest = (EditText) findViewById(R.id.txtEditPersonalInterests);

    }

}
