package com.example.lee.projectrun;

/**
 * Created by Igor and Darren on 21/02/2016.
 */
public class Config
{
    // Addresses to the scripts of the CRUD.
    public static final String URL_CheckLogin= "http://projectrun.x10host.com/CheckLogin.php";
    public static final String URL_AddUser = "http://projectrun.x10host.com/UserCreation.php";
    public static final String URL_ModifyUser = "http://projectrun.x10host.com/UserModification.php";
    public static final String URL_FindFriends = "http://projectrun.x10host.com/FindFriends.php";
    public static final String URL_RetrieveUser = "http://projectrun.x10host.com/RetrieveUser.php";
    public static final String URL_Search = "http://projectrun.x10host.com/Search.php";

    // Keys that will be used to send requests to php scripts.
    public static final String Key_ID = "UniqueCode";
    public static final String Key_Name = "FName";
    public static final String Key_LName = "LName";
    public static final String Key_Email = "Email";
    public static final String Key_Search = "Search";
    public static final String Key_Password = "password";
    public static final String Key_Age = "Age";
    public static final String Key_Gender = "Gender";
    public static final String Key_TeachingLanguage = "TeachingLanguage";
    public static final String Key_PracticeLanguage = "PracticeLanguage";
    public static final String Key_PersonalInterests = "PersonalInterests";
    public static final String Key_Friends = "Friends";
    public static final String Key_IP = "IP";
    public static final String Key_Image = "Image";
    public static final String Key_GPS = "GPS";

    // JSON tags.
    public static final String TAG_ID = "UniqueCode";
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_Name = "FirstName";
    public static final String TAG_Image = "Image";
    public static final String TAG_LName = "LastName";
    public static final String TAG_Email = "Email";
    public static final String TAG_Password = "password";
    public static final String TAG_Age = "Age";
    public static final String TAG_Gender = "Gender";
    public static final String TAG_TeachingLanguage = "TeachingLanguage";
    public static final String TAG_PracticeLanguage = "PracticeLanguage";
    public static final String TAG_PersonalInterests = "PersonalInterests";
    public static final String TAG_Friends = "Friends";




}
