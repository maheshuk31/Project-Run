package com.example.lee.projectrun;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileViewerActivity extends AppCompatActivity {

    private TextView txtProfileFname, txtProfileLname, txtProfileEmail, txtProfileGender, txtProfileAge,
            txtProfileTeaching, txtProfilePractice, txtProfilePersonalInterest;
    private ImageView imgProfileUser;
    private Button btnProfileProfileAddConnection, btnProfileChat, btnProfileLastLocation;
    private String FirstName, LastName, Email, Age;
    private String Gender,PracticeLanguage, PracticeLanguageLevel;
    private String TeachingLanguage, TeachingLanguageLevel, PersonalInterest, Image ,Gps;
    private String TeachingLanguageComplete, PracticeLanguageComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_viewer);

        txtProfileFname = (TextView) findViewById(R.id.txtProfileFname);
        txtProfileLname = (TextView) findViewById(R.id.txtProfileLname);
        txtProfileEmail = (TextView) findViewById(R.id.txtProfileEmail);
        txtProfileAge = (TextView) findViewById(R.id.txtProfileAge);
        txtProfileGender = (TextView) findViewById(R.id.txtProfileGender);
        txtProfileTeaching = (TextView) findViewById(R.id.txtProfileTeaching);
        txtProfilePractice = (TextView) findViewById(R.id.txtProfilePractice);
        txtProfilePersonalInterest = (TextView) findViewById(R.id.txtProfilePersonalInterest);
        imgProfileUser = (ImageView) findViewById(R.id.imgProfileImage);
        btnProfileProfileAddConnection = (Button) findViewById(R.id.btnProfileAddConnection);
        btnProfileChat = (Button) findViewById(R.id.btnProfileChat);
        btnProfileLastLocation = (Button) findViewById(R.id.btnProfileLastLocation);

        Intent intent = getIntent();
        FirstName = intent.getExtras().getString("profileFname");
        LastName = intent.getExtras().getString("profileLname");
        Email = intent.getExtras().getString("profileEmail");
        Age = intent.getExtras().getString("profileAge");
        Gender = intent.getExtras().getString("profileGender");
        PracticeLanguage = intent.getExtras().getString("profilePracticingLanguage");
        PracticeLanguageLevel = intent.getExtras().getString("profilePracticingLanguageLevel");
        TeachingLanguage = intent.getExtras().getString("profileTeachingLanguage");
        TeachingLanguageLevel = intent.getExtras().getString("profileTeachingLanguageLevel");
        PersonalInterest = intent.getExtras().getString("profilePersonalInterest");
        Image = intent.getExtras().getString("profileImage");
        Gps = intent.getExtras().getString("profileGps");

        TeachingLanguageComplete = TeachingLanguage + ", " + TeachingLanguageLevel;
        PracticeLanguageComplete = PracticeLanguage + ", " + PracticeLanguageLevel;

        byte[] decodedString = Base64.decode(Image, 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


        txtProfileFname.setText(FirstName);
        txtProfileLname.setText(LastName);
        txtProfileEmail.setText(Email);
        txtProfileAge.setText(Age);
        txtProfileGender.setText(Gender);
        txtProfileTeaching.setText(TeachingLanguageComplete);
        txtProfilePractice.setText(PracticeLanguageComplete);
        txtProfilePersonalInterest.setText(PersonalInterest);
        imgProfileUser.setImageBitmap(decodedByte);

    }

}
