package com.example.parking_app_andr.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.parking_app_andr.R;
import com.example.parking_app_andr.repository.Show_profile;
import com.example.parking_app_andr.model.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.List;
import java.util.Objects;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    TextView user_name_tv;

    private final Show_profile activity;

    private final List<Profile> mList;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    //show profile constructor
    public MyAdapter(Show_profile activity , List<Profile> mList){
        this.activity = activity;
        this.mList = mList;
    }

    public void updateData(int position){
        Profile item = mList.get(position);
        Bundle bundle = new Bundle();

        bundle.putString("id", item.getId());
        bundle.putString("Email" ,item.getEmail());
        bundle.putString("Name" , item.getName());
        bundle.putString("car plate number" , item.getU_car_plate_num());
        bundle.putString("contact number",item.getU_phone_num());
        bundle.putString("password" , item.getU_pass_word());

        //start the screen

        Intent intent = new Intent(activity , Show_profile.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position){
        Profile item = mList.get(position);
        db.collection("profile_details").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data Deleted !!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity, "Error" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item , parent , false);
       //passing view

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.user_name_tv.setText(mList.get(position).getName());
        holder.u_email_tv.setText(mList.get(position).getEmail());
        holder.u_carplate_num_tv.setText(mList.get(position).getU_car_plate_num());
        holder.u_Phone_num_tv.setText(mList.get(position).getU_phone_num());
        holder.u_Password_tv.setText(mList.get(position).getU_pass_word());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView user_name_tv,u_email_tv,u_carplate_num_tv,u_Phone_num_tv,u_Password_tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name_tv = itemView.findViewById(R.id.user_name_tv);
            u_email_tv=itemView.findViewById(R.id.u_email_tv);
            u_carplate_num_tv=itemView.findViewById(R.id.u_carplate_num_tv);
            u_Phone_num_tv=itemView.findViewById(R.id.u_Phone_num_tv);
            u_Password_tv=itemView.findViewById(R.id.u_Password_tv);

        }
    }

    }