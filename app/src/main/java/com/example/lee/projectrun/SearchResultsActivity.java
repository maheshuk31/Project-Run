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
    private String JSON_STRING;
    private String Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Search = intent.getExtras().getString("stringSearch");


        search();

        linLayResultsHolder = (LinearLayout) findViewById(R.id.linLayResultsHolder);


    }

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

    private void showResult(String json) {
        try {

            JSONArray search = new JSONArray(json);
            Log.d("AAA", search.toString());
            for (int i = 0; i < search.length(); i++) {
                JSONObject jo = search.getJSONObject(i);
                addingLayout(jo.getString("FirstName"), jo.getString("Image"), jo.getString("PersonalInterests"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addingLayout(String Name, String Image, String Personal) {


        txtSearchResultName = new TextView(this);
        txtSearchResultName.setText(Name);
        LinearLayout.LayoutParams lpTxtName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        txtSearchResultName.setTextAlignment(txtSearchResultName.TEXT_ALIGNMENT_CENTER);
        txtSearchResultName.setTextColor(Color.BLACK);
        txtSearchResultName.setLayoutParams(lpTxtName);

        txtSearchResultPersonalInfo = new TextView(this);
        txtSearchResultPersonalInfo.setText(Personal);
        LinearLayout.LayoutParams lpPersonalInfo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        txtSearchResultPersonalInfo.setPadding(10, 0, 0, 0);
        txtSearchResultPersonalInfo.setTextColor(Color.BLACK);
        txtSearchResultPersonalInfo.setLayoutParams(lpPersonalInfo);

        imgProfilePic = new ImageView(this);
        LinearLayout.LayoutParams lpImageHolder = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        lpImageHolder.weight = 0.7f;
        txtSearchResultPersonalInfo.setLayoutParams(lpImageHolder);
        byte[] decodedString = Base64.decode(Image, 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgProfilePic.setImageBitmap(decodedByte);



        linLaySecondSearchResultsPerPerson = new LinearLayout(this);
        linLaySecondSearchResultsPerPerson.setOrientation(LinearLayout.HORIZONTAL);
        linLaySecondSearchResultsPerPerson.setWeightSum(1f);
       // linLaySecondSearchResultsPerPerson.setBackground(getDrawable(R.drawable.customborder));
        LinearLayout.LayoutParams linParamSecond = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linParamSecond.setMargins(0, 0, 0, 20);


        linLaySecondSearchResultsPerPerson.setLayoutParams(linParamSecond);

        linLayThirdSearchResultsNameImageHolder = new LinearLayout(this);
        linLayThirdSearchResultsNameImageHolder.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linParamThird = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linParamThird.weight = 0.3f;

        linLayThirdSearchResultsNameImageHolder.setLayoutParams(linParamThird);

        linLayResultsHolder.addView(linLaySecondSearchResultsPerPerson);
        linLaySecondSearchResultsPerPerson.addView(linLayThirdSearchResultsNameImageHolder);
        linLaySecondSearchResultsPerPerson.addView(txtSearchResultPersonalInfo);
        linLayThirdSearchResultsNameImageHolder.addView(imgProfilePic);
        linLayThirdSearchResultsNameImageHolder.addView(txtSearchResultName);

    }

}

