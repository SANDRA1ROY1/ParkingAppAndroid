package com.example.parkingappandroid.Helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

public class LocationHelper {
    public final int REQUEST_CODE_LOCATION = 101;
    private final String TAG = this.getClass().getCanonicalName();
    public boolean locationPermissionsGranted = false;


    private LocationRequest locationRequest;

    private FusedLocationProviderClient fusedLocationProviderClient = null;

    MutableLiveData<Location> mlocation = new MutableLiveData<>();

    private static final LocationHelper ourInstance = new LocationHelper();//-obj created

    public static LocationHelper getInstance() {
        return ourInstance;
    }

    private LocationHelper() {
        //3.create location request
        //5.add code for locn afetr locn cls added
        this.locationRequest = new LocationRequest();
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //can .setInterval() - receive updates in howmuch time
        //setFastInterval()
        //setPriority
        this.locationRequest.setInterval(10000);//30 sec
    }


    public void checkpermissions(Context context) {
        this.locationPermissionsGranted = (ContextCompat.checkSelfPermission(context.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);

        Log.d(TAG, "LOCATION PERMISIION : " + this.locationPermissionsGranted);

        if (!locationPermissionsGranted) {
            //dpesnt have perm
            //so request perm
            //9.
            requestLocationPermission(context);
        }
    }

    void requestLocationPermission(Context context) {
        //after 8. give 3rd parameter
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                this.REQUEST_CODE_LOCATION);

    }

    //15.if net lsow, charge less
    public FusedLocationProviderClient getFusedLocationProviderClient(Context context) {
        if (this.fusedLocationProviderClient == null) {
            this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        }
        return this.fusedLocationProviderClient;
    }

    @SuppressLint("MissingPermission")
    public MutableLiveData<Location> getLastLocation(Context context) {
        if (this.locationPermissionsGranted) {
            try {

                this.getFusedLocationProviderClient(context)
                        .getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    mlocation.setValue(location);
                                    Log.d(TAG, "onSuccess: last location obtained : lat: " + mlocation.getValue().getLatitude()
                                            + " longitude: " + mlocation.getValue().getLongitude());
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("TAG", "failed to succeed getLastLocation : " + e.getLocalizedMessage());
                            }
                        });
            } catch (Exception e) {
                Log.e("TAG", "couldnt get last location even after perm granted: " + e.getLocalizedMessage());
                return null;
            }

            return mlocation;

        }
        return null;

    }

    public Address getAddress(Context context, Location loc) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList;

        try {

            addressList = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 2);
            String address = addressList.get(0).getAddressLine(0);

            Address addressObj = addressList.get(0);
            Log.d(TAG, "getAddress: country code: " + addressObj.getCountryCode());
            Log.d(TAG, "getAddress: country: " + addressObj.getCountryName());
            Log.d(TAG, "getAddress: city: " + addressObj.getLocality());
            Log.d(TAG, "getAddress: postal code: " + addressObj.getPostalCode());
            Log.d(TAG, "getAddress: province: " + addressObj.getAdminArea());
            Log.d(TAG, "getAddress: address : " + address);
            return addressObj;

        } catch (Exception e) {
            Log.e(TAG, "getAddress: not able to convert to address: " + e.getLocalizedMessage());
        }
        return null;
    }

    @SuppressLint("MissingPermission")
    public void requestLocationUpdates(Context context, LocationCallback locationCallback) {
        if (this.locationPermissionsGranted) {
            try {
                this.getFusedLocationProviderClient(context).requestLocationUpdates(this.locationRequest, locationCallback, Looper.getMainLooper());
            } catch (Exception e) {
                Log.e(TAG, "requestLocationUpdates: couldntr get location updates : " + e.getLocalizedMessage());
            }

        }
    }

    public void stopLocationUpdates(Context context, LocationCallback locationCallback) {
        if (this.locationPermissionsGranted) {
            try {
                this.getFusedLocationProviderClient(context).removeLocationUpdates(locationCallback);
            } catch (Exception e) {
                Log.e(TAG, "stopLocationUpdates: couldntr stop location updates : " + e.getLocalizedMessage());
            }

        }
    }


    public Location getCoordinates(Context context,String addressEntered) {


        Geocoder geocoder = new Geocoder(context);
        double latitude=0;
        double longitude=0;
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(addressEntered, 1);
            if(addresses.size()>0)

            {
                latitude = addresses.get(0).getLatitude();
                longitude = addresses.get(0).getLongitude();
                Location locationcoord = new Location("");
                locationcoord.setLatitude(latitude);
                locationcoord.setLongitude(longitude);
                return locationcoord;
            }
        }catch(Exception e){
            Log.e("TAG","couldnt get coordinates from address enterd: "+e.getLocalizedMessage());
        }
        Location location=new Location("");
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        return location;
//return new Location(latitude,longitude);
    }
}
