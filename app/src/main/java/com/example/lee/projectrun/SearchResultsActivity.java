package com.example.lee.projectrun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SearchResultsActivity extends AppCompatActivity {

    private LinearLayout linLayResultsHolder;
    private LinearLayout linLaySecondSearchResultsPerPerson;
    private LinearLayout linLayThirdSearchResultsNameImageHolder;
    private TextView txtSearchResultName, txtSearchResultPersonalInfo;
    private ImageView imgProfilePic;
    private String Search;
    private String json1;
    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //expands the intent
        Intent intent = getIntent();
        Search = intent.getExtras().getString("stringSearch");
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");

        //method to add data to the activity
        search();

        linLayResultsHolder = (LinearLayout) findViewById(R.id.linLayResultsHolder);


    }

    /**
     * Method that initiates the retrieval of data from the server.
     */
    private void search() {
        class GetUsers extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.Key_Search, Search);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_Search, params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;
            }

            /**
             * After the response is given from the database
             * @param s json String
             */
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showResult(s);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SearchResultsActivity.this, "Fetching...", "Wait...", false, false);
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    /**
     * Method to generate the objects for the search result
     * @param json String that contains the json array
     */
    private void showResult(String json) {
        try {
            json1 = json;
            JSONArray search = new JSONArray(json);
            Log.d("AAA", search.toString());
            for (int i = 0; i < search.length(); i++) {
                JSONObject jo = search.getJSONObject(i);
                addingLayout(jo.getString("UniqueCode"),jo.getString("FirstName"), jo.getString("Image"), jo.getString("PersonalInterests"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a programmatic created layout for each object
     * @param Unique - Object's Unique code
     * @param Name - Object's Name
     * @param Image - Object's Image
     * @param Personal - Object's Personal
     */
    private void addingLayout(final String Unique, String Name, final String Image, String Personal) {

        //Creates a TextView for the name of the person
        txtSearchResultName = new TextView(this);
        txtSearchResultName.setText(Name);
        LinearLayout.LayoutParams lpTxtName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        txtSearchResultName.setTextAlignment(txtSearchResultName.TEXT_ALIGNMENT_CENTER);
        txtSearchResultName.setTextColor(Color.BLACK);
        //Onclick listener to go to their profile
        txtSearchResultName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONArray profileSearch = new JSONArray(json1);
                    for (int i = 0; i < profileSearch.length(); i++) {
                        JSONObject jo = profileSearch.getJSONObject(i);
                        if (jo.getString("UniqueCode").equals(Unique)) {
                            Intent intent = new Intent(getApplicationContext(), ProfileViewerActivity.class);
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            });
        txtSearchResultName.setLayoutParams(lpTxtName);

        //Creates a Text for the Personal information of that user
        txtSearchResultPersonalInfo = new TextView(this);
        txtSearchResultPersonalInfo.setText(Personal);
        LinearLayout.LayoutParams lpPersonalInfo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        txtSearchResultPersonalInfo.setPadding(10, 0, 0, 0);
        txtSearchResultPersonalInfo.setTextColor(Color.BLACK);
        txtSearchResultPersonalInfo.setLayoutParams(lpPersonalInfo);

        //Creates a ImageView for the that users image
        imgProfilePic = new ImageView(this);
        LinearLayout.LayoutParams lpImageHolder = new LinearLayout.LayoutParams(400, 500);
        lpImageHolder.weight = 0.7f;
        imgProfilePic.setLayoutParams(lpImageHolder);
        byte[] decodedString = Base64.decode(Image, 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgProfilePic.setImageBitmap(decodedByte);


        //Creates LinearLayout that holds, a third layout and personal information
        linLaySecondSearchResultsPerPerson = new LinearLayout(this);
        linLaySecondSearchResultsPerPerson.setOrientation(LinearLayout.HORIZONTAL);
        linLaySecondSearchResultsPerPerson.setWeightSum(1f);
        linLaySecondSearchResultsPerPerson.setBackground(getDrawable(R.drawable.customborder));
        LinearLayout.LayoutParams linParamSecond = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linParamSecond.setMargins(0, 0, 0, 20);
        linLaySecondSearchResultsPerPerson.setLayoutParams(linParamSecond);

        //Creates a LinearLayout to hold the name and the image
        linLayThirdSearchResultsNameImageHolder = new LinearLayout(this);
        linLayThirdSearchResultsNameImageHolder.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linParamThird = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linParamThird.weight = 0.3f;
        linLayThirdSearchResultsNameImageHolder.setLayoutParams(linParamThird);

        //Instantiate the layout
        linLayResultsHolder.addView(linLaySecondSearchResultsPerPerson);
        linLaySecondSearchResultsPerPerson.addView(linLayThirdSearchResultsNameImageHolder);
        linLaySecondSearchResultsPerPerson.addView(txtSearchResultPersonalInfo);
        linLayThirdSearchResultsNameImageHolder.addView(imgProfilePic);
        linLayThirdSearchResultsNameImageHolder.addView(txtSearchResultName);

    }

}

