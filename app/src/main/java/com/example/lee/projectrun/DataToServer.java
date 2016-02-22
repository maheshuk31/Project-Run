package com.example.lee.projectrun;


import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataToServer {

    public InputStream inputStream = null;

    /**
     * Creation of user to database
     */
    public void createUser(String Unique, String Password, String Email, String FName) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("UniqueCode", Unique));
        //Password is lowercase because of post in php
        nameValuePairs.add(new BasicNameValuePair("password", Password));
        nameValuePairs.add(new BasicNameValuePair("Email", Email));
        nameValuePairs.add(new BasicNameValuePair("FName", FName));

        connectPHP(nameValuePairs, "http://projectrun.x10host.com/UserCreation.php");

    }

    /**
     * Links to the PHP "ModifyUser.PHP"
     */
    public void modifyUser(String Unique, String FName, String LName, String Email, String Password, String Age, String Gender, String Friends, String Teaching, String Practice, String Personal) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("Unique", Unique));
        nameValuePairs.add(new BasicNameValuePair("FName", FName));
        nameValuePairs.add(new BasicNameValuePair("LName", LName));
        nameValuePairs.add(new BasicNameValuePair("Email", Email));
        nameValuePairs.add(new BasicNameValuePair("password", Password));
        nameValuePairs.add(new BasicNameValuePair("Age", Age));
        nameValuePairs.add(new BasicNameValuePair("Gender", Gender));
        nameValuePairs.add(new BasicNameValuePair("Friends", Friends));
        nameValuePairs.add(new BasicNameValuePair("TeachingLanguage", Teaching));
        nameValuePairs.add(new BasicNameValuePair("PracticeLanguage", Practice));
        nameValuePairs.add(new BasicNameValuePair("PersonalInterests", Personal));
        nameValuePairs.add(new BasicNameValuePair("Friends", Friends));

        connectPHP(nameValuePairs, "http://projectrun.x10host.com/UserModification.php");
    }

    /**
     * Method to connect to specific PHP's
     * @param nameValuePairs List of POSTS for the php being connected
     * @param Http Http to connect to
     */
    public void connectPHP(List<NameValuePair> nameValuePairs, String Http){
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Http);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();

            //String userCreated = "User Created";

            //Toast.makeText(DataToServer.this, userCreated, Toast.LENGTH_SHORT).show();

        }
        catch (ClientProtocolException e) {
            Log.e("CP", "Log_Tag");
            e.printStackTrace();
        }
        catch (IOException e){
            Log.e("IO", "Log_Tag");
            e.printStackTrace();
        }
    }

}
