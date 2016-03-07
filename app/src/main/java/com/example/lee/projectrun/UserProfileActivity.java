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
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {

    private TextView txtUserFname, txtUserLname, txtUserEmail, txtUserGender, txtUserAge,
            txtUserTeaching, txtUserPractice, txtUserPersonalInterest;
    private ImageView imgUserImage;
    private Button btnUserEditProfile;
    private String FirstName, LastName, Email, Age;
    private String Gender,PracticeLanguage, PracticeLanguageLevel;
    private String TeachingLanguage, TeachingLanguageLevel, PersonalInterest, Image;
    private String TeachingLanguageComplete, PracticeLanguageComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        txtUserFname = (TextView) findViewById(R.id.txtUserFname);
        txtUserLname = (TextView) findViewById(R.id.txtUserLname);
        txtUserEmail = (TextView) findViewById(R.id.txtUserEmail);
        txtUserGender = (TextView) findViewById(R.id.txtUserGender);
        txtUserAge = (TextView) findViewById(R.id.txtUserAge);
        txtUserTeaching = (TextView) findViewById(R.id.txtUserTeaching);
        txtUserPractice = (TextView) findViewById(R.id.txtUserPractice);
        txtUserPersonalInterest = (TextView) findViewById(R.id.txtUserPersonalInterest);
        imgUserImage = (ImageView) findViewById(R.id.imgUserImage);
        btnUserEditProfile = (Button) findViewById(R.id.btnUserEditProfile);

        Intent intent = getIntent();
        FirstName = intent.getExtras().getString("userFname");
        LastName = intent.getExtras().getString("userLname");
        Email = intent.getExtras().getString("userEmail");
        Age = intent.getExtras().getString("usereAge");
        Gender = intent.getExtras().getString("userGender");
        PracticeLanguage = intent.getExtras().getString("userPracticingLanguage");
        PracticeLanguageLevel = intent.getExtras().getString("userPracticingLanguageLevel");
        TeachingLanguage = intent.getExtras().getString("userTeachingLanguage");
        TeachingLanguageLevel = intent.getExtras().getString("userTeachingLanguageLevel");
        PersonalInterest = intent.getExtras().getString("userPersonalInterest");
        Image = intent.getExtras().getString("userImage");

        TeachingLanguageComplete = TeachingLanguage + ", " + TeachingLanguageLevel;
        PracticeLanguageComplete = PracticeLanguage + ", " + PracticeLanguageLevel;

        byte[] decodedString = Base64.decode(Image, 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        txtUserFname.setText(FirstName);
        txtUserLname.setText(LastName);
        txtUserEmail.setText(Email);
        txtUserAge.setText(Age);
        txtUserGender.setText(Gender);
        txtUserTeaching.setText(TeachingLanguageComplete);
        txtUserPractice.setText(PracticeLanguageComplete);
        txtUserPersonalInterest.setText(PersonalInterest);
        imgUserImage.setImageBitmap(decodedByte);

    }

}
