package com.example.lee.projectrun;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FriendsListActivity extends AppCompatActivity {

    private LinearLayout linLayFriendsListHolder, linLaySecondFriendsPerPerson;
    private ImageView imgFriends;
    private TextView txtFriendsName;
    private UserInformation userInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        Intent intent = getIntent();
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");


        linLayFriendsListHolder = (LinearLayout) findViewById(R.id.linLayFriendsListHolder);

        addingFriendsLayout(userInformation.getImageString(), userInformation.getFirstName());
    }

    private void addingFriendsLayout(String image, String name){

        txtFriendsName = new TextView(this);
        txtFriendsName.setText(name);
        txtFriendsName.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams lptxtName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lptxtName.weight = 0.7f;
        lptxtName.gravity = Gravity.CENTER_VERTICAL;
        txtFriendsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileViewerActivity.class);
                startActivity(intent);
            }
        });
        txtFriendsName.setLayoutParams(lptxtName);

        imgFriends = new ImageView(this);
        LinearLayout.LayoutParams lpImageHolder = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpImageHolder.weight = 0.3f;
        byte[] decodedString = Base64.decode(image, 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgFriends.setImageBitmap(decodedByte);
        imgFriends.setLayoutParams(lpImageHolder);


        linLaySecondFriendsPerPerson = new LinearLayout(this);
        linLaySecondFriendsPerPerson.setOrientation(LinearLayout.HORIZONTAL);
        linLaySecondFriendsPerPerson.setWeightSum(1f);
        // linLaySecondSearchResultsPerPerson.setBackground(getDrawable(R.drawable.customborder));
        LinearLayout.LayoutParams linParamSecond = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linLaySecondFriendsPerPerson.setLayoutParams(linParamSecond);

        linLayFriendsListHolder.addView(linLaySecondFriendsPerPerson);
        linLaySecondFriendsPerPerson.addView(imgFriends);
        linLaySecondFriendsPerPerson.addView(txtFriendsName);

    }

}
