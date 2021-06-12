package com.example.parkingappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;

import com.example.parkingappandroid.Models.Parking;
import com.example.parkingappandroid.databinding.ActivityAddparkingBinding;
import com.example.parkingappandroid.databinding.ActivityViewParkingBinding;

import java.util.ArrayList;

public class ViewParkingActivity extends AppCompatActivity {

    ArrayList<Parking> pList;
    ParkingAdapter adapter;

    ActivityViewParkingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_view_parking);
        this.binding = ActivityViewParkingBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.pList=new ArrayList<>();
        this.pList.add(new Parking("12345",4));
        this.pList.add(new Parking("1237",24));
       this.adapter=new ParkingAdapter(this,this.pList);

        binding.lvParkings.setAdapter(this.adapter);
    }
}