package com.example.lee.projectrun;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button btnForgotSubmit, btnForgotCancel;
    private EditText txtForgotPassword;
    private Boolean booleanForgotText = true;
    private String stringForgotText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_acitivty);

        txtForgotPassword = (EditText) findViewById(R.id.txtForgotPassword);
        btnForgotSubmit = (Button) findViewById(R.id.btnForgotSubmit);
        btnForgotSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringForgotText = txtForgotPassword.getText().toString().trim();
                if(!isValidForgotString(stringForgotText)) {
                    booleanForgotText = false;
                    txtForgotPassword.setError("Please enter a valid KCL Email or King's ID");
                }
                else{
                    booleanForgotText = true;
                }

                if(booleanForgotText == true){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast toast=Toast.makeText(getApplicationContext(), "Please check your email", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        btnForgotCancel = (Button) findViewById(R.id.btnForgotCancel);
        btnForgotCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Checks to see if a String provided matches either the correct Email Regular Expression or
     * a correct user ID that has a valid format of beginning with K and 7 numbers.
     * @param forgotString
     * @return
     */
    private boolean isValidForgotString(String forgotString) {
        String userPatternRegex = "^[Kk]{1}[0-9]{7}$";
        String emailPatternRegex = "^[A-Za-z0-9]+(\\.[A-Za-z]+)*@kcl+\\.ac\\.uk$";

        Pattern userPattern = Pattern.compile(userPatternRegex);
        Pattern emailPattern = Pattern.compile(emailPatternRegex);
        Matcher userMatcher = userPattern.matcher(forgotString);
        Matcher emailMatcher = emailPattern.matcher(forgotString);

        return userMatcher.matches() || emailMatcher.matches();
    }

    /**
     * To test any inputted email or King's ID Username to reset your password
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
