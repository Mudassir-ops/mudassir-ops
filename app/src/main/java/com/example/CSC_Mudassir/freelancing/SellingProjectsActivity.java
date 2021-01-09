package com.example.CSC_Mudassir.freelancing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.CSC_Mudassir.R;
import com.example.CSC_Mudassir.olxselling.model.adapter.AdminAdapter;
import com.example.CSC_Mudassir.olxselling.model.datamodel.Items;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SellingProjectsActivity extends AppCompatActivity {
    private DatabaseReference DataRef;
    private RecyclerView recyclerView;
    private ProjectAdapter projectAdapter;
    private ArrayList<Project> arrayList;
    private SwipeRefreshLayout refreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_projects);
        DataRef = FirebaseDatabase.getInstance().getReference().child("Projects");
        /*String currrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();*/

        recyclerView = findViewById(R.id.ProjectRecyclerView);
        projectAdapter = new ProjectAdapter(this);
        arrayList = new ArrayList<Project>();
        refreshView = findViewById(R.id.refreshView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.hasFixedSize();
        lodadData();

        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                lodadData();
                Toast.makeText(getApplicationContext(),"Refresh",Toast.LENGTH_LONG).show();
                refreshView.setRefreshing(false);

            }
        });

    }

    private void lodadData() {
        try {
            DataRef.orderByChild("TeacherEmail").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Project projects = postSnapshot.getValue(Project.class);
                        arrayList.add(projects);

                    }

                    projectAdapter.setAdminData(arrayList);
                    recyclerView.setAdapter(projectAdapter);
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