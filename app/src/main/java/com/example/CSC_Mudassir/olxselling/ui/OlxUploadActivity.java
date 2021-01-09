package com.example.CSC_Mudassir.olxselling.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class OlxUploadActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE = 101;
    private ImageView imageViewAdd;
    private EditText editTextInput;
    private EditText editTextPrice;
    private EditText editTextdescription;
    private TextView textViewProgressBar;
    private ProgressBar progressBar;
    private Button buttonUpload;
    private Button log_out;
    private FirebaseAuth firebaseAuth;
    Uri imageUri;
    boolean isImageAdded = false;

    DatabaseReference DataRef;
    StorageReference StorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewAdd = (ImageView) findViewById(R.id.imageview);
        editTextInput = (EditText) findViewById(R.id.inputImageName);
        editTextPrice = (EditText) findViewById(R.id.price_main);
        editTextdescription = (EditText) findViewById(R.id.Description);
        textViewProgressBar = (TextView) findViewById(R.id.textViewProgress);
        progressBar = (ProgressBar) findViewById(R.id.progressbBar);
        buttonUpload = (Button) findViewById(R.id.uploadButton);
        log_out = (Button) findViewById(R.id.log_out);

        textViewProgressBar.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);


        DataRef = FirebaseDatabase.getInstance().getReference().child("Items");
        StorageRef = FirebaseStorage.getInstance().getReference().child("CarImages");
        firebaseAuth = FirebaseAuth.getInstance();

        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String imageName = editTextInput.getText().toString().toLowerCase();
                String price = editTextPrice.getText().toString();
                String decription = editTextdescription.getText().toString();
                if(isImageAdded!=false && !TextUtils.isEmpty((imageName))  && !TextUtils.isEmpty(price) ){
                    uploadImage(imageName,price,decription);
                }
            }


        });
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.getInstance().signOut();
                startActivity(new Intent(OlxUploadActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    private void uploadImage(final String imageName, final String price, final String Description) {
        textViewProgressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        final String key = DataRef.push().getKey();
        StorageRef.child(key + ".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageRef.child(key + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap();

                        hashMap.put("ItemName",  imageName);
                        hashMap.put("ImageUri", uri.toString());
                        hashMap.put("ItemPrice",price);
                        hashMap.put("ItemDescription",Description);
                        hashMap.put("timeStamp", ServerValue.TIMESTAMP);

                        //now we stire the data in Database for further used in recyerlView
                        DataRef.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                  Toast.makeText(getApplicationContext(),"Data Successfuly Stored in Realtime DataBase",Toast.LENGTH_LONG).show();
                               // startActivity(new Intent(MainActivity.this, Home.class));

                            }
                        });

                    }
                });


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (snapshot.getBytesTransferred() * 100) / snapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);
                textViewProgressBar.setText(progress + " % ");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && data != null) {
            imageUri = data.getData();
            isImageAdded = true;
            imageViewAdd.setImageURI(imageUri);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            startActivity(new Intent(OlxUploadActivity.this
            , HomeActivity.class));

    }
}