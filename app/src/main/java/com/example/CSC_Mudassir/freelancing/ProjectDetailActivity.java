package com.example.CSC_Mudassir.freelancing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.CSC_Mudassir.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProjectDetailActivity extends AppCompatActivity {
    public static final String PROJECT_KEY = "Project_Key";
    private DatabaseReference DataRef;

    private EditText projectName,projectDescription,teacherContact,teacherEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        DataRef = FirebaseDatabase.getInstance().getReference().child("Projects");

        projectName=findViewById(R.id.editTextTextPersonName);
        projectDescription=findViewById(R.id.editTextTextPersonName4);
        teacherContact=findViewById(R.id.editTextTextPersonName2);
        teacherEmail=findViewById(R.id.editTextTextPersonName3);

        Intent intent=getIntent();
        String projectKey=intent.getStringExtra(PROJECT_KEY);
        Log.d("data",""+projectKey);

        try {
            DataRef.child(projectKey).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        projectName.setText( snapshot.child("ProjectName").getValue().toString());
                        projectDescription.setText(snapshot.child("ProjectDescription").getValue().toString());

                        teacherContact.setText(  snapshot.child("TeacherContactNo").getValue().toString());
                        teacherEmail.setText( snapshot.child("TeacherEmail").getValue().toString());
                    }


                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Database Error!", "" + error);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}