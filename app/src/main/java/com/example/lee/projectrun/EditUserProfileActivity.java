package com.example.lee.projectrun;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;

public class EditUserProfileActivity extends AppCompatActivity {


    private EditText txtEditFirstName, txtEditLastName;
    private EditText txtEditEmailAddress;
    private EditText txtEditPassword;
    private EditText txtEditAge;
    private EditText txtEditPersonalInterest;
    private RadioButton radioEditBtnMale, radioEditBtnFemale;
    private RadioGroup radioEditGroupGender;
    private Spinner spinnerEditTeachingLanguage, spinnerEditTeachingLanguageLevel;
    private Spinner spinnerEditPracticeLanguage, spinnerEditPracticeLanguageLevel;
    private Button btnEditImage, btnEditConfirm;
    private ImageView imgEditView;
    private UserInformation userInformation;
    private static final int RESULT_LOAD_IMAGE = 1;
    private String[] arrayEditListLanguages = {"Select a Language", "Arabic", "Bengali", "Dutch", "English", "French",
            "German", "Greek", "Gujarati", "Hebrew",
            "Hindi", "Italian", "Japanese", "Korean",
            "Mandarin", "Punjabi", "Persian (Farsi)", "Polish",
            "Portuguese (Brazilian)", "Portuguese(European)", "Portuguese via Spanish", "Russian",
            "Spanish", "Swedish", "Turkish", "Urdu"};
    private String[] arrayEditLanguageLevel = {"Select a Level", "A1", "A2", "B1", "B2", "C1", "C2"};
    private String stringGender, stringImage, stringTeachingLanguage, stringTeachingLanguageLevel;
    private String stringPracticeLanguage, stringPracticeLanguageLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        Intent intent = getIntent();
        userInformation = (UserInformation) intent.getSerializableExtra("userinfo");

        ArrayAdapter<String> adapterLanguages = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, arrayEditListLanguages);
        ArrayAdapter<String> adapterSkill = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, arrayEditLanguageLevel);

        imgEditView = (ImageView) findViewById(R.id.imgEditView);


        btnEditImage = (Button) findViewById(R.id.btnEditImage);
        btnEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, RESULT_LOAD_IMAGE);
            }
        });
        txtEditFirstName = (EditText) findViewById(R.id.txtEditFirstName);
        txtEditLastName = (EditText) findViewById(R.id.txtEditLastName);
        txtEditEmailAddress = (EditText) findViewById(R.id.txtEditEmailAddress);
        txtEditPassword = (EditText) findViewById(R.id.txtEditPassword);
        radioEditGroupGender = (RadioGroup) findViewById(R.id.radioEditGroupGender);
        radioEditBtnMale = (RadioButton) findViewById(R.id.radioEditBtnMale);
        radioEditBtnFemale = (RadioButton) findViewById(R.id.radioEditBtnFemale);
        txtEditAge = (EditText) findViewById(R.id.txtEditAge);
        spinnerEditTeachingLanguage = (Spinner) findViewById(R.id.spinnerEditTeaching1);
        spinnerEditTeachingLanguageLevel = (Spinner) findViewById(R.id.spinnerEditTeaching1Level);
        spinnerEditTeachingLanguage.setAdapter(adapterLanguages);
        spinnerEditTeachingLanguageLevel.setAdapter(adapterSkill);
        spinnerEditPracticeLanguage = (Spinner) findViewById(R.id.spinnerEditPractice1);
        spinnerEditPracticeLanguageLevel = (Spinner) findViewById(R.id.spinnerEditPractice1Level);
        spinnerEditPracticeLanguage.setAdapter(adapterLanguages);
        spinnerEditPracticeLanguageLevel.setAdapter(adapterSkill);

        stringTeachingLanguage = spinnerEditTeachingLanguage.getSelectedItem().toString();
        stringTeachingLanguageLevel = spinnerEditTeachingLanguageLevel.getSelectedItem().toString();
        stringPracticeLanguage = spinnerEditPracticeLanguage.getSelectedItem().toString();
        stringPracticeLanguageLevel = spinnerEditPracticeLanguageLevel.getSelectedItem().toString();

        txtEditPersonalInterest = (EditText) findViewById(R.id.txtEditPersonalInterests);
        btnEditConfirm = (Button) findViewById(R.id.btnEditConfirm);

        byte[] decodedString = Base64.decode(userInformation.getImageString(), 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        imgEditView.setImageBitmap(decodedByte);
        txtEditFirstName.setText(userInformation.getFirstName());
        txtEditLastName.setText(userInformation.getLastName());
        txtEditEmailAddress.setText(userInformation.getEmail());
        txtEditPassword.setText(userInformation.getPassword());
        txtEditAge.setText(userInformation.getAge());
        txtEditPersonalInterest.setText(userInformation.getPersonalInterests());
        if (userInformation.getGender().equals("Male")) {
            radioEditBtnMale.setChecked(true);
        } else {
            radioEditBtnFemale.setChecked(false);
        }

        String[] teachingLanguageArray = userInformation.getTeachingLanguage();
        final String stringTeachingLanguage = teachingLanguageArray[0];
        final String stringTeachingLanguageLevel = teachingLanguageArray[1];
        spinnerEditTeachingLanguage.setSelection(((ArrayAdapter<String>) spinnerEditTeachingLanguage.getAdapter()).getPosition(stringTeachingLanguage));
        spinnerEditTeachingLanguageLevel.setSelection(((ArrayAdapter<String>) spinnerEditTeachingLanguageLevel.getAdapter()).getPosition(stringTeachingLanguageLevel));

        String[] practiceLanguageArray = userInformation.getPracticeLanguage();
        final String stringPracticeLanguage = practiceLanguageArray[0];
        final String stringPracticeLanguageLevel = practiceLanguageArray[1];
        spinnerEditPracticeLanguage.setSelection(((ArrayAdapter<String>) spinnerEditPracticeLanguage.getAdapter()).getPosition(stringPracticeLanguage));
        spinnerEditPracticeLanguageLevel.setSelection(((ArrayAdapter<String>) spinnerEditPracticeLanguageLevel.getAdapter()).getPosition(stringPracticeLanguageLevel));

        btnEditConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInformation.updateFirstName(txtEditFirstName.getText().toString());
                userInformation.updateLastName(txtEditLastName.getText().toString());
                userInformation.updatePassword(txtEditPassword.getText().toString());
                stringGender = ((RadioButton) findViewById(radioEditGroupGender.getCheckedRadioButtonId())).getText().toString();
                userInformation.updateGender(stringGender);
                userInformation.updateAge(txtEditAge.getText().toString());
                if(stringTeachingLanguage.equals(spinnerEditTeachingLanguage.getSelectedItem().toString())){
                    //do nothing
                }else{
                    userInformation.updateTeaching(stringTeachingLanguage);
                }
                if(stringTeachingLanguageLevel.equals(spinnerEditTeachingLanguageLevel.getSelectedItem().toString())){
                    //do nothing
                }else{
                    userInformation.updateTeaching(stringTeachingLanguageLevel);
                }
                if(stringPracticeLanguage.equals(spinnerEditPracticeLanguage.getSelectedItem().toString())){
                    //do nothing
                }else{
                    userInformation.updatePractice(stringPracticeLanguage);
                }
                if(stringPracticeLanguageLevel.equals(spinnerEditPracticeLanguageLevel.getSelectedItem().toString())){
                    //do nothing
                }else{
                    userInformation.updatePractice(stringPracticeLanguageLevel);
                }
                userInformation.updatePersonal(txtEditPersonalInterest.getText().toString());

                BitmapDrawable drawable = (BitmapDrawable) imgEditView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] bb = bos.toByteArray();
                stringImage = Base64.encodeToString(bb, 0);

                userInformation.updateImage(stringImage);

                userInformation.updateStudent(EditUserProfileActivity.this);

                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent.putExtra("userinfo", userInformation);
                startActivity(intent);

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imgEditView.setImageURI(selectedImage);
        }
    }

}
