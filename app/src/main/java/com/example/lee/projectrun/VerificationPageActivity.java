package com.example.lee.projectrun;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lee.projectrun.MessageService.MessagingService;
import com.example.lee.projectrun.appInterfaces.IUserMessageNetworkManager;

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
    private static final int FILL_ALL_FIELDS = 0;
    protected static final int TYPE_SAME_PASSWORD_IN_PASSWORD_FIELDS = 1;
    private static final int SIGN_UP_FAILED = 9;
    private static final int SIGN_UP_USERNAME_CRASHED = 3;
    private static final int SIGN_UP_SUCCESSFULL = 4;
    protected static final int USERNAME_AND_PASSWORD_LENGTH_SHORT = 5;


    //private static final String SERVER_RES_SIGN_UP_FAILED = "0";
    private static final String SERVER_RES_RES_SIGN_UP_SUCCESFULL = "1";
    private static final String SERVER_RES_SIGN_UP_USERNAME_CRASHED = "2";

    private IUserMessageNetworkManager imService;
    private Handler handler = new Handler();

    private ServiceConnection mConnection = new ServiceConnection() {


        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            imService = ((MessagingService.IMBinder)service).getService();


        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            imService = null;
            Toast.makeText(VerificationPageActivity.this, R.string.local_service_stopped,
                    Toast.LENGTH_SHORT).show();
        }
    };

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

        entirePractice = practicingLanguage + "," + practicingLanguageLevel;
        entireTeaching = teachingLanguage + "," + teachingLanguageLevel;

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

        class AddStudent extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(VerificationPageActivity.this, "Adding", "Wait", false, false);
            }
            protected void onPostExecute(String s){
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
                params.put(Config.Key_TeachingLanguage, entireTeaching);
                params.put(Config.Key_PracticeLanguage, entirePractice);
                params.put(Config.Key_PersonalInterests, personalInterest);
                params.put(Config.Key_IP, ip);
                params.put(Config.Key_Image, image);
                if(gps==null){
                    params.put(Config.Key_GPS, "51.51148639999999, -0.11599699999999302" );
                } else {
                    params.put(Config.Key_GPS, gps);
                }

                params.put(Config.Key_Password, password);
                params.put(Config.Key_Friends, "");

                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_AddUser, params);
                return res;
            }
        }
        AddStudent as = new AddStudent();
        if (uniqueCode.length() > 0 &&
                password.length() > 0 &&
                password.length() > 0 &&
                Email.length() > 0
                )
        {
            //TODO check email address is valid

            if (!password.isEmpty()){

                if (uniqueCode.length() >= 5 && password.length() >= 5) {

                    Thread thread = new Thread(){
                        String result = new String();
                        @Override
                        public void run() {
                            result = imService.signUpUser(uniqueCode,
                                    password,
                                    Email);

                            handler.post(new Runnable(){

                                public void run() {
                                    if (result.equals(SERVER_RES_RES_SIGN_UP_SUCCESFULL)) {
                                        Toast.makeText(getApplicationContext(),R.string.signup_successfull, Toast.LENGTH_LONG).show();
                                        //showDialog(SIGN_UP_SUCCESSFULL);
                                    }
                                    else if (result.equals(SERVER_RES_SIGN_UP_USERNAME_CRASHED)){
                                        Toast.makeText(getApplicationContext(),R.string.signup_username_crashed, Toast.LENGTH_LONG).show();
                                        //showDialog(SIGN_UP_USERNAME_CRASHED);
                                    }
                                    else  //if (result.equals(SERVER_RES_SIGN_UP_FAILED))
                                    {
                                        Toast.makeText(getApplicationContext(),R.string.signup_failed, Toast.LENGTH_LONG).show();
                                        //showDialog(SIGN_UP_FAILED);
                                    }
                                }

                            });
                        }

                    };
                    thread.start();
                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.username_and_password_length_short, Toast.LENGTH_LONG).show();
                    //showDialog(USERNAME_AND_PASSWORD_LENGTH_SHORT);
                }
            }
            else {
                Toast.makeText(getApplicationContext(),R.string.signup_type_same_password_in_password_fields, Toast.LENGTH_LONG).show();
                //showDialog(TYPE_SAME_PASSWORD_IN_PASSWORD_FIELDS);
            }

        }
        else {
            Toast.makeText(getApplicationContext(),R.string.signup_fill_all_fields, Toast.LENGTH_LONG).show();
            //showDialog(FILL_ALL_FIELDS);

        }

        as.execute();
    }

}
