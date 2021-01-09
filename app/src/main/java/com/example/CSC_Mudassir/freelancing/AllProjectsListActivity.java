package com.example.CSC_Mudassir.freelancing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.CSC_Mudassir.R;
import com.example.CSC_Mudassir.olxselling.model.datamodel.Items;
import com.example.CSC_Mudassir.olxselling.ui.HomeActivity;
import com.example.CSC_Mudassir.olxselling.ui.Item_DetailActivity;
import com.example.CSC_Mudassir.olxselling.ui.MyViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllProjectsListActivity extends AppCompatActivity {
    private DatabaseReference DataRef;
    private RecyclerView recyclerView;
    private ArrayList<Project> arrayList;
    private SwipeRefreshLayout refreshView;
    private EditText inputSearch;

    FirebaseRecyclerOptions<Project> options;
    FirebaseRecyclerAdapter<Project, MyAllProjectViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_projects_list);
        inputSearch = findViewById(R.id.inputSearch);
        DataRef = FirebaseDatabase.getInstance().getReference().child("Projects");

        recyclerView = findViewById(R.id.ProjectRecyclerView);
        arrayList = new ArrayList<Project>();
        refreshView = findViewById(R.id.refreshView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.hasFixedSize();
        LoadData("");
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                LoadData("");
                Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_LONG).show();
                refreshView.setRefreshing(false);

            }
        });
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null) {
                    LoadData(s.toString());
                } else {
                    LoadData("");
                }
            }
        });
    }
    private void LoadData(String data) {
        Query query = DataRef.orderByChild("ProjectName").startAt(data).endAt(data + "\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<Project>().setQuery(query, Project.class).build();
        adapter = new FirebaseRecyclerAdapter<Project, MyAllProjectViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAllProjectViewHolder holder, final int position, @NonNull Project model) {
                recyclerView.smoothScrollToPosition(position);
                holder.projectName.setText(model.getProjectName());
                holder.teacherDepartment.setText(model.getTeacherDepartment());
                holder.teacherContactNo.setText(model.getTeacherContactNo());

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AllProjectsListActivity.this, ProjectDetailActivity.class);
                        intent.putExtra(ProjectDetailActivity.PROJECT_KEY, getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }
            @NonNull
            @Override
            public MyAllProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_project_layout, parent, false);
                return new MyAllProjectViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}