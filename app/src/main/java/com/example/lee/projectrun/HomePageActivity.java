package com.example.lee.projectrun;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomePageActivity extends AppCompatActivity {

    private Button btnSearch, btnUserProfile;
    private EditText txtSearchVal;
    private String stringSearch;
    private String stringIp;
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

            }
        });


    }

    public void getWifi(){

        WifiManager myWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);

        WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
        int myIp = myWifiInfo.getIpAddress();
        int intMyIp3 = myIp/0x1000000;
        int intMyIp3mod = myIp%0x1000000;
        int intMyIp2 = intMyIp3mod/0x10000;
        int intMyIp2mod = intMyIp3mod%0x10000;
        int intMyIp1 = intMyIp2mod/0x100;
        int intMyIp0 = intMyIp2mod%0x100;
        stringIp = intMyIp0 + "." + intMyIp1 + "." + intMyIp2 + "." + intMyIp3;

    }

}
