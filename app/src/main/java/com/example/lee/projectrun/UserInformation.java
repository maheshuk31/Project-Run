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
    public String GPS;
    public String Stats;

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
                           String Friends,
                           String GPS,
                           String Stats, String Ip) {
        this.Stats = Stats;
        this.GPS = GPS;
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
        this.IP = Ip;

        System.out.println(UniqueCode);
    }

    public String getGPS() {
        return GPS;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getUniqueCode() {
        return UniqueCode;
    }

    public String getStats
            () {
        return Stats;
    }

    public String getImageString() {
        return Image;
    }

    public String getIP() {
        return IP;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return password;
    }

    public String getAge() {
        return Age;
    }

    public String getGender() {
        return Gender;
    }

    public String[] getTeachingLanguage() {
        String holder = TeachingLanguage;
        String[] parts = holder.split(",");

        return parts;
    }

    public String[] getPracticeLanguage() {
        String holder = PracticeLanguage;
        String[] parts = holder.split(",");

        return parts;
    }

    public String getPersonalInterests() {
        return PersonalInterests;
    }

    public String[] getFriends() {
        String holder = Friends;
        String[] parts = holder.split(",");
        return parts;
    }

    public void updateFirstName(String firstName) {
        FirstName = firstName;
    }

    public void updateLastName(String lastName) {
        LastName = lastName;
    }

    public void updateImage(String image) {
        this.Image = image;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateAge(String age) {
        Age = age;
    }

    public void updateStats(String Stats) {
        this.Stats = Stats;
    }

    public void updateGender(String gender) {
        Gender = gender;
    }

    public void updateIp(String Ip) {
        IP = Ip;
    }

    public void completeRedoTeachingLanguage(String teachingLanguage) {
        TeachingLanguage = teachingLanguage;
    }

    public String modifyTeaching(String oldTeaching, String oldLevel, String newTeaching, String newLevel) {
        String holder = TeachingLanguage;
        String[] parts = holder.split(",");
        String newLanguage = "";
        for (int i = 0; i < parts.length; ++i) {

            if (oldTeaching.equals(parts[i])) {
                newLanguage = newLanguage + parts[i].replace(oldTeaching, newTeaching) + ",";
                newLanguage = newLanguage + parts[i + 1].replace(oldLevel, newLevel) + ",";
            } else {
                newLanguage = newLanguage + parts[i] + ",";
                newLanguage = newLanguage + parts[i + 1] + ",";
            }
            ++i;
        }
        return newLanguage;
    }

    public String modifyPractice(String oldPractice, String oldLevel, String newPractice, String newLevel) {
        String holder = PracticeLanguage;
        String[] parts = holder.split(",");
        String newLanguage = "";
        for (int i = 0; i < parts.length; ++i) {

            if (oldPractice.equals(parts[i])) {
                newLanguage = newLanguage + parts[i].replace(oldPractice, newPractice) + ",";
                newLanguage = newLanguage + parts[i + 1].replace(oldLevel, newLevel) + ",";
            } else {
                newLanguage = newLanguage + parts[i] + ",";
                newLanguage = newLanguage + parts[i + 1] + ",";
            }
            ++i;
        }
        return newLanguage;
    }

    public void updateTeaching(String teachingLanguage) {
        TeachingLanguage = TeachingLanguage + "," + teachingLanguage;
    }

    public void updatePractice(String practiceLanguage) {
        PracticeLanguage = PracticeLanguage + "," + practiceLanguage;
    }

    public void updatePersonal(String personalInterests) {
        PersonalInterests = personalInterests;
    }

    /**
     * Boolean response to check if the user has a friend added.
     *
     * @param FriendsCode
     * @return
     */
    public boolean searchFriendsList(String FriendsCode) {
        String holder = Friends;
        String[] parts = holder.split(",");
        for (int x = 0; x < parts.length; x++) {
            if (parts[x].equals(FriendsCode)) {
                return true;
            }
        }
        return false;
    }

    public void addFriends(String friends) {
        Friends = Friends + "," + friends;
    }

    /**
     * Deletion of a friend
     *
     * @param friendsID UniqueCode of the friend
     */
    public void modifyFriends(String friendsID) {
        String test = Friends;
        String[] output = test.split(",");
        String holder = "";


        for (int i = 0; i < output.length; ++i) {
            if (output[i].equals(friendsID)) {
                output[i].replace(output[i], "");
            } else {
                if (holder.equals("")) {
                    holder = output[i];
                } else {
                    holder = holder + "," + output[i];
                }
            }

        }
        Friends = holder;
    }

    public void updateGPS(String GPS) {
        this.GPS = GPS;
    }

    public void updateStudent(final Activity activity) {
        class updateStudent extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(activity, "Adding", "Wait", false, false);
            }

            protected void onPostExecture(String s) {
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
                params.put(Config.Key_Password, password);
                params.put(Config.Key_Friends, Friends);
                params.put(Config.Key_TeachingLanguage, TeachingLanguage);
                params.put(Config.Key_PracticeLanguage, PracticeLanguage);
                params.put(Config.Key_GPS, GPS);
                params.put(Config.Key_Stats, Stats);

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
