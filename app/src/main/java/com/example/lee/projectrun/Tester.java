package com.example.lee.projectrun;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Tester extends AppCompatActivity implements View.OnClickListener
{

    private EditText EditTextName;
    private EditText EditTextID;
    private EditText EditTextEmail;

    private Button ButtonAdd;
    private Button ButtonView;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);

        EditTextID = (EditText) findViewById(R.id.editTextID);
        EditTextName = (EditText) findViewById(R.id.editTextName);
        EditTextEmail = (EditText) findViewById(R.id.editTextEmail);

        ButtonAdd = (Button) findViewById(R.id.buttonAdd);
        ButtonView = (Button) findViewById(R.id.buttonView);


        ButtonAdd.setOnClickListener(this);
        ButtonView.setOnClickListener(this);

    }






    private void AddUser()
    {
        final String ID = EditTextID.getText().toString().trim();
        final String FirstName = EditTextName.getText().toString().trim();
        final String Email = EditTextEmail.getText().toString().trim();

        class AddUser extends AsyncTask <Void, Void, String>
        {
            ProgressDialog Loading;


            protected void OnPreExecute(String S)
            {
                super.onPreExecute();
                Loading = ProgressDialog.show(Tester.this, "Adding...", "Wait...", false, false);
            }



            protected void OnPostExecute(String S)
            {
                super.onPostExecute(S);
                Loading.dismiss();
                Toast.makeText(Tester.this, S, Toast.LENGTH_LONG).show();
            }



            protected String doInBackground(Void... V)
            {
                HashMap <String, String> Params = new HashMap<>();
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



    public void onClick(View v)
    {
        if(v == ButtonAdd)
        {
            AddUser();
        }

        if(v == ButtonView)
        {
            // Nothing YET...
        }
    }

}
