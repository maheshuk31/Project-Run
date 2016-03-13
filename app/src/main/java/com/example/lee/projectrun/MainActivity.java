package com.example.lee.projectrun;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;

import android.location.LocationListener;

import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements LocationListener {


    private TextView txtTitle, txtForgotPassword;
    private Button btnRegister, btnLogin;
    private EditText txtLogin, txtPassword;
    private String stringUpdateGps = "0";
    private String password, UniqueCode;
    private String stringIp;
    private boolean userfill;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Context context;
    private UserInformation userInformation, userInformation3;
    private final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userfill = true;
        txtLogin = (EditText) findViewById(R.id.txtKingsLogin);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        Typeface custom = Typeface.createFromAsset(getAssets(), "fonts/liberationserif.regular.ttf");
        txtTitle.setTypeface(custom);


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

        getIP();

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringLogin = txtLogin.getText().toString().trim();
                String stringPassword = txtPassword.getText().toString().trim();
                UniqueCode = stringLogin;
                password = stringPassword;
                userChecker();
                if (!isValidLogin(stringLogin) && stringPassword.isEmpty()) {
                    txtLogin.setError("Please enter a valid King's ID (e.g. K1234567) Or fill in a password");
                } else if (userfill == false) {
                    txtLogin.setError("Incorrect Password Or Login");
                } else if(Config.permissionrequest) {

                    getGPS();
                }
            }
        });


        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        SpannableString spannableString = new SpannableString("Forgot Your Password?");
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        txtForgotPassword.setText(spannableString);
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
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

    private UserInformation userChecker() {
        class GetUsers extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            UserInformation userInformation1;

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.Key_Password, password);
                params.put(Config.Key_ID, UniqueCode);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_CheckLogin, params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;

            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                userInformation1 = RetrieveUser(s);
                userInformation1.updateIp(stringIp);
                userInformation1.updateGPS(stringUpdateGps);
                userInformation1.updateStudent(MainActivity.this);
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                intent.putExtra("userinfo", userInformation1);
                Log.d("AAAAa", stringIp);
                Log.d("IP", stringIp);
                System.out.print(userInformation1.getFirstName());
                startActivity(intent);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Fetching...", "Wait...", false, false);
            }

            public UserInformation getUserInformation1() {
                return userInformation1;
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();


        return getUsers.getUserInformation1();
    }

    private UserInformation RetrieveUser(String json) {
        UserInformation userInformation2 = null;
        try {

            if (json != null) {
                JSONArray userInfo = new JSONArray(json);

                Log.d("AAA", userInfo.toString());
                if (!userInfo.isNull(0)) {
                    JSONObject userObject = userInfo.getJSONObject(0);
                    userInformation2 = new UserInformation(userObject.getString("UniqueCode"), userObject.getString("FirstName"), userObject.getString("Image"), userObject.getString("LastName"),
                            userObject.getString("Email"), userObject.getString("password"), userObject.getString("Age"), userObject.getString("Gender"), userObject.getString("TeachingLanguage"),
                            userObject.getString("PracticeLanguage"), userObject.getString("PersonalInterests"), userObject.getString("Friends"), userObject.getString("GPS"), userObject.getString("Stats"),
                            userObject.getString("IP"));
                } else {
                    userfill = false;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userInformation2;
    }


    /**
     * Checks to see if what the user has inputted as a login is a valid set of characters that matches
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
     * To test any inputted king's ID in the login to see if it passes
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ResourceType")
    public void getGPS() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        stringUpdateGps = location.getLatitude() + ", " + location.getLongitude();
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

    public void getIP(){

        WifiManager myWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);

        WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
        int myIp = myWifiInfo.getIpAddress();
        int intMyIp3 = myIp/0x1000000;
        int intMyIp3mod = myIp%0x1000000;
        int intMyIp2 = intMyIp3mod/0x10000;
        int intMyIp2mod = intMyIp3mod%0x10000;
        int intMyIp1 = intMyIp2mod/0x100;
        int intMyIp0 = intMyIp2mod%0x100;
        stringIp = intMyIp0 + "." + intMyIp1 + "." + intMyIp2 + "." + intMyIp3;

    }
}
