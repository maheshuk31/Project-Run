package com.example.lee.projectrun;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileViewerActivity extends AppCompatActivity {

    private TextView txtProfileFname, txtProfileLname, txtProfileEmail, txtProfileGender, txtProfileAge,
            txtProfileTeaching, txtProfilePractice, txtProfilePersonalInterest;
    private ImageView imgProfileUser;
    private Button btnProfileAddConnection,  btnProfileChat, btnProfileLastLocation;
    private String FirstName, LastName, Email, Age, UniqueCode, Friends;
    private String Gender,PracticeLanguage, PracticeLanguageLevel;
    private String TeachingLanguage, TeachingLanguageLevel, PersonalInterest, Image ,Gps;
    private String TeachingLanguageComplete, PracticeLanguageComplete;
    private UserInformation userInformation;

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
        btnProfileAddConnection = (Button) findViewById(R.id.btnProfileAddConnection);
        btnProfileChat = (Button) findViewById(R.id.btnProfileChat);
        btnProfileLastLocation = (Button) findViewById(R.id.btnProfileLastLocation);


        Intent intent = getIntent();
        userInformation = (UserInformation) intent.getSerializableExtra("userinfo");
        FirstName = intent.getExtras().getString("profileFname");
        LastName = intent.getExtras().getString("profileLname");
        Email = intent.getExtras().getString("profileEmail");
        Age = intent.getExtras().getString("profileAge");
        Gender = intent.getExtras().getString("profileGender");
        PracticeLanguage = intent.getExtras().getString("profilePracticingLanguage");
        TeachingLanguage = intent.getExtras().getString("profileTeachingLanguage");
        PersonalInterest = intent.getExtras().getString("profilePersonalInterest");
        Image = intent.getExtras().getString("profileImage");
        Gps = intent.getExtras().getString("profileGps");
        UniqueCode = intent.getExtras().getString("profileUnique");


        byte[] decodedString = Base64.decode(Image, 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


        txtProfileFname.setText(FirstName);
        txtProfileLname.setText(LastName);
        txtProfileEmail.setText(Email);
        txtProfileAge.setText(Age);
        txtProfileGender.setText(Gender);
        txtProfileTeaching.setText(TeachingLanguage);
        txtProfilePractice.setText(PracticeLanguage);
        txtProfilePersonalInterest.setText(PersonalInterest);
        imgProfileUser.setImageBitmap(decodedByte);

        if (userInformation.searchFriendsList(UniqueCode)== true) {
            btnProfileAddConnection.setText("Remove Contact");
            btnProfileAddConnection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                userInformation.modifyFriends(UniqueCode);
                    userInformation.updateStudent(ProfileViewerActivity.this);
                    Intent intent = new Intent(getApplicationContext(), ProfileViewerActivity.class);
                    Log.d("Friends", userInformation.Friends);
                    intent.putExtra("userinfo", userInformation);
                    intent.putExtra("profileUnique", UniqueCode);
                    intent.putExtra("profileFname", FirstName);
                    intent.putExtra("profileLname", LastName);
                    intent.putExtra("profileEmail", Email);
                    intent.putExtra("profileAge", Age);
                    intent.putExtra("profileGender", Gender);
                    intent.putExtra("profilePracticingLanguage", PracticeLanguage);
                    intent.putExtra("profileTeachingLanguage", TeachingLanguage);
                    intent.putExtra("profilePersonalInterest", PersonalInterest);
                    intent.putExtra("profileImage", Image);
                    intent.putExtra("profileGps", Gps);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            btnProfileAddConnection.setText("Add Contact");
            btnProfileAddConnection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userInformation.addFriends(UniqueCode);
                    userInformation.updateStudent(ProfileViewerActivity.this);
                    Intent intent = new Intent(getApplicationContext(), ProfileViewerActivity.class);
                    Log.d("Friends", userInformation.Friends);
                    intent.putExtra("userinfo", userInformation);
                    intent.putExtra("profileUnique", UniqueCode);
                    intent.putExtra("profileFname", FirstName);
                    intent.putExtra("profileLname", LastName);
                    intent.putExtra("profileEmail", Email);
                    intent.putExtra("profileAge", Age);
                    intent.putExtra("profileGender", Gender);
                    intent.putExtra("profilePracticingLanguage", PracticeLanguage);
                    intent.putExtra("profileTeachingLanguage", TeachingLanguage);
                    intent.putExtra("profilePersonalInterest", PersonalInterest);
                    intent.putExtra("profileImage", Image);
                    intent.putExtra("profileGps", Gps);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }

    //public ProfileViewerActivity(FirstName, LastName, Email, Age, Gender,PracticeLanguage,
       //                          PracticeLanguageLevel, TeachingLanguage, TeachingLanguageLevel, PersonalInterest, Image ,Gps){

   // }

}
