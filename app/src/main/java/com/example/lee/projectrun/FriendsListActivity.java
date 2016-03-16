package com.example.lee.projectrun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Used to create the Friends Layout for when a user will look at their contacts list of the app
 */
public class FriendsListActivity extends AppCompatActivity {

    private LinearLayout linLayFriendsListHolder, linLaySecondFriendsPerPerson;
    private ImageView imgFriends;
    private TextView txtFriendsName;
    private UserInformation userInformation;
    public ArrayList<String> jsonArray;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        counter = 0;
        setContentView(R.layout.activity_friends_list);

        Intent intent = getIntent();
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");

        linLayFriendsListHolder = (LinearLayout) findViewById(R.id.linLayFriendsListHolder);
        searchFriends();
    }

    /**
     * Goes through each of the users friends list and displays them
     */
    private void searchFriends(){
        String[] string = userInformation.getFriends();
        for(int x = 0; x<userInformation.getFriends().length;x++){
            search(string[x], counter);
            counter = counter+1;
            x++;
        }
    }

    /**
     * Goes through each of the friends associated and gets their data
     * @param FriendsID POST variable for the php
     * @param index to place the json into an arraylist
     */
    private void search(final String FriendsID, final int index) {

        class GetUsers extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            //StoringIndex was placed to make sure the int was being stored
            int StoringIndex = index;

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.URL_FindFriends, FriendsID);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_Search, params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                findFriends(s, StoringIndex);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FriendsListActivity.this, "Fetching...", "Wait...", false, false);
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    /**
     * Adds Friends given the JSON Object
     * @param json array for the for loop to go through
     * @param indicator an array indicator
     */
    private void findFriends(String json, int indicator) {
        try {
            jsonArray.add(indicator, json);
            JSONArray search = new JSONArray(json);
            Log.d("AAA", search.toString());
            for (int i = 0; i < search.length(); i++) {
                final JSONObject jo = search.getJSONObject(i);
                addingFriendsLayout(jo, jo.getString("UniqueCode"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Programmatically creating the layout that will be used to show each of the user's friend's
     * information on a Linear Layout
     * @param jo JSON object
     * @param UniqueCode used to try and match the user's friend with that on the database
     * @throws JSONException
     */
    private void addingFriendsLayout(JSONObject jo, final String UniqueCode) throws JSONException {

        String name = jo.getString("FirstName") + " " + jo.getString("LastName");
        String image = jo.getString("Image");

        //Creating an TextView of the friends name
        txtFriendsName = new TextView(this);
        txtFriendsName.setText(name);
        txtFriendsName.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams lptxtName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lptxtName.weight = 0.7f;
        lptxtName.gravity = Gravity.CENTER_VERTICAL;
        txtFriendsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    for (int x = 0; x < jsonArray.size(); x++) {
                        JSONArray profileSearch = new JSONArray(jsonArray.get(x));
                        for (int i = 0; i < profileSearch.length(); i++) {
                            JSONObject jo = profileSearch.getJSONObject(i);
                            if (jo.getString("UniqueCode").equals(UniqueCode)) {
                                Log.d("FirstName", jo.getString("FirstName"));
                                Intent intent = new Intent(getApplicationContext(), ProfileViewerActivity.class);
                                intent.putExtra("profileFname", jo.getString("FirstName"));
                                intent.putExtra("profileLname", jo.getString("LastName"));
                                intent.putExtra("profileEmail", jo.getString("Email"));
                                intent.putExtra("profileAge", jo.getString("Age"));
                                intent.putExtra("profileGender", jo.getString("Gender"));
                                intent.putExtra("profilePracticingLanguage", jo.getString("PracticeLanguage"));
                                intent.putExtra("profileTeachingLanguage", jo.getString("TeachingLanguage"));
                                intent.putExtra("profilePersonalInterest", jo.getString("PersonalInterests"));
                                intent.putExtra("profileImage", jo.getString("Image"));
                                // intent.putExtra("profileGps", jo.getString("GPS"));
                                startActivity(intent);
                            }
                        }
                    }
                }catch(JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        txtFriendsName.setLayoutParams(lptxtName);

        //Creates a ImageView of the friend#s users image
        imgFriends = new ImageView(this);
        LinearLayout.LayoutParams lpImageHolder = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpImageHolder.weight = 0.3f;
        byte[] decodedString = Base64.decode(image, 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgFriends.setImageBitmap(decodedByte);
        imgFriends.setLayoutParams(lpImageHolder);

        //Creates a second Linear Layout that will hold the image view
        linLaySecondFriendsPerPerson = new LinearLayout(this);
        linLaySecondFriendsPerPerson.setOrientation(LinearLayout.HORIZONTAL);
        linLaySecondFriendsPerPerson.setWeightSum(1f);
        // linLaySecondSearchResultsPerPerson.setBackground(getDrawable(R.drawable.customborder));
        LinearLayout.LayoutParams linParamSecond = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linLaySecondFriendsPerPerson.setLayoutParams(linParamSecond);

        //Instantiate the layout
        linLayFriendsListHolder.addView(linLaySecondFriendsPerPerson);
        linLaySecondFriendsPerPerson.addView(imgFriends);
        linLaySecondFriendsPerPerson.addView(txtFriendsName);
    }

}
