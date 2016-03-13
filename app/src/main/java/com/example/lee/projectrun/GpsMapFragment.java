package com.example.lee.projectrun;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class GpsMapFragment extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    private UserInformation userInformation;

    private static final String TAG = "MyActivity";

    LatLng latLng;
    GoogleMap mGoogleMap;
    SupportMapFragment mFragment;
    Marker mCurrLocation;
    private LinearLayout information;
    private LinearLayout linLaySecondSearchResultsPerPerson;
    private LinearLayout linLayThirdSearchResultsNameImageHolder;
    private TextView txtSearchResultName, txtSearchResultPersonalInfo;
    private ImageView imgProfilePic;
    public String json1;

    private static final int LOCATION_REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Builds the map
        mFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mFragment.getMapAsync(this);
        Intent intent = getIntent();
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");
    }

    //Called when the map is created
    @Override
    public void onMapReady(GoogleMap googleMap) {

        searchLanguage();
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

        buildGoogleApiClient();

        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Log.e(TAG, "Client build " + mGoogleApiClient);

    }

    private void searchLanguage(){
       String[] string = userInformation.getPracticeLanguage();

       for(int x = 0; x<userInformation.getPracticeLanguage().length;x++){
           search(string[x]);

           x++;
       }



    }

    private void search(final String Search) {
        class GetUsers extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.Key_Search, Search);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_Search, params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;

            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showResult(s);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GpsMapFragment.this, "Fetching...", "Wait...", false, false);
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    private void showResult(String json) {
        try {
            json1 = json;
            JSONArray search = new JSONArray(json);
            Log.d("AAA", search.toString());
            for (int i = 0; i < search.length(); i++) {
                final JSONObject jo = search.getJSONObject(i);
                addMarkers(jo, jo.getString("UniqueCode"));




            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void addMarkers(final JSONObject joobject, final String UniqueCode) throws JSONException {
        String holder = joobject.getString("GPS");

        String[] parts = holder.split(",");

        LatLng latlng2;
        Double lat;
        Double lng;
        lat = Double.parseDouble(parts[0]);
        lng = Double.parseDouble(parts[1]);
        //lat = new LatLng(0,0);
        Log.d(TAG, "Long" + lng);
        latlng2 = new LatLng(lat,lng);
        MarkerOptions markersO = new MarkerOptions();
        markersO.position(latlng2);
        markersO.snippet(joobject.getString("FirstName"));
        byte[] decodedString = Base64.decode(joobject.getString("Image"), 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_info_window, null);
        ImageView imgView = (ImageView) marker.findViewById(R.id.imgperson);
        imgView.setImageBitmap(decodedByte);
        final MarkerOptions icon = markersO.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker)));
        markersO.title(joobject.getString("UniqueCode"));



        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {

                try {
                    JSONArray profileSearch = new JSONArray(json1);
                    for (int i = 0; i < profileSearch.length(); i++) {
                        JSONObject jo = profileSearch.getJSONObject(i);
                        if (jo.getString("UniqueCode").equals(marker.getTitle())) {
                            Log.d("FirstName", jo.getString("FirstName"));
                            Intent intent = new Intent(getApplicationContext(), ProfileViewerActivity.class);
                            intent.putExtra("profileFname", jo.getString("FirstName"));
                            intent.putExtra("profileLname", jo.getString("LastName"));
                            intent.putExtra("profileEmail", jo.getString("Email"));
                            intent.putExtra("profileAge", jo.getString("Age"));
                            intent.putExtra("profileGender", jo.getString("Gender"));
                            intent.putExtra("profilePracticingLanguage", jo.getString("PracticeLanguage"));
                            intent.putExtra("profileTeachingLanguage", jo.getString("TeachingLanguage"));
                            intent.putExtra("profilePersonalInterest", jo.getString("PersonalInterests"));
                            intent.putExtra("profileImage", jo.getString("Image"));
                            // intent.putExtra("profileGps", jo.getString("GPS"));
                            startActivity(intent);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        //MyInfoWindowAdapter searchinfoWindow = new MyInfoWindowAdapter(jo.getString("FirstName") + " " +  jo.getString("LastName"), jo.getString("PersonalInterests"), jo.getString("Image"), jo.getString("UniqueCode"));
        //mGoogleMap.setInfoWindowAdapter(searchinfoWindow);
        final Marker searchLocation = mGoogleMap.addMarker(markersO);
    }

    public static Bitmap createDrawableFromView(Context context, View view){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }




    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();
        //Checks permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                    LOCATION_REQUEST_CODE);
            return;
        }

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient); //Gets the last known location

        if (mLastLocation != null) {
            //place marker at last position if available

            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            MarkerOptions markerOptions;
            markerOptions = new MarkerOptions();
            markerOptions.position(latLng);

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng).zoom(13).build();

            mGoogleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

            mCurrLocation = mGoogleMap.addMarker(markerOptions);
            Log.e(TAG, "LATLNG last" + latLng);
        }

        //Sends for location updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        //Called when location request detects a differant longlat
        //remove previous current location marker and add new one at current position

      // if (mCurrLocation != null) {
      //     mCurrLocation.remove();
      // }
      // latLng = new LatLng(location.getLatitude(), location.getLongitude());
      // Toast.makeText(this, location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT);

      // Log.e(TAG, "LATLNG onloc" + latLng);
      // //Adds marker to the current user location
      // MarkerOptions markerOptions = new MarkerOptions();
      // markerOptions.position(latLng);
      // markerOptions.title("Current Position");
      // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
      // mCurrLocation = mGoogleMap.addMarker(markerOptions);



      // Toast.makeText(this,"Location Changed",Toast.LENGTH_SHORT).show();
      // CameraPosition cameraPosition = new CameraPosition.Builder()
      //         .target(latLng).zoom(13).build(); //chnages camara position/zoom when the location changes

      // mGoogleMap.animateCamera(CameraUpdateFactory
      //         .newCameraPosition(cameraPosition));

    }





    //Called to open a popup box asking user for location permission
    protected void requestPermission(String permissionType, int requestCode) {
        int permission = ContextCompat.checkSelfPermission(this,
                permissionType);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permissionType}, requestCode
            );
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Unable to show location - permission required", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //Called when the user click the marker pop up

        marker.showInfoWindow();
        System.out.println("Info window has been clicked!");
    }


    //Opens a window based on the custom_info_window xml when the marker is clicked


    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

        public String Name;
        public String Personal;
        public String Image;
        public String UniqueCode;


        MyInfoWindowAdapter(String Name, String Personal, String Image, String Unique){
            //myContentsView = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            this.Name = Name;
            this.Personal = Personal;
            this.Image = Image;
            this.UniqueCode = Unique;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {

            View v = getLayoutInflater().inflate(R.layout.custom_info_window, null);

            TextView tvTitle = (TextView)v.findViewById(R.id.title);
           // TextView tvSnippet = (TextView)v.findViewById(R.id.snippet);
           // ImageView icon = (ImageView)v.findViewById(R.id.icon_);

//            TextView personal = new TextView(GpsMapFragment.this);
//            personal.setText(Personal);
//
//            TextView name = new TextView(GpsMapFragment.this);
//            name.setText(Name);

          //  tvTitle.setText(Name);
           // tvSnippet.setText(Personal);


            //Sets the text views to the value assigned in markerOptions.title etc
//            ImageView imgProfilePic = new ImageView(GpsMapFragment.this);

        //   byte[] decodedString = Base64.decode(Image, 0);
        //   Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //   icon.setImageBitmap(decodedByte);

            return v;
        }

    }
}

