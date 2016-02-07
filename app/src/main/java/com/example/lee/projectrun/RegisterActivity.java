package com.example.lee.projectrun;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtEmailAddress;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private String stringFirstName, stringLastName, stringEmail, stringPassword, stringConfirmPassword;
    private Button btnRegister;
    private Boolean booleanFirstName = true;
    private Boolean booleanLastName = true;
    private Boolean booleanEmail = true;
    private Boolean booleanPassword = true;
    private Boolean booleanConfirmPassword = true;
    private static String letterList = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtEmailAddress = (EditText) findViewById(R.id.txtEmailAddress);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);
    }

    /**
     * Links with EmailSender Class to send an email with the user's email and corresponding email.
     * Code generator method is used, any amount of letters that the code has to have can be changed.
     */
    private void emailSend() {
        stringFirstName = txtFirstName.getText().toString().trim();
        stringLastName = txtLastName.getText().toString().trim();
        String email = txtEmailAddress.getText().toString().trim();
        String subject = "One Final Step, Please Register";
        String message = "Welcome " + stringFirstName + " " + stringLastName + "\n"
                + "Please use this code " + codeGenerator(4) + " to complete your registration." + "\n"
                + "This is a automated email please do not reply to it.";

        /*if statement to check to see if there is a code in the system and resend the same code OR
        create a new random code*/

        EmailSender sendEmail = new EmailSender(this, email, subject, message);

        sendEmail.execute();
    }

    /**
     * On click method for the Button
     * If statements to check there is no empty fields, if all passed then email is sent and returns
     * user to main page.
     * If any are false then a message will be displayed as to why it has failed.
     * @param v
     */
    @Override
    public void onClick(View v) {

        stringFirstName = txtFirstName.getText().toString();
        stringLastName = txtLastName.getText().toString();
        stringEmail = txtEmailAddress.getText().toString();
        stringPassword = txtPassword.getText().toString();
        stringConfirmPassword = txtConfirmPassword.getText().toString();
        if(TextUtils.isEmpty(stringFirstName)) {
            booleanFirstName = false;
            txtFirstName.setError("Please Enter Your First Name");
            return;
        }
        else{
            booleanFirstName = true;
        }
        if(TextUtils.isEmpty(stringLastName)) {
            booleanLastName = false;
            txtLastName.setError("Please enter Your Last Name");
            return;
        }
        else{
            booleanLastName = true;
        }
        if(!isValidEmail(stringEmail)) {
            booleanEmail = false;
            txtEmailAddress.setError("Please enter a valid KCL Email");
        }
        else{
            booleanEmail = true;
        }
        if(!isValidPassword(stringPassword)){
            booleanPassword = false;
            txtPassword.setError("Please enter a valid Password, MIN 6 characters and at least 1 number");
        }
        else{
            booleanPassword = true;
        }

        if(!stringConfirmPassword.equals(stringPassword)){
            booleanConfirmPassword = false;
            txtConfirmPassword.setError("The passwords do not match");
        }
        else{
            booleanConfirmPassword = true;
        }

        if(booleanFirstName == true && booleanLastName == true && booleanEmail == true && booleanPassword == true && booleanConfirmPassword == true) {
            emailSend();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * A seperate boolean method to check if the correct email patter for a KCL person is valid
     * @param emailString
     * @return
     */
    private boolean isValidEmail(String emailString) {
        String emailPattern = "^[A-Za-z0-9]+(\\.[A-Za-z]+)*@kcl+\\.ac\\.uk$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(emailString);
        return matcher.matches();
    }

    private boolean isValidPassword(String passwordString) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";

        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(passwordString);
        return matcher.matches();
    }

    /**
     * A random code generator that will be used for generating a code that will be assosciated
     * with a user's account to validate their account
     * @param codeLength
     * @return
     */
    String codeGenerator(int codeLength){
        StringBuilder codeBuilder = new StringBuilder(codeLength);
        for(int i=0; i<codeLength; i++)
            codeBuilder.append(letterList.charAt(random.nextInt(letterList.length())));
        return codeBuilder.toString();
    }

}
