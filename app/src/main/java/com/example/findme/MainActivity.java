package com.example.findme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
FirebaseAuth mauth;
Button btn;
Button register,find;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      mauth=FirebaseAuth.getInstance();
     btn=(Button)findViewById(R.id.btn1);
     register=(Button)findViewById(R.id.register);
     find=(Button)findViewById(R.id.find);
     btn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             mauth.signOut();
             startActivity(new Intent(MainActivity.this,loginactivity.class));
         }
     });
     register.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startActivity(new Intent(MainActivity.this,RegisterMissing.class));
         }
     });
     find.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
startActivity(new Intent(MainActivity.this,FindActivity.class));
         }
     });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mauth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(MainActivity.this,loginactivity.class));
        }
    }
}