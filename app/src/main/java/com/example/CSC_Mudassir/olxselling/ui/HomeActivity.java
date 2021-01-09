package com.example.CSC_Mudassir.olxselling.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.CSC_Mudassir.olxselling.model.datamodel.Items;
import com.example.CSC_Mudassir.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    FloatingActionButton floatingActionButton, floatingActionButton2;

    FirebaseRecyclerOptions<Items> options;
    FirebaseRecyclerAdapter<Items, MyViewHolder> adapter;

    EditText inputSearch;
    DatabaseReference DataRef;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "This Is Home activity", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_button);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.floating_button2);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        recyclerView.setHasFixedSize(true);
        // recyclerView.setNestedScrollingEnabled(false);
        firebaseAuth = FirebaseAuth.getInstance();
        DataRef = FirebaseDatabase.getInstance().getReference().child("Items");
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().isEmailVerified()) {
                    ////main is here where logged in user upload his data
                    Intent intent = new Intent(HomeActivity.this, OlxUploadActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
        LoadData("");
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString() != null) {
                    LoadData(editable.toString());
                } else {
                    LoadData("");
                }
            }
        });
    }

    private void LoadData(String data) {

        Query query = DataRef.orderByChild("ItemName").startAt(data).endAt(data + "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<Items>().setQuery(query, Items.class).build();

        adapter = new FirebaseRecyclerAdapter<Items, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull Items model) {
                recyclerView.smoothScrollToPosition(position);
                holder.textView_card.setText(model.getItemName() + "$");
                Picasso.get().load(model.getImageUri()).into(holder.imageView_card);
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                     /*   Intent intent=new Intent(Home.this,Test.class);
                        intent.putExtra(Test.currentrefrence,getRef(position).getKey());
                        startActivity(intent);
*/
                        // Toast.makeText(getApplicationContext(), "sdasdsa" + position, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(HomeActivity.this, Item_DetailActivity.class);
                        intent.putExtra(Item_DetailActivity.ItemId, getRef(position).getKey());
                        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
                return new MyViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        // adapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HomeActivity.this, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.admin_login:
                Intent intent = new Intent(HomeActivity.this, AdminActivity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.Rate_Us:
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Thanks For Your Time", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_orange_dark))
                        .show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}