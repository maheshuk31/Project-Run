package com.voyd.kclexchange;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private UserInformation userInformation;
    private EditText txtSearch;
    private String stringSearch;
    private LinearLayout linLayFriendsListHolder;
    public ArrayList<String> jsonArray;
    private ImageView imgFriends;
    private TextView txtFriendsName;

    @Override
    public void onRestart() {
        super.onRestart();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("userinfo", userInformation);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");
        txtSearch = (EditText) findViewById(R.id.txtSearch);

        if(DataStore.getCurrentUser()!=null)
            userInformation = DataStore.getCurrentUser();

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView barTitle = (TextView) findViewById(R.id.bar_title);
        barTitle.setText("KCLexchange");

        jsonArray = new ArrayList<>();
        linLayFriendsListHolder = (LinearLayout) findViewById(R.id.linLayFriends);
        searchFriends();

    }
    /**
     * Goes through each of the users friends list and displays them
     */
    private void searchFriends(){
        if(userInformation.getFriends()[0]!="") {
            String[] string = userInformation.getFriends();
            for (int x = 0; x < userInformation.getFriends().length; x++) {
                search(string[x], x);
            }
        } else {
            TextView txtEmpty = new TextView(this);
            txtEmpty.setText("No quick-contacts added");
            txtEmpty.setGravity(Gravity.CENTER_HORIZONTAL);
            linLayFriendsListHolder.addView(txtEmpty);
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
                params.put("FriendCode", FriendsID);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_FindFriends, params);
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
                loading = ProgressDialog.show(HomeActivity.this, "Loading", "Wait for it...", false, false);
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
            Log.d("json", json);
            jsonArray.add(indicator, json);
            JSONArray search = new JSONArray(json);
            Log.d("AAA", search.toString());
            for (int i = 0; i < search.length(); i++) {
                JSONObject jo = search.getJSONObject(i);
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
        String name = jo.getString("FirstName");
        String image = jo.getString("Image");
        //creating the layout holder
        LinearLayout linLayTemp = new LinearLayout(this);
        //Creating an TextView of the friends name
        txtFriendsName = new TextView(this);
        txtFriendsName.setText(name);
        txtFriendsName.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams lptxtName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lptxtName.setMargins(15,0,0,0);
        lptxtName.gravity = Gravity.CENTER_VERTICAL;
        linLayTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int x = 0; x < jsonArray.size(); x++) {
                        JSONArray profileSearch = new JSONArray(jsonArray.get(x));
                        for (int i = 0; i < profileSearch.length(); i++) {
                            JSONObject jo = profileSearch.getJSONObject(i);
                            if (jo.getString("UniqueCode").equals(UniqueCode)) {
                                Intent intent = new Intent(getApplicationContext(), ProfileOtherActivity.class);
                                intent.putExtra("profileUnique", jo.getString("UniqueCode"));
                                intent.putExtra("userinfo", userInformation);
                                intent.putExtra("profileFname", jo.getString("FirstName"));
                                intent.putExtra("profileLname", jo.getString("LastName"));
                                intent.putExtra("profileEmail", jo.getString("Email"));
                                intent.putExtra("profileAge", jo.getString("Age"));
                                intent.putExtra("profileGender", jo.getString("Gender"));
                                intent.putExtra("profilePracticingLanguage", jo.getString("PracticeLanguage"));
                                intent.putExtra("profileTeachingLanguage", jo.getString("TeachingLanguage"));
                                intent.putExtra("profilePersonalInterest", jo.getString("PersonalInterests"));
                                intent.putExtra("profileImage", jo.getString("Image"));
                                intent.putExtra("profileGps", jo.getString("GPS"));
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
        //Creates a ImageView of the friend's users image
        imgFriends = new ImageView(this);
        LinearLayout.LayoutParams lpImageHolder = new LinearLayout.LayoutParams(130, 130);
        lpImageHolder.setMargins(0,4,0,4);
        byte[] decodedString = Base64.decode(image, 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgFriends.setImageBitmap(decodedByte);
        imgFriends.setLayoutParams(lpImageHolder);
        //Formats the second Linear Layout that will hold the image and text
        linLayTemp.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linParamTemp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linParamTemp.setMargins(0, 0, 6, 0);
        linLayTemp.setLayoutParams(linParamTemp);
        //Instantiate the layout
        linLayFriendsListHolder.addView(linLayTemp);
        linLayTemp.addView(imgFriends);
        linLayTemp.addView(txtFriendsName);
    }

    //----------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Welcome to KCLexchange! Start off by typing a language or a " +
                    "friend's name into the search box - or use the options below to view your " +
                    "profile, see nearby speakers available for exchange, and explore language" +
                    "resources to help you on your way. Any notifications like upcoming meetings" +
                    "or chat messages will be displayed on this page, as well as anybody you add" +
                    "as a quick-contact.").setTitle("Help");

            builder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {}});

            AlertDialog dialog = builder.create();
            dialog.show();
            TextView textDialog = (TextView)dialog.findViewById(android.R.id.message);
            textDialog.setGravity(Gravity.CENTER);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    //----------------------------------------------------------------

    public void onClickProfile(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfileOwnActivity.class);
        intent.putExtra("userinfo", userInformation);
        startActivity(intent);
    }

    public void onClickResources(View view) {
        Intent i = new Intent(this, ResourcesActivity.class);
        startActivity(i);
    }

    public void onClickMap(View view) {
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        intent.putExtra("userinfo", userInformation);
        startActivity(intent);
    }

    public void onClickSearch(View view) {
        stringSearch = txtSearch.getText().toString();
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        intent.putExtra("stringSearch", stringSearch);
        intent.putExtra("userinfo", userInformation);
        startActivity(intent);

        TextView txtMeeting = new TextView(this);
        txtMeeting.setText("beef");
        txtMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            this.finishAffinity();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
