package com.example.lee.projectrun;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RetrieveUser extends AppCompatActivity
{


    public Button btnSubmit;
    public TextView ViewName, ViewEmail;
    private EditText txtID;
    String UserID;
    String Name, Email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_user);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        txtID = (EditText) findViewById(R.id.txtID);
        ViewName = (TextView) findViewById(R.id.ViewName);
        ViewEmail = (TextView) findViewById(R.id.ViewEmail);



        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                getUser();
            }
        });
    }









    private void getUser()
    {
        UserID = txtID.getText().toString();
        class GetUser extends AsyncTask<Void, Void, String>
        {

            ProgressDialog Loading;

            protected void onPreExecute()
            {
                super.onPreExecute();
                Loading = ProgressDialog.show(RetrieveUser.this, "Fetching...", "Wait...", false, false);

            }

            protected void onPostExecute(String S)
            {
                super.onPostExecute(S);
                Loading.dismiss();
                ShowUser(S);
            }

            protected String doInBackground(Void... Params)
            {
                RequestHandler RH = new RequestHandler();
                String S = RH.sendRequestParam(Config.URL_GetUser, UserID);
                return S;
            }
        }

        GetUser GU = new GetUser();
        GU.execute();
    }













    private void ShowUser(String JSON)
    {
        try
        {
            JSONObject JSONObject = new JSONObject(JSON);
            JSONArray Result = JSONObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject C = Result.getJSONObject(0);
            Name = C.getString(Config.TAG_Name);
            Email = C.getString(Config.TAG_Email);


            ViewName.setText(Name);
            ViewEmail.setText(Email);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
