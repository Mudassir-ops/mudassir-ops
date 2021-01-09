package com.example.CSC_Mudassir.olxselling.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.CSC_Mudassir.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;
public class Item_DetailActivity extends AppCompatActivity {
    public static final String ItemId = "ITEMKEY";
    private ImageView image_view_item;
    private TextView textView_name, textView_decription, textView_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item__detail);
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "This is Detail activity", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
        DatabaseReference ref;
        image_view_item = (ImageView) findViewById(R.id.imageview_Item_detail_new);
        //image_view = (ImageView) findViewById(R.id.image_Detail_view);
        textView_name = (TextView) findViewById(R.id.name_Item_detail_new);
        textView_decription = (TextView) findViewById(R.id.item_Decription_detail_new);
        textView_price = (TextView) findViewById(R.id.item_price_detail_new);

        String item_key = getIntent().getStringExtra(ItemId);
        ref = FirebaseDatabase.getInstance().getReference().child("Items");
        ref.child(item_key).addValueEventListener(new ValueEventListener() {@Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String ItemName = snapshot.child("ItemName").getValue().toString().trim();
                    String ItemImageUri = snapshot.child("ImageUri").getValue().toString();
                    String item_price = snapshot.child("ItemPrice").getValue().toString();
                    String item_Decription = snapshot.child("ItemDescription").getValue().toString();
                    // Toast.makeText(getApplicationContext(),"name :"+ItemName,Toast.LENGTH_LONG).show();
                    textView_name.setText(ItemName);
                    textView_price.setText(item_price);
                    textView_decription.setText(item_Decription);
                    Picasso.get().load(ItemImageUri).into(image_view_item);
                    Animation animation1 = AnimationUtils.loadAnimation(Item_DetailActivity.this, R.anim.blink_anim);
                    image_view_item.startAnimation(animation1);
                    //   Picasso.with(context).load("http://postimg.org/image/wjidfl5pd/").into(ImageView1);
                    //    Picasso.get().load(ItemImageUri).into(image_view);
                    //     textView_name.setText(ItemName);
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
        }
        });
    }
}