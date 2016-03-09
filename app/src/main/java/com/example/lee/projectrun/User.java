package com.example.lee.projectrun;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Lee on 09/03/2016.
 */
public class User {

    String userName;
    LatLng userlat;
    public User(String name, LatLng latLng){
        userName = name;
         userlat = latLng;
    }

    public LatLng getlatlng(){

        userlat = new LatLng(-0.116644,51.512009);

        return userlat;
    }


}
