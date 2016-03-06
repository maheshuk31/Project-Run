package com.example.lee.projectrun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class VerificationPageActivity extends AppCompatActivity {

    private String fName;
    private String lName;
    private String uniqueCode;
    private String Email ;
    private String age;
    private String password ;
    private String gender;
    private String practicingLanguage;
    private String teachingLanguage;
    private String practicingLanguageLevel;
    private String teachingLanguageLevel;
    private String entirePractice, entireTeaching;
    private String personalInterest;
    private String image;
    private String ip;
    private String code;
    private String gps;
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
        lName = intent.getExtras().getString("lname");
        uniqueCode = intent.getExtras().getString("uniqueCode");
        Email = intent.getExtras().getString("Email");
        age = intent.getExtras().getString("age");
        password= intent.getExtras().getString("password");
        gender = intent.getExtras().getString("gender");
        practicingLanguage = intent.getExtras().getString("practicingLanguage");
        practicingLanguageLevel = intent.getExtras().getString("practicingLanguageLevel");
        teachingLanguage = intent.getExtras().getString("teachingLanguage");
        teachingLanguageLevel = intent.getExtras().getString("teachingLanguageLevel");
        personalInterest = intent.getExtras().getString("personalInterest");
        ip = intent.getExtras().getString("ip");
        image = intent.getExtras().getString("image");
        code = intent.getExtras().getString("code");
        gps = intent.getExtras().getString("gps");

        entirePractice = practicingLanguage + ", " + practicingLanguageLevel;
        entireTeaching = teachingLanguage + ", " + teachingLanguageLevel;

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
                params.put(Config.Key_Age, age);
                params.put(Config.Key_LName, lName);
                params.put(Config.Key_Gender, gender);
                params.put(Config.Key_PersonalInterests, personalInterest);
                params.put(Config.Key_IP, ip);
                params.put(Config.Key_Image, image);
                //params.put(Config.Key_GPS, gps);
                params.put(Config.Key_Password, password);

                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_AddUser, params);
                return res;
            }
        }
        AddStudent as = new AddStudent();
        as.execute();
    }

}
