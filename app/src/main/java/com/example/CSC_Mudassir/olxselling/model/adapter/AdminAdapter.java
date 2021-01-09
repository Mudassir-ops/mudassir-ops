package com.example.CSC_Mudassir.olxselling.model.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CSC_Mudassir.olxselling.model.datamodel.Items;
import com.example.CSC_Mudassir.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {
    Context mContext;
    private DatabaseReference DataRef;
    private ArrayList<Items> itemsArrayList;

    public AdminAdapter(Context context) {
        this.mContext = context;
    }

    public void setAdminData(ArrayList<Items> mItemsArrayList) {
        this.itemsArrayList = mItemsArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdminViewHolder holder, final int position) {

        Picasso.get().load(itemsArrayList.get(position).getImageUri()).into(holder.car);
        Log.d("imagruri", "" + itemsArrayList.get(position).getImageUri());
        holder.title.setText(itemsArrayList.get(position).getItemName());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            final DatabaseReference DataRef = FirebaseDatabase.getInstance().getReference().child("Items");
            @Override
            public boolean onLongClick(final View v) {
                final CharSequence[] items = {"Delete Item", "Exit"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Select The Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            DataRef.orderByChild("ImageUri")
                                    .equalTo((String) itemsArrayList.get(position).getImageUri())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.hasChildren()) {
                                                DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                                firstChild.getRef().removeValue();
                                                //  Snackbar snackbar1 = Snackbar.make(v, "Image Deleted!",Snackbar.LENGTH_SHORT);

                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });
                            Toast.makeText(mContext, "Item Removed Refresh Screen", Toast.LENGTH_LONG).show();
                        } else if (item == 1) {
                            dialog.dismiss();
                        } else {
                            dialog.cancel();
                        }

                    }
                });
                builder.show();
                return true;
            }
        });
      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }


    public class AdminViewHolder extends RecyclerView.ViewHolder {
        private ImageView car;
        private TextView title;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            View parentLayout = itemView.findViewById(android.R.id.content);
            DataRef = FirebaseDatabase.getInstance().getReference().child("Items");
            car = itemView.findViewById(R.id.image_card_view);
            title = itemView.findViewById(R.id.text_card_view);
        }
    }
}
