package com.example.lee.projectrun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtRegisterKingsID;
    private EditText txtEmailAddress;
    private EditText txtRegisterPassword;
    private EditText txtRegisterConfirmPassword;
    private String stringFirstName, stringLastName, stringKingsID, stringEmail, stringPassword,
            stringConfirmPassword;
    private Button btnConfirmRegister;
    private Boolean booleanFirstName = true;
    private Boolean booleanLastName = true;
    private Boolean booleanKingsID = true;
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
        txtRegisterKingsID = (EditText) findViewById(R.id.txtRegisterKingsID);
        txtEmailAddress = (EditText) findViewById(R.id.txtEmailAddress);
        txtRegisterPassword = (EditText) findViewById(R.id.txtRegisterPassword);
        txtRegisterConfirmPassword = (EditText) findViewById(R.id.txtRegisterConfirmPassword);
        btnConfirmRegister = (Button) findViewById(R.id.btnConfirmRegister);

        btnConfirmRegister.setOnClickListener(this);
    }

    /**
     * Links with EmailSender Class to send an email with the user's email and corresponding email.
     * Code generator method is used, any amount of letters that the code has to have can be changed.
     */
    private void emailSend() {
        stringFirstName = txtFirstName.getText().toString().trim();
        stringLastName = txtLastName.getText().toString().trim();
        stringEmail = txtEmailAddress.getText().toString().trim();

        String subject = "One Final Step, Please Register";
        String message = "Welcome " + stringFirstName + " " + stringLastName + "\n"
                + "Please use this code " + setCodeHolder(6) + " to complete your registration." + "\n"
                + "This is a automated email please do not reply to it.";

        /*if statement to check to see if there is a code in the system and resend the same code OR
        create a new random code*/

        EmailSender sendEmail = new EmailSender(this, stringEmail, subject, message);

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
        stringKingsID = txtRegisterKingsID.getText().toString();
        stringEmail = txtEmailAddress.getText().toString();
        stringPassword = txtRegisterPassword.getText().toString();
        stringConfirmPassword = txtRegisterConfirmPassword.getText().toString();
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
        if(!isValidLogin(stringKingsID)) {
            booleanKingsID = false;
            txtRegisterKingsID.setError("Please enter a valid King's ID (e.g. K1234567");
        }
        else{
            booleanKingsID = true;
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
            txtRegisterPassword.setError("Please enter a valid Password, MIN 6 characters and at least 1 number");
        }
        else{
            booleanPassword = true;
        }

        if(!stringConfirmPassword.equals(stringPassword)){
            booleanConfirmPassword = false;
            txtRegisterConfirmPassword.setError("The passwords do not match");
        }
        else{
            booleanConfirmPassword = true;
        }

        if(booleanFirstName == true && booleanLastName == true && booleanKingsID == true &&
                booleanEmail == true && booleanPassword == true && booleanConfirmPassword == true) {
            emailSend();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Checks to see if what the user has inputted to register is a valid set of characters that matches
     * a King's student User ID.
     * @param loginString
     * @return
     */
    private boolean isValidLogin(String loginString) {
        String loginPattern = "^[Kk]{1}[0-9]{7}$";

        Pattern pattern = Pattern.compile(loginPattern);
        Matcher matcher = pattern.matcher(loginString);
        return matcher.matches();
    }
    /**
     * To test any inputted king's ID to see if it passes
     * @param kingsIDHolder
     * @return
     */
    public boolean getIsValidKingsID(String kingsIDHolder){
        if(isValidLogin(kingsIDHolder)==true){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Checking to see if the correct email pattern for a KCL person is valid
     * @param emailString
     * @return
     */
    private boolean isValidEmail(String emailString) {
        String emailPattern = "^[A-Za-z0-9]+(\\.[A-Za-z]+)*@kcl+\\.ac\\.uk$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(emailString);
        return matcher.matches();
    }

    /**
     * To test any inputted email address to see if it passes
     * @param emailHolder
     * @return
     */
    public boolean getIsValidEmail(String emailHolder){
        if(isValidEmail(emailHolder)==true){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Checking to see if the correct password inputted has at least 1 Number and minimum 6 characters
     * @param passwordString
     * @return
     */
    private boolean isValidPassword(String passwordString) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";

        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(passwordString);
        return matcher.matches();
    }

    /**
     * To test any inputted password to see if it passes
     * @param passwordHolder
     * @return
     */
    public boolean getIsValidPassword(String passwordHolder){
        if(isValidPassword(passwordHolder)==true){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Random code generator that will be used for generating a code that will be associated with a
     * user's account to validate their account
     * @param codeLength generates a code of a length given
     * @return code generated into a String
     */
    private String codeGenerator(int codeLength){
        StringBuilder codeBuilder = new StringBuilder(codeLength);
        for(int i=0; i<codeLength; i++)
            codeBuilder.append(letterList.charAt(random.nextInt(letterList.length())));
        return codeBuilder.toString();
    }

    /**
     * Setting a randomised code, can be used to test to see if it works too.
     * Can changed the code length by changing the parameter
     * @return code generated in a String
     */
    public String setCodeHolder(int length){
        return codeGenerator(length);
    }

}
