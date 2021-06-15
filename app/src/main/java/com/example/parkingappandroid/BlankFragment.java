package com.example.parkingappandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.parkingappandroid.Adapters.ParkingAdapter;
import com.example.parkingappandroid.databinding.FragmentBlankBinding;
import com.example.parkingappandroid.Models.Parking;
import com.example.parkingappandroid.viewmodel.ParkingViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment{
    FragmentBlankBinding binding;
    ArrayList<Parking> parkingArrayList=new ArrayList<>();
    ParkingAdapter adapter;
    ParkingViewModel parkingViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String user="";

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }

       // insertSomeData();
        //SharedPreferences sp=this.getSharedPreferences("UserPrefs",MODE_PRIVATE);
     //   user=sp.getString("username",".@gmail.com");
        SharedPreferences sp=getActivity().getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        user=sp.getString("username",".@gmail.com");
        parkingViewModel=ParkingViewModel.getInstance(getActivity().getApplication());
      parkingViewModel.getAllParkings(user);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentBlankBinding.inflate(inflater,container,false);
        View view= binding.getRoot();

        parkingViewModel.allParkings.observe(getViewLifecycleOwner(), new Observer<List<Parking>>() {
            @Override
            public void onChanged(List<Parking> parkings) {
                if(parkings != null){

                    parkingArrayList=(ArrayList<Parking>) parkings;
                    Log.d("TAG","arraylist got "+parkingArrayList.toString());
                    adapter=new ParkingAdapter(getActivity().getApplicationContext(),parkingArrayList);
                    Log.d("TAG","parking arraylist in oncreate view "+parkingArrayList.toString());
                    binding.listParking.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.listParking.setAdapter(adapter);
                }
            }
        });






        return view;

    }




}