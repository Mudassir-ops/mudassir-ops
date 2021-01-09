package com.example.CSC_Mudassir.freelancing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.CSC_Mudassir.R;
import com.example.CSC_Mudassir.olxselling.ui.HomeActivity;
import com.example.CSC_Mudassir.olxselling.ui.OlxUploadActivity;
import com.example.CSC_Mudassir.olxselling.ui.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

public class TeacherProfileActivity extends AppCompatActivity {


    private static final String TAG = "tag";
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore firebaseFirestore;

    Button myProjects,makeProjects;
    TextView tecaher_Name,teacher_Department;
    ImageView teacherProfilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecaher_profile);

        myProjects=findViewById(R.id.my_Projects);
        makeProjects=findViewById(R.id.make_Projects);
        tecaher_Name=findViewById(R.id.teacher_Name);
        teacher_Department=findViewById(R.id.teacher_Department);
        teacherProfilePic=findViewById(R.id.teacher_Profile_Pic);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().isEmailVerified()) {

            //------------setting of Teacher Profile
            firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String firstName = task.getResult().getString("f_Name");
                        String email = task.getResult().getString("Email");
                        String password = task.getResult().getString("Password");
                        tecaher_Name.setText(firstName);
                        teacher_Department.setText(email);

                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });

             myProjects.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                  startActivity(new Intent(TeacherProfileActivity.this,SellingProjectsActivity.class));
                 }
             });

             makeProjects.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                      startActivity(new Intent(TeacherProfileActivity.this,MakeProjectActivity.class));
                 }
             });

            ////main is here where logged in user upload his data
            /*Intent intent = new Intent(HomeActivity.this, OlxUploadActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);*/

           /* Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);*/


        }
    }
}