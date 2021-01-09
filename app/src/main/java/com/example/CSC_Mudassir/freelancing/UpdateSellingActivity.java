package com.example.CSC_Mudassir.freelancing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CSC_Mudassir.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UpdateSellingActivity extends AppCompatActivity {
    public static final String PROJECT_ID = "Project_Id";
    EditText project_Name, project_Description, teacher_ContactNo, teacher_Department;
    Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_selling);
        ColorDrawable colorDrawable=new ColorDrawable(Color.TRANSPARENT);
        getWindow().setBackgroundDrawable(colorDrawable);
        getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        project_Name = findViewById(R.id.projectName);
        project_Description = findViewById(R.id.projectDescription);
        teacher_ContactNo = findViewById(R.id.contactNo);
        teacher_Department = findViewById(R.id.teacherDepartment);

        upload = findViewById(R.id.uploadButton);

        Intent intent = getIntent();
        final String mProjectId = intent.getStringExtra(PROJECT_ID);
        Log.d("ProjectId", "" + mProjectId);

        final DatabaseReference DataRef = FirebaseDatabase.getInstance().getReference().child("Projects");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String projectName = project_Name.getText().toString().trim();
                final String projectDescription = project_Description.getText().toString().trim();
                final  String teacherContactNo = teacher_ContactNo.getText().toString().trim();
                final String teacherDepartment = teacher_Department.getText().toString().trim();
                Query query = DataRef.orderByChild("PrimaryID").equalTo((String) mProjectId);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ds.getRef().child("ProjectName").setValue(projectName);
                            ds.getRef().child("ProjectDescription").setValue(projectDescription);
                            ds.getRef().child("TeacherContactNo").setValue(teacherContactNo);
                            ds.getRef().child("TeacherDepartment").setValue(teacherDepartment);
                        }
                        startActivity(new Intent(UpdateSellingActivity.this,SellingProjectsActivity.class));
                        Toast.makeText(getApplicationContext(), "Project Updated", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
}