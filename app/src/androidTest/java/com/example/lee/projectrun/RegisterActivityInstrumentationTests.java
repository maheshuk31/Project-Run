package com.example.lee.projectrun;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by mahesh on 19/02/2016.
 */
public class RegisterActivityInstrumentationTests extends ActivityInstrumentationTestCase2<RegisterActivity> {
    public RegisterActivityInstrumentationTests() {
        super(RegisterActivity.class);
    }

    public void testRegisterActivityExists(){
        RegisterActivity registerActivity = getActivity();
        assertNotNull(registerActivity);
    }

    public void testFirstNameEditTextExists(){
        RegisterActivity registerActivity = getActivity();
        EditText txtFirstName = (EditText) registerActivity.findViewById(R.id.txtFirstName);
        assertNotNull(txtFirstName);
    }

    public void testLastNameEditTextExists(){
        RegisterActivity registerActivity = getActivity();
        EditText txtLastName = (EditText) registerActivity.findViewById(R.id.txtLastName);
        assertNotNull(txtLastName);
    }

    public void testRegisterKingsIDEditTextExists(){
        RegisterActivity registerActivity = getActivity();
        EditText txtRegisterKingsID = (EditText) registerActivity.findViewById(R.id.txtRegisterKingsID);
        assertNotNull(txtRegisterKingsID);
    }

    public void testEmailEditTextExists(){
        RegisterActivity registerActivity = getActivity();
        EditText txtEmailAddress = (EditText) registerActivity.findViewById(R.id.txtEmailAddress);
        assertNotNull(txtEmailAddress);
    }

    public void testRegisterPasswordEditTextExists(){
        RegisterActivity registerActivity = getActivity();
        EditText txtRegisterPassword = (EditText) registerActivity.findViewById(R.id.txtRegisterPassword);
        assertNotNull(txtRegisterPassword);
    }

    public void testRegisterConfirmPasswordEditTextExists(){
        RegisterActivity registerActivity = getActivity();
        EditText txtRegisterConfirmPassword= (EditText) registerActivity.findViewById(R.id.txtRegisterConfirmPassword);
        assertNotNull(txtRegisterConfirmPassword);
    }

    public void testConfirmRegisterButtonExists(){
        RegisterActivity registerActivity = getActivity();
        Button btnConfirmRegister = (Button) registerActivity.findViewById(R.id.btnConfirmRegister);
        assertNotNull(btnConfirmRegister);
    }


}
