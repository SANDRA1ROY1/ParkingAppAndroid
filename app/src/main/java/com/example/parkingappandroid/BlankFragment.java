package com.example.parkingappandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.parkingappandroid.Adapters.ParkingAdapter;
import com.example.parkingappandroid.databinding.FragmentBlankBinding;
import com.example.parkingappandroid.Models.Parking;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment{
    FragmentBlankBinding binding;
    ArrayList<Parking> parkingArrayList=new ArrayList<>();
    ParkingAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String mParam1;


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

        insertSomeData();



       /* binding.listParking.setLayoutManager(new LinearLayoutManager(getContext()));*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentBlankBinding.inflate(inflater,container,false);
        View view= binding.getRoot();


                adapter=new ParkingAdapter(getActivity().getApplicationContext(),parkingArrayList);
        binding.listParking.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.listParking.setAdapter(adapter);



        return view;

    }

    private void insertSomeData() {

        for(int i=0;i<5;i++){
           Parking p=new Parking();
           p.setStreetAddr("asad");
           p.setHrs(i);
           parkingArrayList.add(p);
        }
    }


}