package com.example.CSC_Mudassir.freelancing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CSC_Mudassir.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class MakeProjectActivity extends AppCompatActivity {

    EditText project_Name, project_Description, teacher_ContactNo, teacher_Department;
    TextView textViewProgressBar;
    ProgressBar progressBar;
    Button upload;
    DatabaseReference DataRef;
    StorageReference StorageRef;
    FirebaseAuth firebaseAuth;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_project);

        project_Name = findViewById(R.id.projectName);
        project_Description = findViewById(R.id.projectDescription);
        teacher_ContactNo = findViewById(R.id.contactNo);
        teacher_Department = findViewById(R.id.teacherDepartment);
        textViewProgressBar = (TextView) findViewById(R.id.textViewProgress);
        progressBar = (ProgressBar) findViewById(R.id.progressbBar);
        upload = findViewById(R.id.uploadButton);

        DataRef = FirebaseDatabase.getInstance().getReference().child("Projects");
        //StorageRef = FirebaseStorage.getInstance().getReference().child("CarImages");
        firebaseAuth = FirebaseAuth.getInstance();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String projectName = project_Name.getText().toString().toLowerCase();
                String projectDescription = project_Description.getText().toString();
                String teacherContactNO = teacher_ContactNo.getText().toString();
                String teacherDepartment = teacher_Department.getText().toString();
                if (teacherContactNO.isEmpty()) {
                    teacher_ContactNo.setError("Contact Number Required");
                    return;
                }
                if (projectName.isEmpty()) {
                    project_Name.setError("Project Name Required");
                    return;
                }
                if (!(projectName.isEmpty() && teacherContactNO.isEmpty())) {
                    uploadData(projectName, projectDescription, teacherContactNO, teacherDepartment);
                }

            }
        });
    }

    private void uploadData(final String projectName, final String projectDescription, final String teacherConatctNo, final String teacherDepartment) {
        textViewProgressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        final String key = DataRef.push().getKey();
        HashMap hashMap = new HashMap();
        hashMap.put("ProjectName", projectName);
        hashMap.put("ProjectDescription", projectDescription);
        hashMap.put("TeacherContactNo", teacherConatctNo);
        hashMap.put("TeacherDepartment", teacherDepartment);
        hashMap.put("TeacherEmail", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        hashMap.put("timeStamp", ServerValue.TIMESTAMP);
        hashMap.put("PrimaryID", key);

        //now we set the data in Database
        DataRef.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Data Successfuly Stored in  DataBase", Toast.LENGTH_LONG).show();

                // startActivity(new Intent(MainActivity.this, Home.class));

            }
        });


    }
}