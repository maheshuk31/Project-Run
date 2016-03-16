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

public class FriendsListActivity extends AppCompatActivity {

    private LinearLayout linLayFriendsListHolder, linLaySecondFriendsPerPerson;
    private ImageView imgFriends;
    private TextView txtFriendsName;
    private UserInformation userInformation;
    public ArrayList<String> jsonArray;
    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i = 0;
        setContentView(R.layout.activity_friends_list);

        Intent intent = getIntent();
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");


        linLayFriendsListHolder = (LinearLayout) findViewById(R.id.linLayFriendsListHolder);
        searchLanguage();
    }

    /**
     * Goes through each of the users practicing language and finds people associated with them
     */
    private void searchLanguage(){
        String[] string = userInformation.getFriends();
        for(int x = 0; x<userInformation.getFriends().length;x++){
            search(string[x], i);
            i = i+1;
            x++;
        }
    }

    /**
     * Method that goes through each practicing language and gets that users data
     * @param index index to place the json into an arraylist
     */
    private void search(final String FriendsID, final int index) {

        class GetUsers extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            //yindex was placed to make sure the int was being stored
            int yindex = index;

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
                findFriends(s, yindex);
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
     * Add's Markers through the given json object
     * @param json = json array for the for loop to go through
     * @param y = array indicator
     */
    private void findFriends(String json, int y) {
        try {
            jsonArray.add(y, json);
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



    private void addingFriendsLayout(JSONObject jo, final String UniqueCode) throws JSONException {

        String name = jo.getString("FirstName") + " " + jo.getString("LastName");
        String image = jo.getString("Image");
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
