package com.example.lee.projectrun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lee.projectrun.Actions.ListFriend;

public class HomePageActivity extends AppCompatActivity {

    private Button btnSearch, btnUserProfile, btnGps, btnContacts, btnChat;
    private EditText txtSearchVal;
    private String stringSearch;
    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");

        //userInformation.updateStudent(HomePageActivity.this);

        btnChat = (Button) findViewById(R.id.Chat);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnUserProfile = (Button) findViewById(R.id.btnUserProfile);
        btnGps = (Button) findViewById(R.id.btnGps);
        btnContacts = (Button) findViewById(R.id.btnContacts);
        txtSearchVal = (EditText) findViewById(R.id.txtSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringSearch = txtSearchVal.getText().toString();
                Intent intent = new Intent(getApplicationContext(), SearchResultsActivity.class);
                intent.putExtra("stringSearch", stringSearch);
                intent.putExtra("userinfo", userInformation);
                startActivity(intent);
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListFriend.class);
                intent.putExtra("userinfo", userInformation);
                startActivity(intent);
            }
        });

        btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GpsMapFragment.class);
                intent.putExtra("userinfo", userInformation);
                startActivity(intent);
            }
        });

        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent.putExtra("userinfo", userInformation);
                startActivity(intent);

            }
        });

        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FriendsListActivity.class);
                intent.putExtra("userinfo", userInformation);
                startActivity(intent);
            }
        });




    }



}
