package com.voyd.kclexchange;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by Nick, the Saviour of Chat - in the year of our lord 2016.
 */
public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText txtInput;
    LinearLayout linLayMessages;
    String otherUnique;
    String CombinedUnique1, CombinedUnique2;
    String[] messages;
    String messagesWhole;
    String msgTemp;
    TextView txtTemp;
    String CurrentMessage;
    Button btnSend;

    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        TextView barTitle = (TextView) findViewById(R.id.bar_title);
        barTitle.setText("Chat");

        txtInput = (EditText) findViewById(R.id.txtInput);
        linLayMessages = (LinearLayout) findViewById(R.id.linLayMessages);
        btnSend = (Button) findViewById(R.id.btnSend);

        Intent intent = getIntent();
        userInformation = (UserInformation) intent.getSerializableExtra("userinfo");
        otherUnique = intent.getExtras().getString("profileUnique");

        CombinedUnique1 = otherUnique + userInformation.getUniqueCode();
        CombinedUnique2 = userInformation.getUniqueCode() + otherUnique;
        CurrentMessage = defaultfill();

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (linLayMessages.getChildCount() != 0) {
                                    fillMessageRoom();
                                } else {
                                    defaultfill();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }

    /**
     * Method that retrieves previous messages
     */
    private String defaultfill() {

        class GetUsers extends AsyncTask<Void, Void, String> {

            String s;

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("Combine", CombinedUnique1);
                params.put("Combine2", CombinedUnique2);
                params.put("Message", "");
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest("http://projectrun02.x10host.com/MessageSpecificSearch.php", params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;
            }

            /**
             * After the response is given from the database
             * @param s json String
             */
            protected void onPostExecute(String s) {
                this.s = s;
                super.onPostExecute(s);

                showResult(s);

                btnSend.setEnabled(true);
                btnSend.setBackgroundResource(R.drawable.button_rounded);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            public String getjson() {
                return s;
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
        return getUsers.getjson();
    }



    /**
     * Method that retrieves previous messages
     */
    private void fillMessageRoom() {

        class GetUsers extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("Combine", CombinedUnique1);
                params.put("Combine2", CombinedUnique2);
                params.put("Message", "");
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest("http://projectrun02.x10host.com/MessageSpecificSearch.php", params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;
            }

            /**
             * After the response is given from the database
             * @param s json String
             */
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equals(CurrentMessage)){

                } else {
                    linLayMessages.removeAllViews();
                    showResult(s);
                }

                btnSend.setEnabled(true);
                btnSend.setBackgroundResource(R.drawable.button_rounded);

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    /**
     * Method to generate the objects for the search result
     *
     * @param json String that contains the json array
     */
    private void showResult(String json) {
        try {
            JSONArray search = new JSONArray(json);
            Log.d("AAA", search.toString());
            messages = search.getJSONObject(0).getString("Messages").split(",-,");
            generateDefaultText(messages);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------
    public void generateDefaultText(String[] messages) {

        for(int i = 0; i< messages.length; i++){
            if(messages[i].equals(userInformation.getUniqueCode())){
                String msgTemp = messages[i+1];
                TextView txtTemp = new TextView(this);
                txtTemp.setText(msgTemp);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
                params.weight = 1.0f;
                params.gravity = Gravity.RIGHT;
                txtTemp.setLayoutParams(params);
                linLayMessages.addView(txtTemp);
            } else {
                String msgTemp = messages[i+1];
                TextView txtTemp = new TextView(this);
                txtTemp.setText(msgTemp);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
                params.weight = 1.0f;
                params.gravity = Gravity.LEFT;
                txtTemp.setLayoutParams(params);
                linLayMessages.addView(txtTemp);
            }

            i++;
        }
    }


    public void onClickSend(View v){
        if(!txtInput.getText().toString().equals("")){
            btnSend.setEnabled(false);
            btnSend.setBackgroundResource(R.drawable.button_rounded_inactive);
            txtTemp = new TextView(this);
            fillMessageRoom12();
        }


        //add msgTemp to message log
    }

    /**
     * Method that retrieves previous messages
     */
    private void fillMessageRoom12() {
        class GetUsers extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("Combine", CombinedUnique1);
                params.put("Combine2", CombinedUnique2);
                params.put("Message", "");
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest("http://projectrun02.x10host.com/MessageSpecificSearch.php", params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;
            }

            /**
             * After the response is given from the database
             * @param s json String
             */
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showResult(s);

                msgTemp = txtInput.getText().toString();

                txtTemp.setText(msgTemp);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
                params.weight = 1.0f;
                params.gravity = Gravity.RIGHT;
                txtTemp.setLayoutParams(params);
                linLayMessages.addView(txtTemp);
                txtInput.setText("");

                messagesWhole = "";
                if(messages!=null){
                    if(messages.length == 60){
                        String[] messagesTemp = messages;

                        for(int i = 0; i < 58; i++){
                            messagesTemp[i] = messages[i+2];
                        }

                        messagesTemp[58] = userInformation.getUniqueCode();
                        messagesTemp[59] = msgTemp;

                        for(int i = 0; i < 60; i++){
                            messagesWhole += messagesTemp[i];
                            messagesWhole += ",-,";
                        }
                    } else {
                        for(int i = 0; i < messages.length; i++){
                            messagesWhole += messages[i];
                            messagesWhole += ",-,";
                        }
                        messagesWhole += userInformation.getUniqueCode();
                        messagesWhole += ",-,";
                        messagesWhole += msgTemp;
                    }
                } else {
                    messagesWhole += userInformation.getUniqueCode();
                    messagesWhole += ",-,";
                    messagesWhole += msgTemp;
                }
                sendMessagesToServer();
                btnSend.setEnabled(true);
                btnSend.setBackgroundResource(R.drawable.button_rounded);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                linLayMessages.removeAllViews();
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    /**
     * Method that retrieves previous messages
     */
    private void sendMessagesToServer() {
        class GetUsers extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("Combine", CombinedUnique1);
                params.put("Combine2", CombinedUnique2);
                params.put("Message", messagesWhole);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest("http://projectrun02.x10host.com/MessageCreate.php", params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;
            }

            /**
             * After the response is given from the database
             * @param s json String
             */
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showResult(s);
                //timerMethod();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    public void onClickLocateContact(View v){

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Explain chat").setTitle("Help");

            builder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {}});

            AlertDialog dialog = builder.create();
            dialog.show();
            TextView textDialog = (TextView)dialog.findViewById(android.R.id.message);
            textDialog.setGravity(Gravity.CENTER);

            return true;
        }

        if (id == android.R.id.home) {
            DataStore.setCurrentUser(userInformation);
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

}
