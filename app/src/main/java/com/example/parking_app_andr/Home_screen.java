package com.example.parking_app_andr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.parking_app_andr.model.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class Home_screen extends AppCompatActivity {

    Button sign_out_btn,view_profile;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    TextView username_tv;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        username_tv=findViewById(R.id.username_tv);
        view_profile=findViewById(R.id.view_profile);
        sign_out_btn=findViewById(R.id.sign_out_btn);
        firebaseFirestore= FirebaseFirestore.getInstance();

        firebaseFirestore=FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = firebaseFirestore.collection("profile_details").document("profile_details");
                ref.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        assert doc != null;
                        String fields = "" + "Email: " + doc.get("Email") ;

                               /* +"\nEmail: " + doc.get("Email") +
                                "\nPhone: " + doc.get("Phone");*/
                        username_tv.setText(fields);
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });
        sign_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_screen.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}