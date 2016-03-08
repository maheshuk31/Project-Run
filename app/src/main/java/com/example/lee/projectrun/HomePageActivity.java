package com.example.lee.projectrun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomePageActivity extends AppCompatActivity {

    private Button btnSearch, btnUserProfile;
    private EditText txtSearchVal;
    private String stringSearch;
    private String stringUserFirstName, stringUserLastName, stringUserEmail, stringUserAge;
    private String stringUserGender,stringUserPracticeLanguage, stringUserPracticeLanguageLevel;
    private String stringUserTeachingLanguage, stringUserTeachingLanguageLevel, stringUserPersonalInterest;
    private String stringUserImage;
    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnUserProfile = (Button) findViewById(R.id.btnUserProfile);
        txtSearchVal = (EditText) findViewById(R.id.txtSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringSearch = txtSearchVal.getText().toString();
                Intent intent = new Intent(getApplicationContext(), SearchResultsActivity.class);
                intent.putExtra("stringSearch", stringSearch);
                startActivity(intent);
            }
        });

        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent.putExtra("userinfo", userInformation);
                startActivity(intent);



//                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
//                intent.putExtra("userFname", stringUserFirstName);
//                intent.putExtra("userLname", stringUserLastName);
//                intent.putExtra("userEmail", stringUserEmail);
//                intent.putExtra("userAge", stringUserAge);
//                intent.putExtra("userGender", stringUserGender);
//                intent.putExtra("userPracticingLanguage", stringUserPracticeLanguage);
//                intent.putExtra("userPracticingLanguageLevel", stringUserPracticeLanguageLevel);
//                intent.putExtra("userTeachingLanguage", stringUserTeachingLanguage);
//                intent.putExtra("userTeachingLanguageLevel", stringUserTeachingLanguageLevel);
//                intent.putExtra("userPersonalInterest", stringUserPersonalInterest);
//                intent.putExtra("userImage", stringUserImage);
//                startActivity(intent);
            }
        });


    }

}
