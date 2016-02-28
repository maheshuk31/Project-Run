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

    public void testRegisterActivityExists() {
        RegisterActivity registerActivity = getActivity();
        assertNotNull(registerActivity);
    }

    public void testFirstNameEditTextExists() {
        RegisterActivity registerActivity = getActivity();
        EditText txtFirstName = (EditText) registerActivity.findViewById(R.id.txtFirstName);
        assertNotNull(txtFirstName);
    }

    public void testLastNameEditTextExists() {
        RegisterActivity registerActivity = getActivity();
        EditText txtLastName = (EditText) registerActivity.findViewById(R.id.txtLastName);
        assertNotNull(txtLastName);
    }

    public void testRegisterKingsIDEditTextExists() {
        RegisterActivity registerActivity = getActivity();
        EditText txtRegisterKingsID = (EditText) registerActivity.findViewById(R.id.txtRegisterKingsID);
        assertNotNull(txtRegisterKingsID);
    }

    public void testEmailEditTextExists() {
        RegisterActivity registerActivity = getActivity();
        EditText txtEmailAddress = (EditText) registerActivity.findViewById(R.id.txtEmailAddress);
        assertNotNull(txtEmailAddress);
    }

    public void testRegisterPasswordEditTextExists() {
        RegisterActivity registerActivity = getActivity();
        EditText txtRegisterPassword = (EditText) registerActivity.findViewById(R.id.txtRegisterPassword);
        assertNotNull(txtRegisterPassword);
    }

    public void testRegisterConfirmPasswordEditTextExists() {
        RegisterActivity registerActivity = getActivity();
        EditText txtRegisterConfirmPassword = (EditText) registerActivity.findViewById(R.id.txtRegisterConfirmPassword);
        assertNotNull(txtRegisterConfirmPassword);
    }

    public void testRegisterGenderTextViewExists() {
        RegisterActivity registerActivity = getActivity();
        TextView txtGender = (TextView) registerActivity.findViewById(R.id.txtGender);
        assertNotNull(txtGender);
    }

    public void testRegisterGenderRadioGroupExists() {
        RegisterActivity registerActivity = getActivity();
        RadioGroup radioGroupGender = (RadioGroup) registerActivity.findViewById(R.id.radioGroupGender);
        assertNotNull(radioGroupGender);
    }

    public void testRegisterFemaleGenderRadioButtonExists() {
        RegisterActivity registerActivity = getActivity();
        RadioButton radioBtnFemale = (RadioButton) registerActivity.findViewById(R.id.radioBtnFemale);
        assertNotNull(radioBtnFemale);
    }

    public void testRegisterMaleGenderRadioButtonExists() {
        RegisterActivity registerActivity = getActivity();
        RadioButton radioBtnMale = (RadioButton) registerActivity.findViewById(R.id.radioBtnMale);
        assertNotNull(radioBtnMale);
    }

    public void testRegisterPersonalInterestsEditTextExists() {
        RegisterActivity registerActivity = getActivity();
        EditText txtPersonalInterests = (EditText) registerActivity.findViewById(R.id.txtPersonalInterests);
        assertNotNull(txtPersonalInterests);
    }

    public void testConfirmRegisterButtonExists() {
        RegisterActivity registerActivity = getActivity();
        Button btnConfirmRegister = (Button) registerActivity.findViewById(R.id.btnConfirmRegister);
        assertNotNull(btnConfirmRegister);
    }

    public void testRegisteringAUser() {
        RegisterActivity registerActivity = getActivity();
        final EditText txtFirstName = (EditText) registerActivity.findViewById(R.id.txtFirstName);
        final EditText txtLastName = (EditText) registerActivity.findViewById(R.id.txtLastName);
        final EditText txtRegisterKingsID = (EditText) registerActivity.findViewById(R.id.txtRegisterKingsID);
        final EditText txtEmailAddress = (EditText) registerActivity.findViewById(R.id.txtEmailAddress);
        final EditText txtRegisterPassword = (EditText) registerActivity.findViewById(R.id.txtRegisterPassword);
        final EditText txtRegisterConfirmPassword = (EditText) registerActivity.findViewById(R.id.txtRegisterConfirmPassword);
        final RadioButton radioBtnMale = (RadioButton) registerActivity.findViewById(R.id.radioBtnMale);
        final EditText txtPersonalInterests = (EditText) registerActivity.findViewById(R.id.txtPersonalInterests);
        final Button btnConfirmRegister = (Button) registerActivity.findViewById(R.id.btnConfirmRegister);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtFirstName.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("John");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtLastName.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Doe");
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

        //spinner selecting

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
