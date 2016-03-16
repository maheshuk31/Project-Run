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
    private Spinner spinnerEditTeachingLanguage1, spinnerEditTeachingLanguageLevel1;
    private Spinner spinnerEditPracticeLanguage1, spinnerEditPracticeLanguageLevel1;
    private Spinner spinnerEditTeachingLanguage2, spinnerEditTeachingLanguageLevel2;
    private Spinner spinnerEditPracticeLanguage2, spinnerEditPracticeLanguageLevel2;
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
    private String stringGender, stringImage;
    private String stringOldTeaching1, stringOldTeachingLevel1;
    private String stringOldTeaching2, stringOldTeachingLevel2;
    private String stringOldPractice1, stringOldPraticeLevel1;
    private String stringOldPractice2, stringOldPraticeLevel2;

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
        spinnerEditTeachingLanguage1 = (Spinner) findViewById(R.id.spinnerEditTeaching1);
        spinnerEditTeachingLanguageLevel1 = (Spinner) findViewById(R.id.spinnerEditTeaching1Level);
        spinnerEditTeachingLanguage2 = (Spinner) findViewById(R.id.spinnerEditTeaching2);
        spinnerEditTeachingLanguageLevel2 = (Spinner) findViewById(R.id.spinnerEditTeaching2Level);
        spinnerEditTeachingLanguage1.setAdapter(adapterLanguages);
        spinnerEditTeachingLanguageLevel1.setAdapter(adapterSkill);
        spinnerEditTeachingLanguage2.setAdapter(adapterLanguages);
        spinnerEditTeachingLanguageLevel2.setAdapter(adapterSkill);
        spinnerEditPracticeLanguage1 = (Spinner) findViewById(R.id.spinnerEditPractice1);
        spinnerEditPracticeLanguageLevel1 = (Spinner) findViewById(R.id.spinnerEditPractice1Level);
        spinnerEditPracticeLanguage2 = (Spinner) findViewById(R.id.spinnerEditPractice2);
        spinnerEditPracticeLanguageLevel2 = (Spinner) findViewById(R.id.spinnerEditPractice2Level);
        spinnerEditPracticeLanguage1.setAdapter(adapterLanguages);
        spinnerEditPracticeLanguageLevel1.setAdapter(adapterSkill);
        spinnerEditPracticeLanguage2.setAdapter(adapterLanguages);
        spinnerEditPracticeLanguageLevel2.setAdapter(adapterSkill);

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


        try{
            String stringTeachingLanguageArray1 = teachingLanguageArray[0];
            spinnerEditTeachingLanguage1.setSelection(((ArrayAdapter<String>) spinnerEditTeachingLanguage1.getAdapter()).getPosition(stringTeachingLanguageArray1));
            stringOldTeaching1 = spinnerEditTeachingLanguage1.getSelectedItem().toString();
        }
        catch(Exception e){
            spinnerEditTeachingLanguage1.setSelection(0);
        }

        try{
            String stringTeachingLanguageLevelArray1 = teachingLanguageArray[1];
            spinnerEditTeachingLanguageLevel1.setSelection(((ArrayAdapter<String>) spinnerEditTeachingLanguageLevel1.getAdapter()).getPosition(stringTeachingLanguageLevelArray1));
            stringOldTeachingLevel1 = spinnerEditTeachingLanguageLevel1.getSelectedItem().toString();
        }
        catch(Exception e){
            spinnerEditTeachingLanguageLevel1.setSelection(0);
        }

        try{
            String stringTeachingLanguageArray2 = teachingLanguageArray[2];
            spinnerEditTeachingLanguage2.setSelection(((ArrayAdapter<String>) spinnerEditTeachingLanguage2.getAdapter()).getPosition(stringTeachingLanguageArray2));
            stringOldTeaching2 = spinnerEditTeachingLanguage2.getSelectedItem().toString();
        }catch(Exception e){
            spinnerEditTeachingLanguage2.setSelection(0);
        }

        try{
            String stringTeachingLanguageLevelArray2 = teachingLanguageArray[3];
            spinnerEditTeachingLanguageLevel2.setSelection(((ArrayAdapter<String>) spinnerEditTeachingLanguageLevel2.getAdapter()).getPosition(stringTeachingLanguageLevelArray2));
            stringOldTeachingLevel2 = spinnerEditTeachingLanguageLevel2.getSelectedItem().toString();
        }catch(Exception e){
            spinnerEditTeachingLanguageLevel2.setSelection(0);
        }


        String[] practiceLanguageArray = userInformation.getPracticeLanguage();
        String stringPracticeLanguageArray = practiceLanguageArray[0];
        String stringPracticeLanguageLevelArray = practiceLanguageArray[1];
        spinnerEditPracticeLanguage1.setSelection(((ArrayAdapter<String>) spinnerEditPracticeLanguage1.getAdapter()).getPosition(stringPracticeLanguageArray));
        spinnerEditPracticeLanguageLevel1.setSelection(((ArrayAdapter<String>) spinnerEditPracticeLanguageLevel1.getAdapter()).getPosition(stringPracticeLanguageLevelArray));

        btnEditConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stringTeachingLanguage1 = spinnerEditTeachingLanguage1.getSelectedItem().toString();
                String stringTeachingLanguageLevel1 = spinnerEditTeachingLanguageLevel1.getSelectedItem().toString();
                String stringTeachingLanguage2 = spinnerEditTeachingLanguage2.getSelectedItem().toString();
                String stringTeachingLanguageLevel2 = spinnerEditTeachingLanguageLevel2.getSelectedItem().toString();

                String stringPracticeLanguage1 = spinnerEditPracticeLanguage1.getSelectedItem().toString();
                String stringPracticeLanguageLevel1 = spinnerEditPracticeLanguageLevel1.getSelectedItem().toString();
                String stringPracticeLanguag2 = spinnerEditPracticeLanguage2.getSelectedItem().toString();
                String stringPracticeLanguageLevel2 = spinnerEditPracticeLanguageLevel2.getSelectedItem().toString();

                userInformation.updateFirstName(txtEditFirstName.getText().toString());
                userInformation.updateLastName(txtEditLastName.getText().toString());
                userInformation.updatePassword(txtEditPassword.getText().toString());
                stringGender = ((RadioButton) findViewById(radioEditGroupGender.getCheckedRadioButtonId())).getText().toString();
                userInformation.updateGender(stringGender);
                userInformation.updateAge(txtEditAge.getText().toString());
                userInformation.updatePersonal(txtEditPersonalInterest.getText().toString());
                userInformation.modifyTeaching(stringOldTeaching1, stringOldTeachingLevel1, stringTeachingLanguage1, stringTeachingLanguageLevel1);

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
