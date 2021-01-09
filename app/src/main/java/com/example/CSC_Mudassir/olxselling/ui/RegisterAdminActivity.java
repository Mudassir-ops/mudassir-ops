package com.example.CSC_Mudassir.olxselling.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CSC_Mudassir.R;
import com.example.CSC_Mudassir.olxselling.model.datamodel.DatabaseHelper;

public class RegisterAdminActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);


        db = new DatabaseHelper(this);
        mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mTextCnfPassword = (EditText) findViewById(R.id.edittext_cnf_password);
        mButtonRegister = (Button) findViewById(R.id.button_register);
        mTextViewLogin = (TextView) findViewById(R.id.textview_login);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(RegisterAdminActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
            }
        });

        ///////////////
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = mTextCnfPassword.getText().toString().trim();

                if (pwd.equals(cnf_pwd)) {
                    long val = db.addUser(user, pwd);
                    if (val > 0) {
                        Toast.makeText(RegisterAdminActivity.this, "You are registered", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegisterAdminActivity.this, LoginActivity.class);
                        startActivity(moveToLogin);
                    } else {
                        Toast.makeText(RegisterAdminActivity.this, "Registeration Error", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterAdminActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}