package com.example.lee.projectrun;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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


public class GpsMapFragment extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;

    private static final String TAG = "MyActivity";

    TextView textView;


    LatLng latLng;
    GoogleMap mGoogleMap;
    SupportMapFragment mFragment;
    Marker mCurrLocation;
    private static final int LOCATION_REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Builds the map
        mFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mFragment.getMapAsync(this);




    }

    //Called when the map is created
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);


        buildGoogleApiClient();

        mGoogleApiClient.connect();


        mGoogleMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
        mGoogleMap.setOnInfoWindowClickListener(this);


        LatLng l = new LatLng(-0.116644,51.512009);
        User user = new User("Lee", l);
        MarkerOptions mO=new MarkerOptions();
        Marker mm;
        mO.position(user.getlatlng());

       mm = mGoogleMap.addMarker(mO);


        for (int i =0;i<7;i++){

        }


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
                mGoogleApiClient);

        if (mLastLocation != null) {
            //place marker at current position

            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions;
            markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Lee Wonnacott");
            markerOptions.snippet("Computer Science");
      //      markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.kinglogo));

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng).zoom(13).build();

            mGoogleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

            mCurrLocation = mGoogleMap.addMarker(markerOptions);

            Toast.makeText(this, mLastLocation.getLatitude() + ", " + mLastLocation.getLongitude(), Toast.LENGTH_SHORT);

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

        if (mCurrLocation != null) {
            mCurrLocation.remove();
        }
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Toast.makeText(this, location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT);

        Log.e(TAG, "LATLNG onloc" + latLng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCurrLocation = mGoogleMap.addMarker(markerOptions);



        Toast.makeText(this,"Location Changed",Toast.LENGTH_SHORT).show();
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(13).build();

        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

    }





    //Called in onCreate to open a popup box asking user for location permission
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
        //TODO Open the user profile when popup is clicked

        System.out.println("Popup has been clicked!");
    }


    //Opens a window based on the custom_info_window xml when the marker is clicked


    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

        private final View myContentsView;
        MyInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_window, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {



            TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
            TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));




            //Sets the text views to the value assigned in markerOptions.title etc
            ImageView image = ((ImageView) findViewById(R.id.icon_));


          //  image.setImageIcon(marker.setIcon(R.drawable.kinglogo);


          //  tvTitle.setText(marker.getTitle());
       //     tvSnippet.setText(marker.getSnippet());



            return myContentsView;


        }

    }
}

