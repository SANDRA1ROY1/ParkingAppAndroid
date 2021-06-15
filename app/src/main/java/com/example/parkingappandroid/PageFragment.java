package com.example.parkingappandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.parkingappandroid.viewmodel.ParkingViewModel;
import com.example.parkingappandroid.databinding.FragmentPageBinding;
import com.example.parkingappandroid.Models.Parking;
import com.example.parkingappandroid.viewmodel.ProfileViewModel;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import com.example.parkingappandroid.Helpers.LocationHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageFragment extends Fragment {
    FragmentPageBinding binding;



    private static final Pattern carPlatePattern = Pattern.compile("^" + "[0-9a-zA-Z]{2,8}" + "$");
    private static final Pattern buldingCodePattern = Pattern.compile("^" + "[0-9a-zA-Z]{5}" + "$");
    private static final Pattern suitNoPattern = Pattern.compile("^" + "[0-9a-zA-Z]{2,5}" + "$");

    Date currentTime = Calendar.getInstance().getTime();

    //location
    LocationHelper locationHelper;
    private Location lastlOCATION;

    private int[] spinnerForHrs={1,4,12,24};

    private ParkingViewModel pViewModel;
    private ProfileViewModel profileViewModel;

    AlertDialog.Builder builder;
    Location addressloc;

    String user;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PAage = "arg_page";
   private int mPage;
    private LocationCallback locationCallback;

    // TODO: Rename and change types of parameters


    public static PageFragment newInstance(int page) {
        // Required empty public constructor
        Bundle args=new Bundle();
        args.putInt(ARG_PAage,page);
        PageFragment fragment=new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment PageFragment.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAage);

        }

        //location
        this.locationHelper=LocationHelper.getInstance();
        this.locationHelper.checkpermissions(getContext());
        this.pViewModel=ParkingViewModel.getInstance(getActivity().getApplication());
        this.profileViewModel=ProfileViewModel.getInstance(getActivity().getApplication());


        SharedPreferences sp=getActivity().getApplicationContext().getSharedPreferences("UserPrefs",Context.MODE_PRIVATE);
        user=sp.getString("username",".@gmail.com");
        loadDefaultCarPlateNo(user);


    }

    private void loadDefaultCarPlateNo(String email) {
        this.profileViewModel.searchUserToLoadCarPlateNo(email);
        this.profileViewModel.email123.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!= null) {
                    binding.edLicensePlate.setText(s);
                }
            }
        });


        //

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentPageBinding.inflate(inflater,container,false);
       View view= binding.getRoot();
        //loadDefaultCarPlateNo(user);
        Log.d("user",user);
        setSpinnerForHours(getContext());


        setDate();

        builder = new AlertDialog.Builder(getContext());



        binding.btnSaveParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
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

        return  view;
    }



    private void setBuilder() {


        AlertDialog.Builder alertbox = new AlertDialog.Builder(getContext());

        LinearLayout ll_alert_layout = new LinearLayout(getContext());
        ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
        final EditText ed_input1 = new EditText(getContext());
        ed_input1.setHint("Enter street");
        final EditText ed_input2 = new EditText(getContext());
        ed_input2.setHint("Enter city");
        final EditText ed_input3 = new EditText(getContext());
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
                        addressloc=locationHelper.getCoordinates(getContext(),addressString);
                        if(addressloc.getLatitude()==0 && addressloc.getLongitude()==0) {
                            if(!binding.chLocation.isChecked()) {

                            }
                            Log.d("TAG","couldnt fetch address coordinates");
                        }else{
                            Address addressObj=locationHelper.getAddress(getContext(),addressloc);
                            binding.tvLocation.setText(addressObj.getAddressLine(0));
                            Log.d("TAG","location coords for locn entered "+addressloc.getLatitude()+"      "+addressloc.getLongitude());
                        }

                    }
                });
        alertbox.show();


    }

    //location
    private void getAndPrintLocation() {
        this.locationHelper.getLastLocation(getContext()).observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                if(location != null){
                    lastlOCATION=location;


                   Address obtainedAddress=locationHelper.getAddress(getContext(),lastlOCATION);

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
                        binding.tvLocation.setText(locationHelper.getAddress(getContext(), loc).getAddressLine(0));
                    }
                    Log.d("TAG","onLocationResult: update location: "+lastlOCATION.getLatitude());
                }
            }
        };

        this.locationHelper.requestLocationUpdates(getContext(),this.locationCallback);
    }


    private void setDate() {

        String time=currentTime.toString();
        this.binding.tvDate.setText(time);

    }

    private void setSpinnerForHours(Context context) {

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.hrs_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        this.binding.spHrs.setAdapter(adapter);
    }

    public void validate(){
        Toast toast;
        validateSuitNo();
        validateBuldingCode();
        validateCarPlateNo();
        if(validateBuldingCode() && validateCarPlateNo() && validateSuitNo() && !binding.tvLocation.getText().toString().isEmpty()) {

        saveParkingToDB();
        clearFields();
        }else{
                if(binding.tvLocation.getText().toString().isEmpty()) {
                    toast = Toast.makeText(getContext(), "Please enter correct location", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

    }

    private void clearFields() {


        binding.edBuildingCode.setText("");
        binding.edSuitNo.setText("");
        binding.edLicensePlate.setText("");

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

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding=null;
    }

    private void saveParkingToDB(){
        Parking p  =new Parking();
        p.setBuildingCode(binding.edBuildingCode.getText().toString());
        p.setCarPlateNo(binding.edLicensePlate.getText().toString());
        p.setSuiteNo(binding.edSuitNo.getText().toString());
        p.setHrs(spinnerForHrs[binding.spHrs.getSelectedItemPosition()]);

        String dateString=binding.tvDate.getText().toString();
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE MMM d HH:mm:ss zz yyyy");
        Date stringDate = simpledateformat.parse(dateString,pos);
        if(stringDate != null){
            p.setDate(stringDate);

        }else{
            Date d=new Date();
            p.setDate(d);

        }
        if(!binding.tvLocation.getText().toString().isEmpty()) {
            p.setStreetAddr(binding.tvLocation.getText().toString());
        }
        if(binding.chLocation.isChecked()){
              p.setLat(lastlOCATION.getLatitude());
              p.setLng(lastlOCATION.getLongitude());
        }else{
            p.setLat(addressloc.getLatitude());
            p.setLng(addressloc.getLongitude());
        }

        p.setEmail(user);

        Boolean isParkingExist=pViewModel.searchParking(stringDate);

        Toast toast;
        if(!isParkingExist) {
            pViewModel.addParking(p);
            pViewModel.getAllParkings(user);
          toast  = Toast.makeText(getContext(), "Data saved successfully", Toast.LENGTH_SHORT);

        }else{
            toast=Toast.makeText(getContext(),"parking already exists",Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}