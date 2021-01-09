package com.example.CSC_Mudassir.freelancing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CSC_Mudassir.R;
import com.example.CSC_Mudassir.olxselling.ui.LoginActivity;
import com.example.CSC_Mudassir.olxselling.ui.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FreeLancingHomeActivity extends AppCompatActivity {

    Button mTechaerLogin, mStudentLogin;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_lancing_home);
        mTechaerLogin = findViewById(R.id.TechaerLogin);
        mStudentLogin = findViewById(R.id.StudentLogin);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null){
            startActivity(new Intent(FreeLancingHomeActivity.this,TeacherProfileActivity.class));
        }else {
            mTechaerLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FreeLancingHomeActivity.this, SellingLoginActivity.class);
                    intent.putExtra(SellingLoginActivity.LOGIN_KEY, "TEACHER");
                    startActivity(intent);
                }
            });

            mStudentLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FreeLancingHomeActivity.this, SellingLoginActivity.class);
                    intent.putExtra(SellingLoginActivity.LOGIN_KEY, "STUDENT");
                    startActivity(intent);
                }
            });

        }


     /*   mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mButtonLogin = (Button) findViewById(R.id.button_login);
        mTextViewRegister = (TextView) findViewById(R.id.textview_register);
        progressBar = findViewById(R.id.progressBar1);
        fAuth = FirebaseAuth.getInstance();
        *//*mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });*//*

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _user = mTextUsername.getText().toString().trim();
                String _email = mTextPassword.getText().toString().trim();

                fAuth.signInWithEmailAndPassword(_user, _email).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            if (fAuth.getCurrentUser().isEmailVerified()) {
                            //    Toast.makeText(FreeLancingLoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), StartActivity.class));

                            } else {
                              //  Toast.makeText(FreeLancingLoginActivity.this, "Please Verify Your Email First to Login Succcessfully", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                          //  Toast.makeText(FreeLancingLoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });


    }*/
  /*  public void showProgressingView() {

        if (!isProgressShowing) {
            View view=findViewById(R.id.progressBar1);
            view.bringToFront();
        }
    }

    public void hideProgressingView() {
        View v = this.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }
*/
    }
}