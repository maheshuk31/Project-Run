package com.example.lee.projectrun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewAllUsers extends AppCompatActivity
{


    private ListView ListView;
    private String JSON_String;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_users);


        ListView = (ListView) findViewById(R.id.ListView);

        getJSON();

    }











    private void ShowUser()
    {
        JSONObject JSONObject = null;

        ArrayList<HashMap <String, String>> List = new ArrayList<HashMap<String, String>>();

        try {
            JSONObject = new JSONObject(JSON_String);
            JSONArray Result = JSONObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for (int i = 0; i < Result.length(); i++) {
                JSONObject JO = Result.getJSONObject(i);
                String ID = JO.getString(Config.TAG_ID);
                String Name = JO.getString(Config.TAG_Name);

                HashMap<String, String> Users = new HashMap<>();
                Users.put(Config.TAG_ID, ID);
                Users.put(Config.TAG_Name, Name);
                List.add(Users);
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        ListAdapter Adapter = new SimpleAdapter(ViewAllUsers.this, List, R.layout.list_item, new String[]{Config.TAG_ID, Config.TAG_Name}, new int[]{R.id.ID, R.id.Name});
        ListView.setAdapter(Adapter);

    }










    private void getJSON()
    {
        class GetJSON extends AsyncTask<Void, Void, String>
        {

            ProgressDialog Loading;

            protected void onPreExecute()
            {
                super.onPreExecute();
                Loading = ProgressDialog.show(ViewAllUsers.this, "Fetching Data", "Wait...", false, false);
            }

            protected void onPostExecute(String S)
            {
                super.onPostExecute(S);
                Loading.dismiss();
                JSON_String = S;
                ShowUser();
            }

            protected String doInBackground(Void... Params)
            {
                RequestHandler RH = new RequestHandler();
                String S = RH.SendGetRequest(Config.URL_GetAllUsers);
                return S;
            }
        }

        GetJSON GJ = new GetJSON();
        GJ.execute();
    }


}
