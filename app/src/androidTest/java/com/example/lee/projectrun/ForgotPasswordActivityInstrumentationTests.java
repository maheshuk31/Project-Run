package com.example.lee.projectrun;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by mahesh on 22/02/2016.
 */
public class ForgotPasswordActivityInstrumentationTests extends ActivityInstrumentationTestCase2<ForgotPasswordActivity> {

    public ForgotPasswordActivityInstrumentationTests() {
        super(ForgotPasswordActivity.class);
    }

    public void testRegisterActivityExists(){
        ForgotPasswordActivity forgotPasswordActivity = getActivity();
        assertNotNull(forgotPasswordActivity);
    }

    public void testForgetPasswordTitleTextViewExists(){
        ForgotPasswordActivity forgotPasswordActivity = getActivity();
        TextView txtForgotPasswordTitle = (TextView) forgotPasswordActivity.findViewById(R.id.txtForgotPasswordTitle);
        assertNotNull(txtForgotPasswordTitle);
    }

    public void testForgetPasswordFieldEditTextExists(){
        ForgotPasswordActivity forgotPasswordActivity = getActivity();
        EditText txtForgotPassword = (EditText) forgotPasswordActivity.findViewById(R.id.txtForgotPassword);
        assertNotNull(txtForgotPassword);
    }

    public void testForgetSubmitButtonExists(){
        ForgotPasswordActivity forgotPasswordActivity = getActivity();
        Button btnForgotSubmit = (Button) forgotPasswordActivity.findViewById(R.id.btnForgotSubmit);
        assertNotNull(btnForgotSubmit);
    }

    public void testForgetCancelButtonExists(){
        ForgotPasswordActivity forgotPasswordActivity = getActivity();
        Button btnForgotCancel = (Button) forgotPasswordActivity.findViewById(R.id.btnForgotCancel);
        assertNotNull(btnForgotCancel);
    }

    /*
    *These tests below do not work currently, then end up on an infinite loop as the activity doesn't
    * revert back causing it to not be able to continue, need to fix
    */

//    public void testInputtingForgettingPasswordViaEmailScenario1StartingWithK(){
//        ForgotPasswordActivity forgotPasswordActivity = getActivity();
//        final EditText txtForgotPassword = (EditText) forgotPasswordActivity.findViewById(R.id.txtForgotPassword);
//        final Button btnForgotSubmit = (Button) forgotPasswordActivity.findViewById(R.id.btnForgotSubmit);
//        getInstrumentation().runOnMainSync(new Runnable() {
//            @Override
//            public void run() {
//                txtForgotPassword.requestFocus();
//            }
//        });
//        getInstrumentation().waitForIdleSync();
//        getInstrumentation().sendStringSync("k1461984@kcl.ac.uk");
//        getInstrumentation().runOnMainSync(new Runnable() {
//            @Override
//            public void run() {
//                btnForgotSubmit.requestFocus();
//                btnForgotSubmit.performClick();
//            }
//        });
//        getInstrumentation().waitForIdleSync();
//
//        getInstrumentation().callActivityOnRestart(forgotPasswordActivity);
//    }
//
//    public void testInputtingForgettingPasswordViaEmailScenario2FirstAndLastNames(){
//        ForgotPasswordActivity forgotPasswordActivity = getActivity();
//        final EditText txtForgotPassword = (EditText) forgotPasswordActivity.findViewById(R.id.txtForgotPassword);
//        final Button btnForgotSubmit = (Button) forgotPasswordActivity.findViewById(R.id.btnForgotSubmit);
//        getInstrumentation().runOnMainSync(new Runnable() {
//            @Override
//            public void run() {
//                txtForgotPassword.requestFocus();
//            }
//        });
//        getInstrumentation().waitForIdleSync();
//        getInstrumentation().sendStringSync("mahesh.bhudia@kcl.ac.uk");
//        getInstrumentation().runOnMainSync(new Runnable() {
//            @Override
//            public void run() {
//                btnForgotSubmit.requestFocus();
//                btnForgotSubmit.performClick();
//            }
//        });
//        getInstrumentation().waitForIdleSync();
//
//        getInstrumentation().callActivityOnRestart(forgotPasswordActivity);
//    }
//
//    public void testInputtingForgettingPasswordViaKingsID(){
//        ForgotPasswordActivity forgotPasswordActivity = getActivity();
//        final EditText txtForgotPassword = (EditText) forgotPasswordActivity.findViewById(R.id.txtForgotPassword);
//        final Button btnForgotSubmit = (Button) forgotPasswordActivity.findViewById(R.id.btnForgotSubmit);
//        getInstrumentation().runOnMainSync(new Runnable() {
//            @Override
//            public void run() {
//                txtForgotPassword.requestFocus();
//            }
//        });
//        getInstrumentation().waitForIdleSync();
//        getInstrumentation().sendStringSync("k1461984");
//        getInstrumentation().runOnMainSync(new Runnable() {
//            @Override
//            public void run() {
//                btnForgotSubmit.requestFocus();
//                btnForgotSubmit.performClick();
//            }
//        });
//        getInstrumentation().waitForIdleSync();
//
//        getInstrumentation().callActivityOnRestart(forgotPasswordActivity);
//    }
//
//    public void testCancelling(){
//        final ForgotPasswordActivity forgotPasswordActivity = getActivity();
//        final Button btnForgotCancel = (Button) forgotPasswordActivity.findViewById(R.id.btnForgotCancel);
//
//        getInstrumentation().runOnMainSync(new Runnable() {
//            @Override
//            public void run() {
//                btnForgotCancel.requestFocus();
//                btnForgotCancel.performClick();
//            }
//        });
//        getInstrumentation().waitForIdleSync();
//    }
}
