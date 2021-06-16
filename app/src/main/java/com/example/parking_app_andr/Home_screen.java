package com.example.parking_app_andr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parking_app_andr.repository.Show_profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Home_screen extends AppCompatActivity {
//set text
    EditText home_username_edt,home_email_edt,home_password_edt,home_contact_edt,home_car_plate_edt;

    Button sign_out_btn,view_profile,save_btn;
    FirebaseFirestore firebaseFirestore;
     DatabaseReference mDatabase;

    //strings to store

     String Uid,Name,Email,car_plate_number,phone_number,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        save_btn=findViewById(R.id.save_btn);
        view_profile=findViewById(R.id.view_profile);
        sign_out_btn=findViewById(R.id.sign_out_btn);

//getting fire store data
        firebaseFirestore= FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

//for update screen to get the updated data
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //setting data
            save_btn.setText("Update");

            //getting the data from bundle

            Uid = bundle.getString("id");
            Name = bundle.getString("Name");
            Email = bundle.getString("Email");
            car_plate_number = bundle.getString("car plate number");
            phone_number = bundle.getString("contact number");
            password = bundle.getString("password");

//setting text

            home_username_edt.setText(Name);
            home_email_edt.setText(Email);

        }else{
            save_btn.setText("Save");
        }
        //edit texts

        home_username_edt=findViewById(R.id.home_username_edt);
        home_email_edt=findViewById(R.id.home_email_edt);
        home_password_edt=findViewById(R.id.home_password_edt);
        home_contact_edt=findViewById(R.id.home_contact_edt);
        home_car_plate_edt=findViewById(R.id.home_car_plate_edt);


        view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_screen.this, Show_profile.class);
                startActivity(i);
            }
        });
        sign_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_screen.this,MainActivity.class);
                startActivity(i);
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating bundle for save button update action
                // home_username_edt,home_email_edt,home_password_edt,home_contact_edt,home_car_plate_edt;
                String name = home_username_edt.getText().toString();
                String email = home_email_edt.getText().toString();
                String car_plate_number=home_car_plate_edt.getText().toString();

                String contact_number= home_contact_edt.getText().toString();
                String password=home_password_edt.getText().toString();


                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = Uid;

                    updateToFireStore(id ,name, email,car_plate_number,contact_number,password);
                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id ,name, email,car_plate_number,contact_number,password);
                }

            }
        });


            }


  /*  Profile model = new Profile(snapshot.getString("id") ,
            snapshot.getString("Name") ,
            snapshot.getString("Email"),
            snapshot.getString("car plate number"),
            snapshot.getString("phone number"),
            snapshot.getString("password"));
                                list.add(model);*/
    //method to update the data
            private void updateToFireStore(String id,String name,String email,String car_plate_number,String contact_number,String password){
                //collection from firestore
                firebaseFirestore.collection("profile_details").
                        document(id).
                        update("Name" , name ,
                                "Email" ,email,
                                "car plate number", car_plate_number,
                                "contact number",contact_number,
                                "password",password)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Home_screen.this, "Data Updated!!", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(Home_screen.this, "Error : " + Objects.requireNonNull(task.getException()).getMessage() , Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Home_screen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
    private void saveToFireStore(String id, String name, String email, String contact_number, String car_plate_number, String password) {
        if (!name.isEmpty() && !password.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("id" , id);
            map.put("Name" , name);
            map.put("Email" , email);
            map.put("car plate number" ,car_plate_number);
            map.put("contact number" , contact_number);
            map.put("password" , password);

//retrive and save to firebase


            firebaseFirestore.collection("profile_details").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Home_screen.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Home_screen.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
    }


