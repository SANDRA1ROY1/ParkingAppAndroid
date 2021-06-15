package com.example.parkingappandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.parkingappandroid.Helpers.LocationHelper;
import com.example.parkingappandroid.Models.Parking;
import com.example.parkingappandroid.viewmodel.ParkingViewModel;
import com.example.parkingappandroid.databinding.ActivityUpdateBinding;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.util.List;
import java.util.regex.Pattern;

public class UpdateActivity extends AppCompatActivity {


    ActivityUpdateBinding binding;

    private static final Pattern carPlatePattern = Pattern.compile("^" + "[0-9a-zA-Z]{2,8}" + "$");
    private static final Pattern buldingCodePattern = Pattern.compile("^" + "[0-9a-zA-Z]{5}" + "$");
    private static final Pattern suitNoPattern = Pattern.compile("^" + "[0-9a-zA-Z]{2,5}" + "$");

    private LocationHelper locationHelper;
    private Location lastlOCATION;
    private LocationCallback locationCallback;

    private int[] spinnerForHrs={1,4,12,24};

    private ParkingViewModel pViewModel;

    AlertDialog.Builder builder;
    Location addressloc;

    int adapterPosition=0;
    Parking currentParking=new Parking();

    String user="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_update);
        binding=ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sp=getApplicationContext().getSharedPreferences("UserPrefs",MODE_PRIVATE);
        user=sp.getString("username",".@gmail.com");

        //load data from db
        Intent i =getIntent();
       adapterPosition= i.getIntExtra("position",0);
       Log.d("position received",String.valueOf(adapterPosition));
       pViewModel=ParkingViewModel.getInstance(getApplication());
       pViewModel.getAllParkings(user);
       this.pViewModel.allParkings.observe(this, new Observer<List<Parking>>() {
           @Override
           public void onChanged(List<Parking> parkings) {
               if(parkings!= null && !parkings.isEmpty()){
                   Log.d("parking","isEmpty");
                   if(adapterPosition<0){
                       adapterPosition=0;
                   }
                   currentParking=parkings.get(adapterPosition);
                   binding.edBuildingCode.setText(currentParking.getBuildingCode());
                   binding.edLicensePlate.setText(currentParking.getCarPlateNo());
                   binding.edSuitNo.setText(currentParking.getSuiteNo());
                   binding.tvDate.setText(String.valueOf(currentParking.getDate()));
                   binding.tvLocation.setText(currentParking.getStreetAddr());
                   binding.spHrs.setText(String.valueOf(currentParking.getHrs()));
               }
           }
       });


    binding.btnDeleteParking.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pViewModel.deleteParking(currentParking.getRef());
            pViewModel.getAllParkings(user);
            Intent i=new Intent(getApplicationContext(),MainActivity1.class);
            i.putExtra("toast","Parking deleted successfully");
            startActivity(i);
        }
    });

    binding.btnUpdateParking.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentParking.setBuildingCode(binding.edBuildingCode.getText().toString());
            currentParking.setCarPlateNo(binding.edLicensePlate.getText().toString());
            currentParking.setSuiteNo(binding.edSuitNo.getText().toString());

            pViewModel.updateParking(currentParking);
            pViewModel.getAllParkings(user);
            Intent i=new Intent(getApplicationContext(),MainActivity1.class);
            i.putExtra("toast","Parking updated successfully");
            startActivity(i);
        }
    });




        this.locationHelper=LocationHelper.getInstance();
        this.locationHelper.checkpermissions(this);
        this.pViewModel=ParkingViewModel.getInstance(this.getApplication());

        //setEverytging invisible
        binding.chLocation.setChecked(false);

        this.binding.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent=new Intent(getApplicationContext(), MapsActivity.class);
                mapIntent.putExtra("EXTRA_LAT",lastlOCATION.getLatitude());
                mapIntent.putExtra("EXTRA_LNG",lastlOCATION.getLongitude());
                startActivity(mapIntent);
            }
        });

        if(this.locationHelper.locationPermissionsGranted){
            this.initiateLocationListener();

            //then we can get dev location
            Log.d("TAG", "oncreate:Location permission granted");

            if(binding.chLocation.isChecked()){
                Log.d("TAG","isChecked");
                getAndPrintLocation();

            }

            binding.chLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(binding.chLocation.isChecked()){
                        getAndPrintLocation();
                    }else{
                        binding.tvLocation.setText("");
                        setBuilder();

                    }
                }
            });
        }



    }


    private void setBuilder() {


        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        LinearLayout ll_alert_layout = new LinearLayout(this);
        ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
        final EditText ed_input1 = new EditText(this);
        ed_input1.setHint("Enter street");
        final EditText ed_input2 = new EditText(this);
        ed_input2.setHint("Enter city");
        final EditText ed_input3 = new EditText(this);
        ed_input3.setHint("Enter country");
        ll_alert_layout.addView(ed_input1);
        ll_alert_layout.addView(ed_input2);
        ll_alert_layout.addView(ed_input3);

        alertbox.setTitle("Enter Address");
        alertbox.setMessage("");

        //setting linear layout to alert dialog

        alertbox.setView(ll_alert_layout);

        alertbox.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        binding.chLocation.setChecked(true);

                        // will automatically dismiss the dialog and will do nothing

                    }
                });


        alertbox.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        String street = ed_input1.getText().toString();
                        String city = ed_input2.getText().toString();
                        String country = ed_input3.getText().toString();

                        // do your action with input string - forward geocoding
                        //after success of forward, prin it to textview
                        String addressString=street+","+city+","+country;
                        addressloc=locationHelper.getCoordinates(getApplicationContext(),addressString);
                        if(addressloc.getLatitude()==0 && addressloc.getLongitude()==0) {
                            if(!binding.chLocation.isChecked()) {

                            }
                            Log.d("TAG","couldnt fetch address coordinates");
                        }else{
                            Address addressObj=locationHelper.getAddress(getApplicationContext(),addressloc);
                            binding.tvLocation.setText(addressObj.getAddressLine(0));
                            Log.d("TAG","location coords for locn entered "+addressloc.getLatitude()+"      "+addressloc.getLongitude());
                        }

                    }
                });
        alertbox.show();


    }

    private void getAndPrintLocation() {
        this.locationHelper.getLastLocation(this).observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                if(location != null){
                    lastlOCATION=location;


                    Address obtainedAddress=locationHelper.getAddress(getApplicationContext(),lastlOCATION);

                    if(binding.chLocation.isChecked()) {
                        binding.tvLocation.setText(obtainedAddress.getAddressLine(0));
                    }
                    Log.d("TAG", "onCreate: Last location obtained " + lastlOCATION.toString());
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == this.locationHelper.REQUEST_CODE_LOCATION){
            this.locationHelper.locationPermissionsGranted = (grantResults.length>0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED);


            if(this.locationHelper.locationPermissionsGranted){
                Log.d("TAG","onCreate: location permission granted : "+this.locationHelper.locationPermissionsGranted);
            }
        }
    }

    private void initiateLocationListener(){
        Log.d("TAG","reched initiate loc listener");
        this.locationCallback=new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if(locationResult == null){
                    return;
                }
                for(Location loc: locationResult.getLocations()){
                    lastlOCATION=loc;
                    if(binding.chLocation.isChecked()) {
                        binding.tvLocation.setText(locationHelper.getAddress(getApplicationContext(), loc).getAddressLine(0));
                    }
                    Log.d("TAG","onLocationResult: update location: "+lastlOCATION.getLatitude());
                }
            }
        };

        this.locationHelper.requestLocationUpdates(this,this.locationCallback);
    }

    public void validate(){
        Toast toast;
        validateSuitNo();
        validateBuldingCode();
        validateCarPlateNo();
        if(validateBuldingCode() && validateCarPlateNo() && validateSuitNo() && !binding.tvLocation.getText().toString().isEmpty()) {

           // updateParkingToDB();
           // clearFields();
        }else{
            if(binding.tvLocation.getText().toString().isEmpty()) {
                toast = Toast.makeText(this, "Please enter correct location", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }

    private Boolean validateBuldingCode() {

        Boolean isValidated = false;

        String buildingText = binding.edBuildingCode.getText().toString();

        if (!buldingCodePattern.matcher(buildingText).matches()) {
            binding.edBuildingCode.setError("Please provide exactly 5 alphanumeric characters");
        } else {
            isValidated = true;
        }
        return isValidated;
    }

    private Boolean validateSuitNo() {
        Boolean isValidated = false;

        String suitNotext = binding.edSuitNo.getText().toString();

        if (!suitNoPattern.matcher(suitNotext).matches()) {
            binding.edSuitNo.setError("Please provide 2-5 alphanumeric characters");
        } else {
            isValidated = true;
        }
        return isValidated;
    }

    private Boolean validateCarPlateNo() {
        Boolean isValidated = false;

        String carPlateText = binding.edLicensePlate.getText().toString();

        if (!carPlatePattern.matcher(carPlateText).matches()) {
            binding.edLicensePlate.setError("Please provide 2-8 alphanumeric characters");
        } else {
            isValidated = true;
        }
        return isValidated;
    }
}