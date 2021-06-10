package com.example.parking_app_andr;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.text.TextUtils.isEmpty;

public class Sign_up_page extends AppCompatActivity {

    Button Home_btn;
    EditText u_name,u_email,u_phone_num,u_car_plate_num,u_pass_word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        Home_btn = findViewById(R.id.home_btn);
        u_name = findViewById(R.id.u_name);
        u_email = findViewById(R.id.u_email);
        u_phone_num = findViewById(R.id.u_phone_num);
        u_car_plate_num = findViewById(R.id.u_car_plate_num);
        u_pass_word = findViewById(R.id.u_pass_word);


        Home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();

            }
        });

    }

    boolean isEmail(EditText u_name) {
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
        }

    }

}
