package com.example.CSC_Mudassir.freelancing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CSC_Mudassir.R;
import com.example.CSC_Mudassir.olxselling.ui.LoginActivity;
import com.example.CSC_Mudassir.olxselling.ui.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterationSellingActivity extends AppCompatActivity {


    public static final String USER_SIGN_KEY = "user_sign_in_Key";

    private TextInputEditText uName, uPassword, uEmail;
    private Button button_Signin_Selling;
    FirebaseAuth firebaseAuth;
    //String userId;
    ProgressBar progressDialog;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_selling);
        uName = findViewById(R.id.userNameSelling);
        uEmail = findViewById(R.id.userEmailSelling);
        uPassword = findViewById(R.id.userPasswordSelling);
        button_Signin_Selling=findViewById(R.id.button_Signin_Selling);
        progressDialog=findViewById(R.id.progressBarRegistration);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String userkey = intent.getStringExtra(USER_SIGN_KEY);

        if (userkey == "TEACHER") {
                 button_Signin_Selling.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                    final String uname=uName.getText().toString().trim();
                    final String uemail=uEmail.getText().toString().trim();
                    final String upassowrd=uPassword.getText().toString();
                    if(TextUtils.isEmpty(uname)){
                        uName.setError("Name is Required");
                        return;
                    }
                         if (TextUtils.isEmpty(uemail)) {
                             uEmail.setError("Email is Required.");
                             return;
                         }
                         if (TextUtils.isEmpty(upassowrd)) {
                             uPassword.setError("Password is Required.");
                             return;
                         }
                         if (uPassword.length() < 6) {
                             uPassword.setError("Password Must be >= 6 Characters");
                             return;
                         }
                         progressDialog.setVisibility(View.VISIBLE);
                         firebaseAuth.createUserWithEmailAndPassword(uemail, upassowrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isSuccessful()) {
                                     progressDialog.setVisibility(View.GONE);
                                     //   Toast.makeText(SignIn.this,"user crseated",Toast.LENGTH_SHORT).show();
                                   final String userId = firebaseAuth.getCurrentUser().getUid();
                                     firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             if (task.isSuccessful()) {
                                                 Toast.makeText(getApplicationContext(), "User Ceated regsterd plz verfiy using email", Toast.LENGTH_SHORT).show();
                                                 uEmail.setText("");
                                                 uPassword.setText("");
                                                 DocumentReference documentReference = firebaseFirestore.collection("users").document(userId);
                                                 HashMap<String, Object> user = new HashMap<>();
                                                 user.put("f_Name", uname);
                                                 user.put("Email", uemail);
                                                 user.put("Password",upassowrd);
                                                 //     user.put("Phone_no", phone);
                                                 documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                     @Override
                                                     public void onSuccess(Void aVoid) {

                                                     }
                                                 });
                                                 startActivity(new Intent(RegisterationSellingActivity.this, SellingLoginActivity.class));
                                             } else {
                                                 Toast.makeText(RegisterationSellingActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     });
                                     //  startActivity(new Intent(Register.this,Home.class));

                                 } else {
                                     Toast.makeText(RegisterationSellingActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                     progressDialog.setVisibility(View.GONE);
                                 }

                             }
                         });
                     }
                 });

        } else {
            button_Signin_Selling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String uname=uName.getText().toString().trim();
                    final String uemail=uEmail.getText().toString().trim();
                    final String upassowrd=uPassword.getText().toString();
                    if(TextUtils.isEmpty(uname)){
                        uName.setError("Name is Required");
                        return;
                    }
                    if (TextUtils.isEmpty(uemail)) {
                        uEmail.setError("Email is Required.");
                        return;
                    }
                    if (TextUtils.isEmpty(upassowrd)) {
                        uPassword.setError("Password is Required.");
                        return;
                    }
                    if (uPassword.length() < 6) {
                        uPassword.setError("Password Must be >= 6 Characters");
                        return;
                    }
                    progressDialog.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(uemail, upassowrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.setVisibility(View.GONE);
                                //   Toast.makeText(SignIn.this,"user crseated",Toast.LENGTH_SHORT).show();
                                final String userId = firebaseAuth.getCurrentUser().getUid();
                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "User Ceated regsterd plz verfiy using email", Toast.LENGTH_SHORT).show();
                                            uEmail.setText("");
                                            uPassword.setText("");
                                            DocumentReference documentReference = firebaseFirestore.collection("users").document(userId);
                                            HashMap<String, Object> user = new HashMap<>();
                                            user.put("f_Name", uname);
                                            user.put("Email", uemail);
                                            user.put("Password",upassowrd);
                                            //     user.put("Phone_no", phone);
                                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            });
                                            startActivity(new Intent(RegisterationSellingActivity.this, SellingLoginActivity.class));
                                        } else {
                                            Toast.makeText(RegisterationSellingActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                //  startActivity(new Intent(Register.this,Home.class));

                            } else {
                                Toast.makeText(RegisterationSellingActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.setVisibility(View.GONE);
                            }

                        }
                    });

                }
            });

        }

    }
}