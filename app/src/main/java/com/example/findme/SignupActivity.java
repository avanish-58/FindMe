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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
EditText email;
EditText Pass;
Button signup;
Button Login;
FirebaseAuth maAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email= (EditText)findViewById(R.id.email);
        Pass=(EditText)findViewById(R.id.password);
        signup=(Button)findViewById(R.id.signup);
        Login=(Button)findViewById(R.id.login);
        maAuth=FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createuser();
                startActivity(new Intent(SignupActivity.this,loginactivity.class));
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,loginactivity.class));
            }
        });
    }
    private void createuser(){
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
            maAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignupActivity.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SignupActivity.this,"Registration error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}