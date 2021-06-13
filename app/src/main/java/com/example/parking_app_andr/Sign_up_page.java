package com.example.parking_app_andr;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parking_app_andr.viewmodel.ProfileViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.text.TextUtils.isEmpty;

public class Sign_up_page extends AppCompatActivity {
    private ProfileViewModel profileViewModel;
    //firestore essentials
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;

    Button Register_btn;
    EditText u_name,u_email,u_phone_num,u_car_plate_num,u_pass_word,u_pass_word_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_sign_up_page);
        Register_btn = findViewById(R.id.Register_btn);
        u_name = findViewById(R.id.u_name);
        u_email = findViewById(R.id.u_email);
        u_phone_num = findViewById(R.id.u_phone_num);
        u_car_plate_num = findViewById(R.id.u_car_plate_num);
        u_pass_word = findViewById(R.id.u_pass_word);
        u_pass_word_check=findViewById(R.id.u_pass_word_check);
        //init

        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("profile_details").document();


//    EditText u_name,u_email,u_phone_num,u_car_plate_num,u_pass_word;
        Register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // checkDataEntered();
                    if(u_name.getText().toString().equals("")) {
                        Toast.makeText(Sign_up_page.this, "Please type a username", Toast.LENGTH_SHORT).show();

                    }else if(u_email.getText().toString().equals("")) {
                        Toast.makeText(Sign_up_page.this, "Please type an email id", Toast.LENGTH_SHORT).show();

                    }
                    else if(u_phone_num.getText().toString().equals("")){
                        Toast.makeText(Sign_up_page.this, "Please type a phone number", Toast.LENGTH_SHORT).show();
                    }
                    else if(u_car_plate_num.getText().toString().equals("")){
                        Toast.makeText(Sign_up_page.this, "Please type a user car plate number", Toast.LENGTH_SHORT).show();
                    }
                    else if(u_pass_word.getText().toString().equals("")){
                        Toast.makeText(Sign_up_page.this, "Please type a password", Toast.LENGTH_SHORT).show();

                    }else if(!u_pass_word_check.getText().toString().equals(u_pass_word_check.getText().toString())){
                        Toast.makeText(Sign_up_page.this, "Password mismatch", Toast.LENGTH_SHORT).show();

                    }else {

                        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                if (documentSnapshot.exists())
                                {
                                    Toast.makeText(Sign_up_page.this, "Sorry,this user exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    //    EditText u_name,u_email,u_phone_num,u_car_plate_num,u_pass_word;

                                    Map<String, Object> reg_entry = new HashMap<>();
                                    reg_entry.put("Name", u_name.getText().toString());
                                    reg_entry.put("Email", u_email.getText().toString());
                                    reg_entry.put("phone number", u_phone_num.getText().toString());
                                    reg_entry.put("car plate number", u_car_plate_num.getText().toString());
                                    reg_entry.put("password", u_pass_word.getText().toString());
                                    reg_entry.put("final Password", u_pass_word_check.getText().toString());


                                   //   String myId = ref.getId();
                                    firebaseFirestore.collection("profile_details")
                                            .add(reg_entry)
                                            .addOnSuccessListener(documentReference -> Toast.makeText(Sign_up_page.this, "Successfully added", Toast.LENGTH_SHORT).show())
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("Error", e.getMessage());
                                                }
                                            });
                                }
                            }
                        });
                    }
                }
            });

            }
        }


   /* boolean isEmail(EditText u_name) {
        CharSequence email = u_name.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText u_car_plate_num) {
        CharSequence str = u_car_plate_num.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered() {
        if (isEmpty(u_name)) {
            Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmpty(u_pass_word)) {
            u_pass_word.setError("Password is required!");
        }
        if (!isEmail(u_email)) {
            u_email.setError("Enter valid email!");
        }
        if (isEmpty(u_car_plate_num)) {
            u_pass_word.setError("Car plate number is required!");
        }*/

