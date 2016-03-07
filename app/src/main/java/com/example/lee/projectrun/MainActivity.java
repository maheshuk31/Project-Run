package com.example.lee.projectrun;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    private TextView txtTitle, txtForgotPassword;
    private Button btnRegister, btnLogin;
    private EditText txtLogin, txtPassword;
    private String stringUpdateGps;
    private String password, UniqueCode;
    private boolean userfill;

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



        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringLogin = txtLogin.getText().toString().trim();
                String stringPassword = txtPassword.getText().toString().trim();
                UniqueCode = stringLogin;
                password = stringPassword;
                userChecker();
                if (!isValidLogin(stringLogin)&& stringPassword.isEmpty()) {
                    txtLogin.setError("Please enter a valid King's ID (e.g. K1234567) Or fill in a password");
                } else if (userfill==false) {
                    txtLogin.setError("Incorrect Password Or Login");
                } else {

                    Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);

                    //link with database and store the String 'stringpdateGps' and update it
                    LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    LocationListener mlocListener = new GetLocation();
                    mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

                    startActivity(intent);
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

    private void userChecker() {
        class GetUsers extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

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
                RetrieveUser(s);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Fetching...", "Wait...", false, false);
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    private void RetrieveUser(String json) {
        try {
            if (json!=null) {
                JSONArray userInfo = new JSONArray(json);

                Log.d("AAA", userInfo.toString());
                if (userInfo.length() == 1) {
                    JSONObject userObject = userInfo.getJSONObject(0);
                    UserInformation userInformation = new UserInformation(userObject.getString("UniqueCode"), userObject.getString("FirstName"), userObject.getString("Image"), userObject.getString("LastName"),
                            userObject.getString("Email"), userObject.getString("password"), userObject.getString("Age"), userObject.getString("Gender"), userObject.getString("TeachingLanguage"),
                            userObject.getString("PracticeLanguage"), userObject.getString("PersonalInterests"), userObject.getString("Friends"));
                } else {
                    userfill = false;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    class GetLocation implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {

            location.getLatitude();
            location.getLongitude();

            stringUpdateGps = location.getLatitude() + ", " + location.getLongitude();

//            Toast.makeText(getApplicationContext(), stringUpdateGps, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        @Override
        public void onProviderEnabled(String provider) {
//            Toast.makeText(getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onProviderDisabled(String provider) {
//            Toast.makeText(getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();
        }
    }
}
