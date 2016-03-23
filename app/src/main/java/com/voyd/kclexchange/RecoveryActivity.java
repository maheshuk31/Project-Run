package com.voyd.kclexchange;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecoveryActivity extends AppCompatActivity {

    Toolbar toolbar;
    String stringName, stringKnumber;
    EditText txtKnumber;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        TextView barTitle = (TextView) findViewById(R.id.bar_title);
        barTitle.setText("Recover Password");

        txtKnumber = (EditText) findViewById(R.id.txtKNumber);
    }

    //----------------------------------------------------------------

    public void onClickDone(View view) {
        stringKnumber = txtKnumber.getText().toString();
    }

    /*private void emailSend() {
        stringName = txtName.getText().toString().trim();
        String stringEmail = (txtRegisterKingsID.getText().toString().trim())+"@kcl.ac.uk";
        String subject = "KCLexchange Reset Password";
        String message =
                "Hey " + stringName + "," + "\n" + "\n"
                        + "So you misplaced your KCLexchange password? Smart." + "\n"
                        + "\n"
                        + "No problem. Just use this temporary password:" + "\n"
                        + "\n"
                        + password + "\n"
                        + "\n"
                        + "to sign into your account, then change it to something else straight away."+ "\n"
                        + "\n"
                        + "This is an automated email please don't reply.\n "
                        + "If you have received this email in error please notify the sender immediately. The email is intended for the exclusive use of the addressee only. " + "\n"
                        + "Copyright, Void©. All rights reserved.";

        EmailSender sendEmail = new EmailSender(this, stringEmail, subject, message);

        sendEmail.execute();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Checks to see if a String provided matches a correct user ID that has a valid format of
     * beginning with K and 7 numbers.
     * @param forgotString
     * @return
     */
    private boolean isValidForgotString(String forgotString) {
        String userPatternRegex = "^[Kk]{1}[0-9]{7}$";

        Pattern userPattern = Pattern.compile(userPatternRegex);
        Matcher userMatcher = userPattern.matcher(forgotString);

        return userMatcher.matches();
    }

    /**
     * To test any inputted King's ID Username to reset your password
     * @param forgotStringHolder
     * @return
     */
    public boolean getIsValidForgotString(String forgotStringHolder){
        if(isValidForgotString(forgotStringHolder)==true){
            return true;
        }
        else{
            return false;
        }
    }
}







//onClickDone
