package com.example.CSC_Mudassir.olxselling.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.CSC_Mudassir.olxselling.model.adapter.AdminAdapter;
import com.example.CSC_Mudassir.olxselling.model.datamodel.Items;
import com.example.CSC_Mudassir.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AdminActivity extends AppCompatActivity {
    private DatabaseReference DataRef;
    private RecyclerView recyclerView;
    private AdminAdapter adminAdapter;
    private ArrayList<Items> arrayList;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        floatingActionButton = findViewById(R.id.floating_button_admin);
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "This is Admin activity", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());

            }
        });
        DataRef = FirebaseDatabase.getInstance().getReference().child("Items");
        recyclerView = findViewById(R.id.AdminRecyclerView);
        adminAdapter = new AdminAdapter(this);
        arrayList = new ArrayList<Items>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.hasFixedSize();
        // Query query = FirebaseDatabase.getInstance().getReference().child("CarName").orderByChild("CarName");
        //Date c = Calendar.getInstance().getTime();
        //current time - 86400000
        // Query query = DataRef.orderByChild("CarName").startAt(c.getDate()).endAt("\uf8ff");
        //     long before24Hour = new Date().getTime()- 86400000;
        long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(15, TimeUnit.DAYS);
        long cutoff1 = new Date().getTime() - TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS);
        Log.d("cutoff", "cutoff:" + cutoff);
        Query query1 = DataRef.orderByChild("timeStamp").endAt(cutoff1);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Items items = postSnapshot.getValue(Items.class);
                    Log.d("data", "" + postSnapshot);
                    arrayList.add(items);
                }
                adminAdapter.setAdminData(arrayList);
                recyclerView.setAdapter(adminAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Database Error!", "" + error);
            }
        });

    /*    Items items = new Items();
        items.setCarName("Mudassir");
        items.setImageUri(String.valueOf(R.drawable.car));
        Items items1 = new Items();
        items1.setCarName("Mudassir");
        items1.setImageUri(String.valueOf(R.drawable.car));
        arrayList.add(items);
        arrayList.add(items1);*/


        //---------------Poulate first alld ata from firebase

    }
}