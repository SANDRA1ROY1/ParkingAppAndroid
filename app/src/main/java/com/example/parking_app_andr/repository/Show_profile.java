package com.example.parking_app_andr.repository;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parking_app_andr.R;
import com.example.parking_app_andr.TouchHelper;
import com.example.parking_app_andr.model.Profile;
import com.example.parking_app_andr.viewmodel.MyAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Show_profile extends AppCompatActivity {

     RecyclerView recyclerView;
    private   FirebaseFirestore db;
    private MyAdapter adapter;
    private  List<Profile> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


            db= FirebaseFirestore.getInstance();
            list = new ArrayList<>();
            adapter = new MyAdapter(this ,list);
            recyclerView.setAdapter(adapter);

            ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper(adapter));
            touchHelper.attachToRecyclerView(recyclerView);
           //calling data
            showData();
        }

        public void showData(){

            db.collection("profile_details").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            list.clear();
                            for (DocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())){
        //    public Profile(String id, String name, String email, String u_phone_num, String u_car_plate_num, String u_pass_word) {
                                Profile model = new Profile(snapshot.getString("id") ,
                                        snapshot.getString("Name") ,
                                        snapshot.getString("Email"),
                                        snapshot.getString("car plate number"),
                                        snapshot.getString("phone number"),
                                        snapshot.getString("password"));
                                list.add(model);

                            }
                            adapter.notifyDataSetChanged();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Show_profile.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    private void updateToFireStore(String id , String title , String desc){

        db.collection("Documents").document(id).update("title" , title , "desc" , desc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Show_profile.this, "Data Updated!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Show_profile.this, "Error : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Show_profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveToFireStore(String id , String title , String desc){

        if (!title.isEmpty() && !desc.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("id" , id);
            map.put("title" , title);
            map.put("desc" , desc);

            db.collection("profile_details").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Show_profile.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Show_profile.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
}

