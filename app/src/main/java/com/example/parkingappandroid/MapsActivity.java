package com.example.parkingappandroid;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.parkingappandroid.Helpers.LocationHelper;
import com.example.parkingappandroid.databinding.ActivityMapsBinding;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private LocationHelper locationHelper;
    private LocationCallback locationCallback;
    private LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        this.currentLocation=new LatLng(this.getIntent().getDoubleExtra("EXTRA_LAT",0)
                ,this.getIntent().getDoubleExtra("EXTRA_LNG",0));

        Log.d("TAG","maps loaction "+currentLocation.toString());


        this.locationHelper=LocationHelper.getInstance();
        this.locationHelper.checkpermissions(this);


        if(this.locationHelper.locationPermissionsGranted){
            this.initiateLocationListener();
        }
    }

    public void initiateLocationListener() {
        this.locationCallback=new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if(locationResult == null){
                    return;
                }
                for(Location loc: locationResult.getLocations()){
                   // currentLocation=new LatLng(loc.getLatitude(),loc.getLongitude());

                    //update map with location

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15.0f));

                    Log.d("TAG","onLocationResult: update location: "+loc.toString());
                }
            }
        };

        this.locationHelper.requestLocationUpdates(this,this.locationCallback);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(googleMap != null){
            mMap=googleMap;
            this.setupMapAppearance(googleMap);

        }
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(, 151);
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private  void setupMapAppearance(GoogleMap googleMap){
        googleMap.setBuildingsEnabled(true);
        googleMap.setIndoorEnabled(false);
        googleMap.setTrafficEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        UiSettings myUIsettings=googleMap.getUiSettings();
        myUIsettings.setZoomControlsEnabled(true);
        myUIsettings.setZoomGesturesEnabled(true);

        myUIsettings.setRotateGesturesEnabled(true);


        googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Parking Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.locationHelper.stopLocationUpdates(this,this.locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.locationHelper.requestLocationUpdates(this,locationCallback);
    }
}