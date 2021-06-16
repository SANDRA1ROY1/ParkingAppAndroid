package com.example.parkingappandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.parkingappandroid.Adapters.SFragmentPageAdapter;
import com.example.parkingappandroid.databinding.ActivityMain1Binding;
import com.example.parkingappandroid.databinding.ActivityMainBinding;
import com.example.parkingappandroid.databinding.FragmentPageBinding;
import com.example.parkingappandroid.viewmodel.ProfileViewModel;


public class MainActivity1 extends AppCompatActivity {
    @NonNull ActivityMain1Binding binding;
    FragmentPageBinding binding2;

    private ProfileViewModel profileViewModel;

public int tabPos=0;
public String user="";

    SFragmentPageAdapter adapter;

    String toast="";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = ActivityMain1Binding.inflate(getLayoutInflater());

        setContentView(this.binding.getRoot());

binding2=FragmentPageBinding.inflate(getLayoutInflater());
profileViewModel=ProfileViewModel.getInstance(getApplication());


        adapter=new SFragmentPageAdapter(getSupportFragmentManager(), MainActivity1.this);
        binding.viewpager.setAdapter(adapter);


        binding.slidingTabs.setupWithViewPager(binding.viewpager);
        SharedPreferences sp=getApplicationContext().getSharedPreferences("UserPrefs",MODE_PRIVATE);
        user=sp.getString("username",".@gmail.com");



        Intent i = getIntent();
       toast=i.getStringExtra("toast");
       Log.d("Toast","tOAST- "+toast);
       if(toast != null) {
           if (!toast.equals("")) {
               Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
           }
       }






    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);



        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                //
                Intent i=new Intent(MainActivity1.this,Home_screen.class);
                startActivity(i);
            }
            break;
            case R.id.action_signOut: {
                //
               Intent i=new Intent(MainActivity1.this,MainActivity.class);
               startActivity(i);

            }
            break;




        }

        return super.onOptionsItemSelected(item);
    }


}