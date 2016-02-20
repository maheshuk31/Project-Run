package com.example.lee.projectrun;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// I am here

public class MainActivity extends AppCompatActivity {

    private Button btnRegister, btnLogin;
    private TextView txtTitle;
    private EditText txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLogin = (EditText) findViewById(R.id.txtKingsLogin);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        Typeface custom = Typeface.createFromAsset(getAssets(), "fonts/liberationserif.regular.ttf");
        txtTitle.setTypeface(custom);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringLogin = txtLogin.getText().toString().trim();
                if(!isValidLogin(stringLogin)) {
                    txtLogin.setError("Please enter a valid King's ID (e.g. K1234567");
                }
                else{
                    //Code for moving to picture page
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
    }

    private boolean isValidLogin(String loginString) {
        String loginPattern = "^[Kk]{1}[0-9]{7}$";

        Pattern pattern = Pattern.compile(loginPattern);
        Matcher matcher = pattern.matcher(loginString);
        return matcher.matches();
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
}
