package com.example.lee.projectrun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtFirstName, txtLastName;
    private EditText txtRegisterKingsID;
    private EditText txtEmailAddress;
    private EditText txtRegisterPassword;
    private EditText txtRegisterConfirmPassword;
    private RadioButton radioBtnMale, radioBtnFemale;
    private String stringKingsID, stringEmail, stringPassword, stringConfirmPassword;
    private String stringFirstName, stringLastName;
    private Spinner spinnerTeaching1, spinnerTeaching1Level, spinnerTeaching2, spinnerTeaching2Level,
            spinnerTeaching3, spinnerTeaching3Level, spinnerTeaching4, spinnerTeaching4Level;
    private Spinner spinnerPractice1, spinnerPractice1Level, spinnerPractice2, spinnerPractice2Level,
            spinnerPractice3, spinnerPractice3Level, spinnerPractice4, spinnerPractice4Level;
    private Button btnConfirmRegister;
    private Boolean booleanFirstName = true;
    private Boolean booleanLastName = true;
    private Boolean booleanKingsID = true;
    private Boolean booleanEmail = true;
    private Boolean booleanPassword = true;
    private Boolean booleanConfirmPassword = true;
    private Boolean booleanGenderSelected = true;
    private static String letterList = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Random random = new Random();
    private String[] arrayListLanguages = {"Select a Language", "Arabic", "Bengali", "Dutch", "French",
            "German", "Greek", "Gujarati", "Hebrew",
            "Hindi", "Italian", "Japanese", "Korean",
            "Mandarin", "Punjabi", "Persian (Farsi)", "Polish",
            "Portuguese (Brazilian)", "Portuguese(European)", "Portuguese via Spanish", "Russian",
            "Spanish", "Swedish", "Turkish", "Urdu"};
    private String[] arrayLanguageLevel = {"Select a Skill", "A1", "A2", "B1", "B2", "C1", "C2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
        radioBtnMale = (RadioButton) findViewById(R.id.radioBtnMale);
        radioBtnFemale = (RadioButton) findViewById(R.id.radioBtnFemale);

        spinnerTeaching1 = (Spinner) findViewById(R.id.spinnerTeaching1);
        spinnerTeaching1Level = (Spinner) findViewById(R.id.spinnerTeaching1Level);
        spinnerTeaching2 = (Spinner) findViewById(R.id.spinnerTeaching2);
        spinnerTeaching2Level = (Spinner) findViewById(R.id.spinnerTeaching2Level);
        spinnerTeaching3 = (Spinner) findViewById(R.id.spinnerTeaching3);
        spinnerTeaching3Level = (Spinner) findViewById(R.id.spinnerTeaching3Level);
        spinnerTeaching4 = (Spinner) findViewById(R.id.spinnerTeaching4);
        spinnerTeaching4Level = (Spinner) findViewById(R.id.spinnerTeaching4Level);
        spinnerTeaching1.setAdapter(adapterLanguages);
        spinnerTeaching1Level.setAdapter(adapterSkill);
        spinnerTeaching2.setAdapter(adapterLanguages);
        spinnerTeaching2Level.setAdapter(adapterSkill);
        spinnerTeaching3.setAdapter(adapterLanguages);
        spinnerTeaching3Level.setAdapter(adapterSkill);
        spinnerTeaching4.setAdapter(adapterLanguages);
        spinnerTeaching4Level.setAdapter(adapterSkill);

        spinnerPractice1 = (Spinner) findViewById(R.id.spinnerPractice1);
        spinnerPractice1Level = (Spinner) findViewById(R.id.spinnerPractice1Level);
        spinnerPractice2 = (Spinner) findViewById(R.id.spinnerPractice2);
        spinnerPractice2Level = (Spinner) findViewById(R.id.spinnerPractice2Level);
        spinnerPractice3 = (Spinner) findViewById(R.id.spinnerPractice3);
        spinnerPractice3Level = (Spinner) findViewById(R.id.spinnerPractice3Level);
        spinnerPractice4 = (Spinner) findViewById(R.id.spinnerPractice4);
        spinnerPractice4Level = (Spinner) findViewById(R.id.spinnerPractice4Level);
        spinnerPractice1.setAdapter(adapterLanguages);
        spinnerPractice1Level.setAdapter(adapterSkill);
        spinnerPractice2.setAdapter(adapterLanguages);
        spinnerPractice2Level.setAdapter(adapterSkill);
        spinnerPractice3.setAdapter(adapterLanguages);
        spinnerPractice3Level.setAdapter(adapterSkill);
        spinnerPractice4.setAdapter(adapterLanguages);
        spinnerPractice4Level.setAdapter(adapterSkill);

        btnConfirmRegister.setOnClickListener(this);
    }

    /**
     * Links with EmailSender Class to send an email with the user's email and corresponding email.
     * Code generator method is used, any amount of letters that the code has to have can be changed.
     */
    private void emailSend() {
        stringFirstName = txtFirstName.getText().toString().trim();
        stringLastName = txtLastName.getText().toString().trim();
        stringEmail = txtEmailAddress.getText().toString().trim();
        String subject = "One Final Step, Please Register";
        String message =
                "Welcome " + stringFirstName + " " + stringLastName + "," + "\n" + "\n"
                        + "Thank you for registering for the Tandem Learning App to complete your registration please use the following code " + "\n" + "\n"
                        + setCodeHolder(6) + "\n" + "\n"
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

        if (TextUtils.isEmpty(stringFirstName)) {
            booleanFirstName = false;
            txtFirstName.setError("Please Enter Your First Name");
            return;
        } else {
            booleanFirstName = true;
        }
        if (TextUtils.isEmpty(stringLastName)) {
            booleanLastName = false;
            txtLastName.setError("Please Enter Your Last Name");
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

        if (booleanFirstName == true && booleanLastName && booleanKingsID == true && booleanEmail == true &&
                booleanPassword == true && booleanConfirmPassword == true &&
                booleanGenderSelected == true) {
            emailSend();
            //database code should go here
            //checkingSpinner();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }


        if(v == btnConfirmRegister)
        {
            AddUser();
        }




    }





    private void AddUser()
    {
        final String ID = txtRegisterKingsID.getText().toString().trim();
        final String FirstName = txtFirstName.getText().toString().trim();
        final String Email = txtEmailAddress.getText().toString().trim();

        class AddUser extends AsyncTask<Void, Void, String>
        {
            ProgressDialog Loading;


            protected void OnPreExecute(String S)
            {
                super.onPreExecute();
                Loading = ProgressDialog.show(RegisterActivity.this, "Adding...", "Wait...", false, false);
            }



            protected void OnPostExecute(String S)
            {
                super.onPostExecute(S);
                Loading.dismiss();
                Toast.makeText(RegisterActivity.this, S, Toast.LENGTH_LONG).show();
            }



            protected String doInBackground(Void... V)
            {
                HashMap<String, String> Params = new HashMap<>();
                Params.put(Config.Key_ID, ID);
                Params.put(Config.Key_Name, FirstName);
                Params.put(Config.Key_Email, Email);

                RequestHandler RH = new RequestHandler();
                String Res = RH.SendPostRequest(Config.URL_AddUser, Params);
                return Res;
            }
        }


        AddUser AU = new AddUser();
        AU.execute();
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

    //trying to make a method that ignores the first selection in spinner currently not working
//    private void checkingSpinner() {
//
//        spinnerTeaching1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerTeaching1.getSelectedItem() == "Select a Language") {
//                    //set to null
//                }
//            }
//        });
//        spinnerTeaching1Level.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerTeaching1Level.getSelectedItem() == "Select a Skill") {
//                    //set to null
//                }
//            }
//        });
//        spinnerTeaching2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerTeaching2.getSelectedItem() == "Select a Language") {
//                    //set to null
//                }
//            }
//        });
//        spinnerTeaching2Level.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerTeaching2Level.getSelectedItem() == "Select a Skill") {
//                    //set to null
//                }
//            }
//        });
//        spinnerTeaching3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerTeaching3.getSelectedItem() == "Select a Language") {
//                    //set to null
//                }
//            }
//        });
//        spinnerTeaching3Level.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerTeaching3Level.getSelectedItem() == "Select a Skill") {
//                    //set to null
//                }
//            }
//        });
//        spinnerTeaching4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerTeaching4.getSelectedItem() == "Select a Language") {
//                    //set to null
//                }
//            }
//        });
//        spinnerTeaching4Level.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerTeaching4Level.getSelectedItem() == "Select a Skill") {
//                    //set to null
//                }
//            }
//        });
//
//        spinnerPractice1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerPractice1.getSelectedItem() == "Select a Language") {
//                    //set to null
//                }
//            }
//        });
//        spinnerPractice1Level.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerPractice1Level.getSelectedItem() == "Select a Skill") {
//                    //set to null
//                }
//            }
//        });
//        spinnerPractice2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerPractice2.getSelectedItem() == "Select a Language") {
//                    //set to null
//                }
//            }
//        });
//        spinnerPractice2Level.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerPractice2Level.getSelectedItem() == "Select a Skill") {
//                    //set to null
//                }
//            }
//        });
//        spinnerPractice3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerPractice3.getSelectedItem() == "Select a Language") {
//                    //set to null
//                }
//            }
//        });
//        spinnerPractice3Level.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerPractice3Level.getSelectedItem() == "Select a Skill") {
//                    //set to null
//                }
//            }
//        });
//        spinnerPractice4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerPractice4.getSelectedItem() == "Select a Language") {
//                    //set to null
//                }
//            }
//        });
//        spinnerPractice4Level.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerPractice4Level.getSelectedItem() == "Select a Skill") {
//                    //set to null
//                }
//            }
//        });
//
//    }

}
