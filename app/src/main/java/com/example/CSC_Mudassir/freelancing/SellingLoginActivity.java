package com.example.CSC_Mudassir.freelancing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.CSC_Mudassir.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SellingLoginActivity extends AppCompatActivity {
    public static final String LOGIN_KEY = "login_key";

    TextInputEditText userEmail, userPassword;
    Button loginSelling;
    FirebaseAuth fAuth;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_login);
        userEmail = findViewById(R.id.userNameSelling);
        userPassword = findViewById(R.id.userPasswordSelling);
        loginSelling = findViewById(R.id.button_login_Selling);
        linearLayout = findViewById(R.id.not_Registered);
        fAuth = FirebaseAuth.getInstance();


        Intent intent = getIntent();
        String userkey = intent.getStringExtra(LOGIN_KEY);

        if (userkey.equals("TEACHER")) {
            loginSelling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uEmail = userEmail.getText().toString().trim();
                    String password = userPassword.getText().toString().trim();

                    if (TextUtils.isEmpty(uEmail)) {
                        userEmail.setError("Email is Required.");
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        userPassword.setError("Password is Required.");
                        return;
                    }
                    if (password.length() < 6) {
                        userPassword.setError("Password Must be >= 6 Characters");
                        return;
                    }
                    fAuth.signInWithEmailAndPassword(uEmail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (fAuth.getCurrentUser().isEmailVerified()) {
                                    Intent intent1 = new Intent(SellingLoginActivity.this, TeacherProfileActivity.class);
                                    startActivity(intent1);

                                } else {
                                    Toast.makeText(SellingLoginActivity.this, "Please Verify Your Email First to Login Succcessfully", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(SellingLoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                // progressBar.setVisibility(View.GONE);
                            }
                        }
                    });


                }
            });
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(SellingLoginActivity.this, RegisterationSellingActivity.class);
                    intent1.putExtra(RegisterationSellingActivity.USER_SIGN_KEY,"TEACHER");
                    startActivity(intent1);

                }
            });

        } else {
            loginSelling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uEmail = userEmail.getText().toString().trim();
                    String password = userPassword.getText().toString().trim();

                    if (TextUtils.isEmpty(uEmail)) {
                        userEmail.setError("Email is Required.");
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        userPassword.setError("Password is Required.");
                        return;
                    }
                    if (password.length() < 6) {
                        userPassword.setError("Password Must be >= 6 Characters");
                        return;
                    }
                    fAuth.signInWithEmailAndPassword(uEmail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (fAuth.getCurrentUser().isEmailVerified()) {
                                    Toast.makeText(SellingLoginActivity.this, "Student Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(SellingLoginActivity.this, StudentProfileActivity.class);
                                    startActivity(intent1);

                                } else {
                                    Toast.makeText(SellingLoginActivity.this, "Please Verify Your Email First to Login Succcessfully", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Toast.makeText(SellingLoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                // progressBar.setVisibility(View.GONE);
                            }
                        }
                    });


                }
            });

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(SellingLoginActivity.this, RegisterationSellingActivity.class);
                    intent1.putExtra(RegisterationSellingActivity.USER_SIGN_KEY,"STUDENT");
                    startActivity(intent1);

                }
            });


        }


    }
}