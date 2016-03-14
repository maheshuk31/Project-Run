package com.example.lee.projectrun;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private EditText txtFirstName, txtLastName;
    private EditText txtRegisterKingsID;
    private EditText txtEmailAddress;
    private EditText txtRegisterPassword;
    private EditText txtRegisterConfirmPassword;
    private EditText txtAge;
    private EditText txtPersonalInterest;
    private RadioButton radioBtnMale, radioBtnFemale;
    private RadioGroup radioGroupGender;
    private String stringKingsID, stringEmail, stringPassword, stringConfirmPassword;
    private String stringFirstName, stringLastName, stringAge, stringTeachingLanguage, stringPracticeLanguage;
    private String stringTeachingLanguageLevel, stringPracticeLanguageLevel;
    private String stringPersonalInterest, stringGender, stringCodeHolder, stringImage, stringGps;
    private Spinner spinnerTeaching1, spinnerTeaching1Level;
    private Spinner spinnerPractice1, spinnerPractice1Level;
    private ImageView imgRegisterUser;
    private Button btnConfirmRegister, btnUploadImage;
    private Boolean booleanFirstName = true;
    private Boolean booleanLastName = true;
    private Boolean booleanKingsID = true;
    private Boolean booleanEmail = true;
    private Boolean booleanPassword = true;
    private Boolean booleanConfirmPassword = true;
    private Boolean booleanGenderSelected = true;
    private static String letterList = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Random random = new Random();
    private String[] arrayListLanguages = {"Select a Language", "Arabic", "Bengali", "Dutch", "English", "French",
            "German", "Greek", "Gujarati", "Hebrew",
            "Hindi", "Italian", "Japanese", "Korean",
            "Mandarin", "Punjabi", "Persian (Farsi)", "Polish",
            "Portuguese (Brazilian)", "Portuguese(European)", "Portuguese via Spanish", "Russian",
            "Spanish", "Swedish", "Turkish", "Urdu"};
    private String[] arrayLanguageLevel = {"Select a Level", "A1", "A2", "B1", "B2", "C1", "C2"};
    private static final int RESULT_LOAD_IMAGE = 1;
    private String stringIp;
    private LocationManager locationManager;
    private final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        new RetrieveOnlineStatusTask().execute((Void) null);

        ArrayAdapter<String> adapterLanguages = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, arrayListLanguages);
        ArrayAdapter<String> adapterSkill = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, arrayLanguageLevel);

        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtRegisterKingsID = (EditText) findViewById(R.id.txtRegisterKingsID);
        txtEmailAddress = (EditText) findViewById(R.id.txtEmailAddress);
        txtRegisterPassword = (EditText) findViewById(R.id.txtRegisterPassword);
        txtRegisterConfirmPassword = (EditText) findViewById(R.id.txtRegisterConfirmPassword);
        btnConfirmRegister = (Button) findViewById(R.id.btnConfirmRegister);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        radioBtnMale = (RadioButton) findViewById(R.id.radioBtnMale);
        radioBtnFemale = (RadioButton) findViewById(R.id.radioBtnFemale);
        txtAge = (EditText) findViewById(R.id.txtAge);
        txtPersonalInterest = (EditText) findViewById(R.id.txtPersonalInterests);

        spinnerTeaching1 = (Spinner) findViewById(R.id.spinnerTeaching1);
        spinnerTeaching1Level = (Spinner) findViewById(R.id.spinnerTeaching1Level);

        spinnerTeaching1.setAdapter(adapterLanguages);
        spinnerTeaching1Level.setAdapter(adapterSkill);


        spinnerPractice1 = (Spinner) findViewById(R.id.spinnerPractice1);
        spinnerPractice1Level = (Spinner) findViewById(R.id.spinnerPractice1Level);

        spinnerPractice1.setAdapter(adapterLanguages);
        spinnerPractice1Level.setAdapter(adapterSkill);


        imgRegisterUser = (ImageView) findViewById(R.id.imgRegisterUser);
        btnUploadImage = (Button) findViewById(R.id.btnUploadImage);

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, RESULT_LOAD_IMAGE);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                } else {

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                }
            }
            else{
                getGPS();
                Config.permissionrequest = true;
            }
        }
        else{
            getGPS();
            Config.permissionrequest = true;
        }


        btnConfirmRegister.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Config.permissionrequest = true;
                    getGPS();


                } else {

                }
                return;
            }

        }
    }

    /**
     * Links with EmailSender Class to send an email with the user's email and corresponding email.
     * Code generator method is used, any amount of letters that the code has to have can be changed.
     */
    private void emailSend() {
        stringFirstName = txtFirstName.getText().toString().trim();
        stringLastName = txtLastName.getText().toString().trim();
        stringEmail = txtEmailAddress.getText().toString().trim();
        stringCodeHolder = setCodeHolder(6);
        String subject = "One Final Step, Please Register";
        String message =
                "Welcome " + stringFirstName + " " + stringLastName + "," + "\n" + "\n"
                        + "Thank you for registering for the Tandem Learning App to complete your registration please use the following code " + "\n" + "\n"
                        + stringCodeHolder + "\n" + "\n"
                        + "in the application to complete your account verification to be able to use the app" + "\n" + "\n" + "\n"
                        + "This is a automated email please do not reply to it. "
                        + "If you have received this email in error please notify the sender immediately. The email is intended for the exclusive use of the addressee only. " + "\n"
                        + "Copyright, Team Void©. All rights reserved.";

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
     *
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
        stringAge = txtAge.getText().toString();
        stringPersonalInterest = txtPersonalInterest.getText().toString();
        stringPracticeLanguage = spinnerPractice1.getSelectedItem().toString();
        stringPracticeLanguageLevel = spinnerPractice1Level.getSelectedItem().toString();
        stringTeachingLanguage = spinnerTeaching1.getSelectedItem().toString();
        stringTeachingLanguageLevel = spinnerTeaching1Level.getSelectedItem().toString();
        stringGender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();

        if (TextUtils.isEmpty(stringFirstName)) {
            booleanFirstName = false;
            txtFirstName.setError("Please Enter Your First Name");
            return;
        } else {
            booleanFirstName = true;
        }
        if (TextUtils.isEmpty(stringLastName)) {
            booleanLastName = false;
            txtLastName.setError("Please enter your last name");
            return;
        } else {
            booleanLastName = true;
        }

        if (!isValidLogin(stringKingsID)) {
            booleanKingsID = false;
            txtRegisterKingsID.setError("Please enter a valid King's ID (e.g. K1234567");
        } else {
            booleanKingsID = true;
        }
        if (!isValidEmail(stringEmail)) {
            booleanEmail = false;
            txtEmailAddress.setError("Please enter a valid KCL Email");
        } else {
            booleanEmail = true;
        }
        if (!isValidPassword(stringPassword)) {
            booleanPassword = false;
            txtRegisterPassword.setError("Please enter a valid Password, MIN 6 characters and at least 1 number");
        } else {
            booleanPassword = true;
        }
        if (!stringConfirmPassword.equals(stringPassword)) {
            booleanConfirmPassword = false;
            txtRegisterConfirmPassword.setError("The passwords do not match");
        } else {
            booleanConfirmPassword = true;
        }
        if (!radioBtnMale.isChecked() && !radioBtnFemale.isChecked()) {
            booleanGenderSelected = false;
            radioBtnMale.setError("Please select a gender");
        } else {
            booleanGenderSelected = true;
        }
        if (stringPracticeLanguage.equals("Select a Language")){
            stringPracticeLanguage = "";
        }
        if (stringTeachingLanguage.equals("Select a Language")){
            stringTeachingLanguage = "";
        }
        if (stringPracticeLanguageLevel.equals("Select a Level")){
            stringPracticeLanguageLevel = "";
        }
        if (stringTeachingLanguageLevel.equals("Select a Level")){
            stringTeachingLanguageLevel = "";
        }

        if (booleanFirstName == true && booleanLastName && booleanKingsID == true && booleanEmail == true &&
                booleanPassword == true && booleanConfirmPassword == true &&
                booleanGenderSelected == true) {

            BitmapDrawable drawable = (BitmapDrawable) imgRegisterUser.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bb = bos.toByteArray();
            stringImage = Base64.encodeToString(bb, 0);

            emailSend();
            Intent intent = new Intent(getApplicationContext(), VerificationPageActivity.class);
            intent.putExtra("fname", stringFirstName);
            intent.putExtra("lname", stringLastName);
            intent.putExtra("uniqueCode", stringKingsID);
            intent.putExtra("Email", stringEmail);
            intent.putExtra("age", stringAge);
            intent.putExtra("password", stringPassword);
            intent.putExtra("gender", stringGender);
            intent.putExtra("practicingLanguage", stringPracticeLanguage);
            intent.putExtra("practicingLanguageLevel", stringPracticeLanguageLevel);
            intent.putExtra("teachingLanguage", stringTeachingLanguage);
            intent.putExtra("teachingLanguageLevel", stringTeachingLanguageLevel);
            intent.putExtra("personalInterest", stringPersonalInterest);
            intent.putExtra("ip", stringIp);
            intent.putExtra("image", stringImage);
            intent.putExtra("gps", stringGps);

            intent.putExtra("code", stringCodeHolder);
            startActivity(intent);
        }
    }
    /**
     * Checks to see if what the user has inputted to register is a valid set of characters that matches
     * a King's student User ID.
     *
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
     *
     * @param kingsIDHolder
     * @return
     */
    public boolean getIsValidKingsID(String kingsIDHolder) {
        if (isValidLogin(kingsIDHolder) == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checking to see if the correct email pattern for a KCL person is valid
     *
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
     *
     * @param emailHolder
     * @return
     */
    public boolean getIsValidEmail(String emailHolder) {
        if (isValidEmail(emailHolder) == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checking to see if the correct password inputted has at least 1 Number and minimum 6 characters
     *
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
     *
     * @param passwordHolder
     * @return
     */
    public boolean getIsValidPassword(String passwordHolder) {
        if (isValidPassword(passwordHolder) == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Random code generator that will be used for generating a code that will be associated with a
     * user's account to validate their account
     *
     * @param codeLength generates a code of a length given
     * @return code generated into a String
     */
    private String codeGenerator(int codeLength) {
        StringBuilder codeBuilder = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++)
            codeBuilder.append(letterList.charAt(random.nextInt(letterList.length())));
        return codeBuilder.toString();
    }

    /**
     * Setting a randomised code, can be used to test to see if it works too.
     * Can changed the code length by changing the parameter
     *
     * @return code generated in a String
     */
    public String setCodeHolder(int length) {
        return codeGenerator(length);
    }

    /**
     * Activity adapts to the change of the image that is being selected
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imgRegisterUser.setImageURI(selectedImage);
        }
    }

    class RetrieveOnlineStatusTask extends AsyncTask<Void, Void, BufferedReader> {

        @Override
        protected BufferedReader doInBackground(Void... arg0) {
            URL url;
            BufferedReader bufferedReader = null;
            InputStreamReader inputStreamReader = null;
            HttpURLConnection httpURLConnection = null;
            try {
                url = new URL("http://ip2country.sourceforge.net/ip2c.php?format=JSON");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuffer buffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                    buffer.append('\r');
                }
                bufferedReader.close();
                inputStreamReader.close();
                JSONObject json_data = new JSONObject(buffer.toString());
                stringIp = json_data.getString("ip");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    @SuppressWarnings("ResourceType")
    public void getGPS() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        stringGps = location.getLatitude() + ", " + location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
