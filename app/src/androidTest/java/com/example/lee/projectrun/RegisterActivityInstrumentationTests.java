package com.example.lee.projectrun;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

    public void testFullNameEditTextExists(){
        RegisterActivity registerActivity = getActivity();
        EditText txtFullName = (EditText) registerActivity.findViewById(R.id.txtFullName);
        assertNotNull(txtFullName);
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

    public void testRegisterGenderTextViewExists(){
        RegisterActivity registerActivity = getActivity();
        TextView txtGender = (TextView) registerActivity.findViewById(R.id.txtGender);
        assertNotNull(txtGender);
    }

    public void testRegisterGenderRadioGroupExists(){
        RegisterActivity registerActivity = getActivity();
        RadioGroup radioGroupGender = (RadioGroup) registerActivity.findViewById(R.id.radioGroupGender);
        assertNotNull(radioGroupGender);
    }

    public void testRegisterFemaleGenderRadioButtonExists(){
        RegisterActivity registerActivity = getActivity();
        RadioButton radioBtnFemale = (RadioButton) registerActivity.findViewById(R.id.radioBtnFemale);
        assertNotNull(radioBtnFemale);
    }

    public void testRegisterMaleGenderRadioButtonExists(){
        RegisterActivity registerActivity = getActivity();
        RadioButton radioBtnMale = (RadioButton) registerActivity.findViewById(R.id.radioBtnMale);
        assertNotNull(radioBtnMale);
    }

    public void testRegisterTeachingLanguageEditTextExists(){
        RegisterActivity registerActivity = getActivity();
        EditText txtTeachingLanguage = (EditText) registerActivity.findViewById(R.id.txtTeachingLanguage);
        assertNotNull(txtTeachingLanguage);
    }

    public void testRegisterPracticeLanguageEditTextExists(){
        RegisterActivity registerActivity = getActivity();
        EditText txtPracticeLanguage = (EditText) registerActivity.findViewById(R.id.txtPracticeLanguage);
        assertNotNull(txtPracticeLanguage);
    }

    public void testRegisterPersonalInterestsEditTextExists(){
        RegisterActivity registerActivity = getActivity();
        EditText txtPersonalInterests = (EditText) registerActivity.findViewById(R.id.txtPersonalInterests);
        assertNotNull(txtPersonalInterests);
    }

    public void testConfirmRegisterButtonExists(){
        RegisterActivity registerActivity = getActivity();
        Button btnConfirmRegister = (Button) registerActivity.findViewById(R.id.btnConfirmRegister);
        assertNotNull(btnConfirmRegister);
    }

    public void testRegisteringAUser(){
        RegisterActivity registerActivity = getActivity();
        final EditText txtFullName = (EditText) registerActivity.findViewById(R.id.txtFullName);
        final EditText txtRegisterKingsID = (EditText) registerActivity.findViewById(R.id.txtRegisterKingsID);
        final EditText txtEmailAddress = (EditText) registerActivity.findViewById(R.id.txtEmailAddress);
        final EditText txtRegisterPassword = (EditText) registerActivity.findViewById(R.id.txtRegisterPassword);
        final EditText txtRegisterConfirmPassword= (EditText) registerActivity.findViewById(R.id.txtRegisterConfirmPassword);
        final RadioButton radioBtnMale = (RadioButton) registerActivity.findViewById(R.id.radioBtnMale);
        final EditText txtTeachingLanguage = (EditText) registerActivity.findViewById(R.id.txtTeachingLanguage);
        final EditText txtPracticeLanguage = (EditText) registerActivity.findViewById(R.id.txtPracticeLanguage);
        final EditText txtPersonalInterests = (EditText) registerActivity.findViewById(R.id.txtPersonalInterests);
        final Button btnConfirmRegister = (Button) registerActivity.findViewById(R.id.btnConfirmRegister);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtFullName.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("John Doe");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtRegisterKingsID.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("K1461984");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtEmailAddress.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("mahesh.bhudia@kcl.ac.uk");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtRegisterPassword.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("testing123");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtRegisterConfirmPassword.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("testing123");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                radioBtnMale.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_ENTER);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtTeachingLanguage.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("English");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtPracticeLanguage.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("French");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtPersonalInterests.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Interested in Testing");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                btnConfirmRegister.requestFocus();
                btnConfirmRegister.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
    }
}
