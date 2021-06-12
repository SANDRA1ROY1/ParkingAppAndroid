package com.example.parkingappandroid;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.parkingappandroid.Models.Parking;
import com.example.parkingappandroid.databinding.RowLayoutBinding;


import java.util.ArrayList;

public class ParkingAdapter extends ArrayAdapter<Parking> {
    RowLayoutBinding binding;
    public ParkingAdapter(Context context, ArrayList<Parking> parkingsList) {
        super(context, 0, parkingsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }
        // Setup row bindings
        binding = RowLayoutBinding.bind(convertView);

        // Get the data item for this position
        Parking p = getItem(position);

        // Update the layout with the data from the Product
       binding.tvBuild.setText(p.getBuildingCode());
       binding.tvHrs.setText(String.valueOf(p.getHrs()));

        // Return the completed view to render on screen
        return convertView;
    }
}
