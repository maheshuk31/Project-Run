package com.example.lee.projectrun;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class ScheduleMeetingActivity extends AppCompatActivity {

    private Spinner spinnerCampuses;
    private static EditText txtSetTime, txtSetDate;
    private Button btnSchConfirm, btnSchCancel;
    private String[] arrayListCampuses = {"Select a Campus", "Strand", "Guy's", "Waterloo", "St Thomas'",
            "Denmark Hill", "The Maughan Library", "Franklin-Wilkins Library", "James Clark Maxwell"};
    private String stringCampus, stringTime, stringDate;
    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_meeting);

        Intent intent = getIntent();
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");

        ArrayAdapter<String> adapterLanguages = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, arrayListCampuses);

        spinnerCampuses = (Spinner) findViewById(R.id.spinnerCampuses);
        spinnerCampuses.setAdapter(adapterLanguages);
        txtSetTime = (EditText) findViewById(R.id.txtSetTime);
        txtSetDate = (EditText) findViewById(R.id.txtSetDate);
        btnSchConfirm = (Button) findViewById(R.id.btnSchConfirm);
        btnSchCancel = (Button) findViewById(R.id.btnSchCancel);

        txtSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog(v);
            }
        });

        txtSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog(v);
            }
        });


        btnSchConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerCampuses.getSelectedItem().toString().equals("Select a Campus")){
                    txtSetTime.setError("Select a valid campus");
                }
                else{
                    stringCampus = spinnerCampuses.getSelectedItem().toString();
                    stringTime = txtSetTime.getText().toString();
                    stringDate = txtSetDate.getText().toString();
                }
            }
        });

        btnSchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void timePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "ChooseTime");
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hour, int minute) {
            txtSetTime.setText(hour + ":" + minute);
        }
    }

    public void datePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "ChooseDate");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            txtSetDate.setText(day + "/" + (month + 1) + "/" + year);
        }
    }





}
