package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginactivity extends AppCompatActivity {
    EditText email;
    EditText Pass;
    Button signup;
    Button Login;
    FirebaseAuth maAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        email= (EditText)findViewById(R.id.email);
        Pass=(EditText)findViewById(R.id.password);
        signup=(Button)findViewById(R.id.signup);
        Login=(Button)findViewById(R.id.login);
        maAuth=FirebaseAuth.getInstance();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnlogin();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginactivity.this,SignupActivity.class));
            }
        });
    }
    private void btnlogin(){
        String mail=email.getText().toString().trim();
        String password=Pass.getText().toString().trim();
        if(TextUtils.isEmpty(mail)){
            email.setError("Email cannot be empty");
            email.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            Pass.setError("Password Cannot be empty");
            Pass.requestFocus();
        }
        else{
            maAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(loginactivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(loginactivity.this,MainActivity.class));
                    }
                    else{
                        Toast.makeText(loginactivity.this, "Login failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}