package com.example.lee.projectrun;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lee.projectrun.ui.SampleActivity;

public class HomePageActivity extends AppCompatActivity {

    private Button btnSearch, btnUserProfile, btnGps, btnContacts, btnVideoChat;
    private EditText txtSearchVal;
    private String stringSearch;
    private UserInformation userInformation;
    private LinearLayout linNotificationHolder, linMeetingHolder;
    private TextView txtMeeting;
    private Button btnYes, btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        linNotificationHolder = (LinearLayout) findViewById(R.id.linNotificationHolder);

        Intent intent = getIntent();
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");

        //userInformation.updateStudent(HomePageActivity.this);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnUserProfile = (Button) findViewById(R.id.btnUserProfile);
        btnGps = (Button) findViewById(R.id.btnGps);
        btnContacts = (Button) findViewById(R.id.btnContacts);
        btnVideoChat = (Button) findViewById(R.id.btnVideoChat);
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


        btnVideoChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SampleActivity.class);
                intent.putExtra("userinfo", userInformation);
                startActivity(intent);
            }
        });

    }


    private void addingNotificationLayout(String meeting){

        linMeetingHolder = new LinearLayout(this);
        linMeetingHolder.setOrientation(LinearLayout.HORIZONTAL);
        linMeetingHolder.setWeightSum(1f);
        LinearLayout.LayoutParams linParamMeetingHolder = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linMeetingHolder.setLayoutParams(linParamMeetingHolder);


        txtMeeting = new TextView(this);
        txtMeeting.setText(meeting);
        LinearLayout.LayoutParams lpTxtMeeting = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpTxtMeeting.weight = 0.6f;
        txtMeeting.setTextColor(Color.BLACK);
        txtMeeting.setLayoutParams(lpTxtMeeting);

        btnYes = new Button(this);
        btnYes.setText("YES");
        LinearLayout.LayoutParams lpBtnYes = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpBtnYes.weight = 0.2f;
        btnYes.setLayoutParams(lpBtnYes);

        btnNo = new Button(this);
        btnNo.setText("NO");
        LinearLayout.LayoutParams lpBtnNo = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpBtnNo.weight = 0.2f;
        btnNo.setLayoutParams(lpBtnYes);

        linNotificationHolder.addView(linMeetingHolder);
        linMeetingHolder.addView(txtMeeting);
        linMeetingHolder.addView(btnYes);
        linMeetingHolder.addView(btnNo);


    }


}
