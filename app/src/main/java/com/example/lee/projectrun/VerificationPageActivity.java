package com.example.lee.projectrun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.HashMap;

public class VerificationPageActivity extends AppCompatActivity {

    private String fName;
    private String uniqueCode;
    private String Email ;
    private String age;
    private String password ;
    private String gender;
    private String practicingLanguage;
    private String teachingLanguage;
    private String personalInterest;
    private String image;
    private String ip;
    private String code;
    private EditText txtVerificationInput;
    private Button btnVefSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_page);

        txtVerificationInput = (EditText) findViewById(R.id.txtVerificationInput);
        btnVefSubmit = (Button) findViewById(R.id.btnVefSubmit);

        Intent intent = getIntent();
        fName = intent.getExtras().getString("fname");
        uniqueCode = intent.getExtras().getString("uniqueCode");
        Email = intent.getExtras().getString("Email");
        age = intent.getExtras().getString("age");
        password= intent.getExtras().getString("password");
        gender = intent.getExtras().getString("gender");
        practicingLanguage = intent.getExtras().getString("practicingLanguage");
        teachingLanguage = intent.getExtras().getString("teachingLanguage");
        personalInterest = intent.getExtras().getString("personalInterest");
        ip = intent.getExtras().getString("ip");
        code = intent.getExtras().getString("code");
        btnVefSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //checks the code
                if(txtVerificationInput.getText().toString().trim().equals(code)){
                    addStudent();
                    Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                    startActivity(intent);
                }
                else{
                    txtVerificationInput.setError("Not the correct code try again");
                }

            }
        });
    }

    public void addStudent(){
//        final String fName = txtFirstName.getText().toString().trim();
//        final String uniqueCode = txtRegisterKingsID.getText().toString().trim();
//        final String Email = txtEmailAddress.getText().toString().trim();
//        final String age = txtAge.getText().toString().trim();
//        final String password = txtRegisterPassword.getText().toString().trim();
//        final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();
//        final String practicingLanguage = spinnerPractice1.getSelectedItem().toString();
//        final String teachingLanguage = spinnerTeaching1.getSelectedItem().toString();
//        final String personalInterest = txtPersonalInterest.getText().toString().trim();
//        final String image;

        class AddStudent extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(VerificationPageActivity.this, "Adding", "Wait", false, false);
            }
            protected void onPostExecture(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(VerificationPageActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.Key_Name, fName);
                params.put(Config.Key_ID, uniqueCode);
                params.put(Config.Key_Email, Email);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_AddUser, params);
                return res;
            }
        }
        AddStudent as = new AddStudent();
        as.execute();
    }

}
