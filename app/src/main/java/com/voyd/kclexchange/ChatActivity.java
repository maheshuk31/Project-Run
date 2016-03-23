package com.voyd.kclexchange;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nick, the Saviour of Chat in the year of our lord 2016.
 */
public class ChatActivity extends AppCompatActivity {

        Toolbar toolbar;
        EditText txtInput;
        LinearLayout linLayMessages;

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

        }

        //----------------------------------------------------------------


        public void onClickSend(View v){
            String msgTemp = txtInput.getText().toString();
            TextView txtTemp = new TextView(this);
            txtTemp.setText(msgTemp);
            linLayMessages.addView(txtTemp);
            txtInput.setText("");

            //add msgTemp to message log
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
