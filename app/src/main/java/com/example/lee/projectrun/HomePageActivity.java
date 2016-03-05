package com.example.lee.projectrun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {

    private Button btnSearch, btnAdvanced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnAdvanced = (Button) findViewById(R.id.btnAdvanced);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchResultsActivity.class);
                startActivity(intent);
            }
        });


    }

//  public String getSearchVal(){
//  return
//  }

}
