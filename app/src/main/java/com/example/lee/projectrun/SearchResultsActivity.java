package com.example.lee.projectrun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

        linLayResultsHolder = (LinearLayout)findViewById(R.id.linLayResultsHolder);


        addingLayout(Search, "DSassad", "Personal Interests");

    }

    private void search(){
        class GetUsers extends AsyncTask<Void, Void, String>{
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

            JSONObject obj = new JSONObject(json);
            JSONArray search = obj.getJSONArray("friends");
            Log.d("AAA", search.toString());
            for (int i = 0; i < search.length(); i++) {
                JSONObject chatRoomsObj = (JSONObject) search.get(i);
                addingLayout(chatRoomsObj.getString(Config.Key_Name), chatRoomsObj.getString(Config.Key_Name), chatRoomsObj.getString(Config.Key_Name));
            }

            //   JSONObject jsonObject = new JSONObject(json);
            //   JSONObject mObj = jsonObject.getJSONObject("friends");
            //   JSONObject c = mObj.getJSONObject(0);
            //   String name = c.getString(Config.TAG_Name);
            //   String personal = c.getString(Config.TAG_PersonalInterests);
            //   String image = c.getString(Config.TAG_Image);
            //   System.out.println(name);

            //  addingLayout(name, personal, image);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addingLayout(String Name, String Image, String Personal){


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

        TextView txtIMAGEHOLDER = new TextView(this);
        txtIMAGEHOLDER.setText("Picture");
        LinearLayout.LayoutParams lpImageHolder = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        lpImageHolder.weight = 0.7f;
        txtSearchResultPersonalInfo.setLayoutParams(lpImageHolder);


        linLaySecondSearchResultsPerPerson = new LinearLayout(this);
        linLaySecondSearchResultsPerPerson.setOrientation(LinearLayout.HORIZONTAL);
        linLaySecondSearchResultsPerPerson.setWeightSum(1f);
        LinearLayout.LayoutParams linParamSecond = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


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
        linLayThirdSearchResultsNameImageHolder.addView(txtIMAGEHOLDER);
        linLayThirdSearchResultsNameImageHolder.addView(txtSearchResultName);

    }

}
