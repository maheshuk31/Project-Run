package com.example.lee.projectrun;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;

public class UserInformation implements Serializable {
    public String UniqueCode;
    public String FirstName;
    public String Image;
    public String LastName;
    public String Email;
    public String password;
    public String Age;
    public String Gender;
    public String TeachingLanguage;
    public String PracticeLanguage;
    public String PersonalInterests;
    public String Friends;
    public String Search;
    public String IP;

    public UserInformation(String UniqueCode, String FirstName,
                           String Image,
                           String LastName,
                           String Email,
                           String password,
                           String Age,
                           String Gender,
                           String TeachingLanguage,
                           String PracticeLanguage,
                           String PersonalInterests,
                           String Friends) {
        this.UniqueCode = UniqueCode;
        this.FirstName = FirstName;
        this.Image = Image;
        this.LastName = LastName;
        this.Email = Email;
        this.password = password;
        this.Age = Age;
        this.Gender = Gender;
        this.TeachingLanguage = TeachingLanguage;
        this.PracticeLanguage = PracticeLanguage;
        this.PersonalInterests = PersonalInterests;
        this.Friends = Friends;

        System.out.println(UniqueCode);
        }

    public String getFirstName(){
        return FirstName;
    }
    public String getUniqueCode(){
        return UniqueCode;
    }
    public String getImageString(){
        return Image;
    }
    public String getLastName(){
        return LastName;
    }
    public String getEmail(){
        return Email;
    }
    public String getPassword(){
        return password;
    }
    public String getAge(){
        return Age;
    }
    public String getGender(){
        return Gender;
    }
    public String getTeachingLanguage(){
        return TeachingLanguage;
    }
    public String getPracticeLanguage(){
        return PracticeLanguage;
    }
    public String getPersonalInterests(){
        return PersonalInterests;
    }
    public String getFriends(){
        return Friends;
    }
    public void updateFirstName(String firstName){
        FirstName = firstName;
    }
    public void updateLastName(String lastName){
        LastName = lastName;
    }
    public void updateImage(String image){
        this.Image = image;
    }
    public void updatePassword(String password){
        this.password = password;
    }
    public void updateAge(String age){
        Age = age;
    }
    public void updateGender(String gender){
        Gender = gender;
    }
    public void updateTeaching(String teachingLanguage){
        TeachingLanguage = teachingLanguage;
    }
    public void updatePractice(String practiceLanguage){
        PracticeLanguage = practiceLanguage;
    }
    public void updatePersonal(String personalInterests){
        PersonalInterests = personalInterests;
    }
    public void updateFriends(String friends){
        Friends  = Friends + "," + friends;
    }

    public void updateStudent(final Activity activity){
        class updateStudent extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(activity, "Adding", "Wait", false, false);
            }
            protected void onPostExecture(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(activity, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {

                HashMap<String, String> params = new HashMap<>();
                params.put(Config.Key_Name, FirstName);
                params.put(Config.Key_ID, UniqueCode);
                params.put(Config.Key_Email, Email);
                params.put(Config.Key_Age, Age);
                params.put(Config.Key_LName, LastName);
                params.put(Config.Key_Gender, Gender);
                params.put(Config.Key_PersonalInterests, PersonalInterests);
                params.put(Config.Key_IP, IP);
                params.put(Config.Key_Image, Image);
                //params.put(Config.Key_GPS, gps);
                params.put(Config.Key_Password, password);
                params.put(Config.Key_Friends, Friends);
                params.put(Config.Key_TeachingLanguage, TeachingLanguage);
                params.put(Config.Key_PracticeLanguage, PracticeLanguage);

                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_ModifyUser, params);
                return res;
            }
        }
        updateStudent us = new updateStudent();
        us.execute();
    }



 // private void search(String Search1, final Activity activity) {
 //     this.Search = Search1;
 //     class GetUsers extends AsyncTask<Void, Void, String> {
 //         ProgressDialog loading;

 //         @Override
 //         protected String doInBackground(Void... v) {
 //             HashMap<String, String> params = new HashMap<>();
 //             params.put(Config.Key_Search, Search);
 //             RequestHandler rh = new RequestHandler();
 //             String res = rh.SendPostRequest(Config.URL_Search, params);
 //             Log.d("AAAA", "doInBackground: " + res);
 //             return res;

 //         }

 //         protected void onPostExecute(String s) {
 //             super.onPostExecute(s);
 //             loading.dismiss();
 //             showResult(s);
 //         }

 //         @Override
 //         protected void onPreExecute() {
 //             super.onPreExecute();
 //             loading = ProgressDialog.show(activity, "Fetching...", "Wait...", false, false);
 //         }
 //     }
 //     GetUsers getUsers = new GetUsers();
 //     getUsers.execute();
 // }

}
