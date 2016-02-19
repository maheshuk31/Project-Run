package com.example.lee.projectrun;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mahesh on 19/02/2016.
 */
public class MainActivityInstrumentationTests extends ActivityInstrumentationTestCase2<MainActivity> {
    public MainActivityInstrumentationTests() {
        super(MainActivity.class);
    }

    public void testMainActivityExists(){
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity);
    }

    //Can be removed if we go with an image that has the title of our app rather then a font
    public void testTitleTextViewExists(){
        MainActivity mainActivity = getActivity();
        TextView txtTitle = (TextView) mainActivity.findViewById(R.id.txtTitle);
        assertNotNull(txtTitle);
    }

    public void testImageAppImageViewExists(){
        MainActivity mainActivity = getActivity();
        ImageView imageApp = (ImageView) mainActivity.findViewById(R.id.imageApp);
        assertNotNull(imageApp);
    }
    public void testKingsLoginEditTextExists(){
        MainActivity mainActivity = getActivity();
        EditText txtKingsLogin = (EditText) mainActivity.findViewById(R.id.txtKingsLogin);
        assertNotNull(txtKingsLogin);
    }
    public void testPasswordEditTextExists(){
        MainActivity mainActivity = getActivity();
        EditText txtPassword = (EditText) mainActivity.findViewById(R.id.txtPassword);
        assertNotNull(txtPassword);
    }

    public void testLoginButtonExists(){
        MainActivity mainActivity = getActivity();
        Button btnLogin = (Button) mainActivity.findViewById(R.id.btnLogin);
        assertNotNull(btnLogin);
    }

    public void testRegisterButtonExists(){
        MainActivity mainActivity = getActivity();
        Button btnRegister = (Button) mainActivity.findViewById(R.id.btnRegister);
        assertNotNull(btnRegister);
    }


}