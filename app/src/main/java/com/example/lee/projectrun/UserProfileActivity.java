package com.example.lee.projectrun;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class UserProfileActivity extends AppCompatActivity {

    private TextView txtUserFname, txtUserLname, txtUserEmail, txtUserGender, txtUserAge,
            txtUserTeaching, txtUserPractice, txtUserPersonalInterest;
    private ImageView imgUserImage;
    private Button btnUserEditProfile;
    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");

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
        btnUserEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), EditUserProfileActivity.class);
                intent.putExtra("userinfo", userInformation);
                startActivity(intent);

            }
        });

        byte[] decodedString = Base64.decode(userInformation.getImageString(), 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        txtUserFname.setText(userInformation.getFirstName());
        txtUserLname.setText(userInformation.getLastName());
        txtUserEmail.setText(userInformation.getEmail());
        txtUserAge.setText(userInformation.getAge());
        txtUserGender.setText(userInformation.getGender());
        txtUserTeaching.setText(Arrays.toString(userInformation.getTeachingLanguage()).replaceAll("\\[|\\]", ""));
        txtUserPractice.setText(Arrays.toString(userInformation.getPracticeLanguage()).replaceAll("\\[|\\]", ""));
        txtUserPersonalInterest.setText(userInformation.getPersonalInterests());
        imgUserImage.setImageBitmap(decodedByte);

    }

}
