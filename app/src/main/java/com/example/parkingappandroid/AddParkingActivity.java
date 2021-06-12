package com.example.parkingappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingappandroid.Helpers.LocationHelper;
import com.example.parkingappandroid.databinding.ActivityAddparkingBinding;
import com.example.parkingappandroid.databinding.ActivityMainBinding;

import java.util.Date;
import java.util.regex.Pattern;

public class AddParkingActivity extends AppCompatActivity {

    ActivityAddparkingBinding binding;

    private static final Pattern carPlatePattern = Pattern.compile("^"+"[0-9a-zA-Z]{2,8}"+"$");
    private static final Pattern buldingCodePattern = Pattern.compile("^"+"[0-9a-zA-Z]{5}"+"$");
    private static final Pattern suitNoPattern = Pattern.compile("^"+"[0-9a-zA-Z]{2,5}"+"$");


    Date currentTime = Calendar.getInstance().getTime();

    //location
    private LocationHelper locationHelper;
    private Location lastlOCATION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // setContentView(R.layout.activity_addparking);
        this.binding = ActivityAddparkingBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        //location
        this.locationHelper=LocationHelper.getInstance();
        this.locationHelper.checkpermissions(this);

        if(this.locationHelper.locationPermissionsGranted) {
            //then we can get dev location
            Log.d("TAG", "oncreate:Location permission granted");

            if(binding.chLocation.isChecked()){
                getAndPrintLocation();
            }

            binding.chLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(binding.chLocation.isChecked()){
                        getAndPrintLocation();
                    }else{
                        binding.tvLocation.setText("");
                    }
                }
            });


        }

        setSpinnerForHours();

        setDate();


    }

    private void getAndPrintLocation() {
        this.locationHelper.getLastLocation(this).observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                if(location != null){
                    lastlOCATION=location;

                    // binding.tvLocation.setText(lastlOCATION.toString());
                    String obtainedAddress=locationHelper.getAddress(getApplicationContext(),lastlOCATION);
                    // binding.tvLocationInfo.setText(obtainedAddress);
                    binding.tvLocation.setText(obtainedAddress);
                    Log.d("TAG", "onCreate: Last location obtained " + lastlOCATION.toString());
                }
            }
        });
    }

    //location


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

    private void setDate() {

        String time=currentTime.toString();
        this.binding.tvDate.setText(time);

    }

    private void setSpinnerForHours() {

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hrs_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        this.binding.spHrs.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:{
                //
            }
            break;
            case R.id.action_signOut:{
               //

            }
            break;
            case R.id.action_save:{
                validateSuitNo();
                validateBuldingCode();
                if(validateCarPlateNo() && validateSuitNo() && validateBuldingCode()){
                    clearFields();
                }


            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void clearFields() {

        binding.edBuildingCode.setText("");
       binding.edSuitNo.setText("");
        binding.edLicensePlate.setText("");
        Toast toast = Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_SHORT);
        toast.show();
    }
    private Boolean validateBuldingCode() {
        Boolean isValidated = false;

        String buildingText = binding.edBuildingCode.getText().toString();

        if(!buldingCodePattern.matcher(buildingText).matches()){
            binding.edBuildingCode.setError("Please provide exactly 5 alphanumeric characters");
        }else{
            isValidated=true;
        }
        return  isValidated;
    }
    private Boolean validateSuitNo() {
        Boolean isValidated = false;

        String suitNotext = binding.edSuitNo.getText().toString();

        if(!suitNoPattern.matcher(suitNotext).matches()){
            binding.edSuitNo.setError("Please provide 2-5 alphanumeric characters");
        }else{
            isValidated=true;
        }
        return  isValidated;
    }
    private Boolean validateCarPlateNo() {
        Boolean isValidated = false;

        String carPlateText = binding.edLicensePlate.getText().toString();

        if(!carPlatePattern.matcher(carPlateText).matches()){
            binding.edLicensePlate.setError("Please provide 2-8 alphanumeric characters");
        }else{
            isValidated=true;
        }
        return  isValidated;
    }
}