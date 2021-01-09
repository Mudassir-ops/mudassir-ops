package com.example.CSC_Mudassir.olxselling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.CSC_Mudassir.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Test extends AppCompatActivity {
    public static final String currentrefrence = "CURRENT_REFRENCE";
    FirebaseFirestore db;
    FirebaseUser firebaseUser;
    String current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String current_Refrence = getIntent().getStringExtra(currentrefrence);
        Toast.makeText(getApplicationContext(), "a dsa" + current_Refrence, Toast.LENGTH_SHORT).show();

          db= FirebaseFirestore.getInstance();

        //  firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //  current_user = firebaseUser.getUid();

        db.collection("users").document(current_user).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                 if(task.isSuccessful()&& task.getResult()!=null){
                                     String email = task.getResult().getString("Email");
                                            Toast.makeText(getApplicationContext(),"currrent user email:"+email,Toast.LENGTH_SHORT).show();
                                 }else{
                                     Toast.makeText(getApplicationContext(),"No such document", Toast.LENGTH_LONG).show();
                                 }
            }
        });
    }
}