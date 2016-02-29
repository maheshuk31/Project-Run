package com.example.lee.projectrun;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchResultsActivity extends AppCompatActivity {

    private LinearLayout linLayResultsHolder;
    private LinearLayout linLaySecondSearchResultsPerPerson;
    private LinearLayout linLayThirdSearchResultsNameImageHolder;
    private TextView txtSearchResultName, txtSearchResultPersonalInfo;
    private ImageView imgProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linLayResultsHolder = (LinearLayout)findViewById(R.id.linLayResultsHolder);

        addingLayout();
    }

    private void addingLayout(){

        txtSearchResultName = new TextView(this);
        txtSearchResultName.setText("John Doe");
        LinearLayout.LayoutParams lpTxtName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        txtSearchResultName.setTextAlignment(txtSearchResultName.TEXT_ALIGNMENT_CENTER);
        txtSearchResultName.setTextColor(Color.BLACK);
        txtSearchResultName.setLayoutParams(lpTxtName);

        txtSearchResultPersonalInfo = new TextView(this);
        txtSearchResultPersonalInfo.setText("LONG TEXT PLS LONG TEXT PLS LONG TEXT PLS LONG TEXT PLS LONG TEXT PLS LONG TEXT PLS LONG TEXT PLS LONG TEXT PLS LONG TEXT PLS LONG TEXT PLS LONG TEXT PLS");
        LinearLayout.LayoutParams lpPersonalInfo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        txtSearchResultPersonalInfo.setPadding(10, 0, 0, 0);
        txtSearchResultPersonalInfo.setTextColor(Color.BLACK);
        txtSearchResultPersonalInfo.setLayoutParams(lpPersonalInfo);

        TextView txtIMAGEHOLDER = new TextView(this);
        txtIMAGEHOLDER.setText("Picture");
        LinearLayout.LayoutParams lpImageHolder = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
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
