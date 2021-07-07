package com.example.expense_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPass;
    private Button btnLogin;
    private TextView mForgetPassword;
    private TextView mSignUpHere;

    private ProgressDialog mDialog;


    //Firebase
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }

        mDialog=new ProgressDialog(this);

        loginDetails();

    }

    private void loginDetails(){
        mEmail=findViewById(R.id.email_login);
        mPass=findViewById(R.id.password_login);
        btnLogin=findViewById(R.id.btn_login);
        mForgetPassword=findViewById(R.id.forget_password);
        mSignUpHere= findViewById(R.id.sign_up);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String email = mEmail.getText().toString().trim();
              String pass = mPass.getText().toString().trim();

              if(TextUtils.isEmpty(email)){
                  mEmail.setError("Email Required..");
                  return;
              }
              if(TextUtils.isEmpty(pass)){
                  mPass.setError("Password Required..");

              }

              mDialog.setMessage("Processing..");
              mDialog.show();

              mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){
                          mDialog.dismiss();
                          startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                          Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                      }
                      else{
                          mDialog.dismiss();
                          Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();

                      }
                  }
              });

            }
        });


        //Registration Activity
        mSignUpHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });
        //Reset Password Activity
            mForgetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   startActivity(new Intent(getApplicationContext(),ResetActivity.class));
                }
            });


    }

}