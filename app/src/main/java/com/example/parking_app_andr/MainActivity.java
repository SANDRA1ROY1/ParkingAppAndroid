package com.example.parking_app_andr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //declaration
    Button Login_btn,Sign_up_btn;
EditText email_edt,password_Edt;
    //firestore declaration
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding our view
        Sign_up_btn = findViewById(R.id.Sign_up_btn);

        Login_btn = findViewById(R.id.Login_btn);

        //finding edit texts
        email_edt=findViewById(R.id.email_edt);

        password_Edt= findViewById(R.id.password_Edt);


        //init firestore db

        db= FirebaseFirestore.getInstance();


        Login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                switch(v.getId()){
                case R.id.Login_btn:
                    if(email_edt.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    }else if( password_Edt.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                    }
                    db.collection("profile_details")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())){
                                            String a=doc.getString("Email");
                                            String b=doc.getString("password");
                                            String a1=email_edt.getText().toString().trim();
                                            String b1=password_Edt.getText().toString().trim();
                                            assert a != null;
                                            assert b != null;
                                            if(a.equalsIgnoreCase(a1) & b.equalsIgnoreCase(b1)) {
                                                Intent home = new Intent(MainActivity.this, Home_screen.class);
                                                startActivity(home);
                                                Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                                break;
                                            }else
                                                Toast.makeText(MainActivity.this, "Cannot login,incorrect Email and Password", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                }
                            });


                    break;

                   /* case R.id.Sign_up_btn:
                    Intent register_view=new Intent(MainActivity.this,Sign_up_page.class);
                    startActivity(register_view);
                    break;*/
            }}});

        Sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,Sign_up_page.class);
                startActivity(i);
            }});}

    }