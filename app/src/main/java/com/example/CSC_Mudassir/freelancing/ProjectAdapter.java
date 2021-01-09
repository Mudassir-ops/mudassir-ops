package com.example.CSC_Mudassir.freelancing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CSC_Mudassir.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
    Context mContext;
    private DatabaseReference DataRef;
    private ArrayList<Project> projectArrayList;

    public ProjectAdapter(Context context) {
        this.mContext = context;
    }

    public void setAdminData(ArrayList<Project> mProjectsArrayList) {
        this.projectArrayList = mProjectsArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_cardview, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, final int position) {

        holder.projectName.setText(projectArrayList.get(position).getProjectName());
        holder.teacherDepartment.setText(projectArrayList.get(position).getProjectDescription());
        holder.teacherContactNo.setText(projectArrayList.get(position).getTeacherContactNo());
        final DatabaseReference DataRef = FirebaseDatabase.getInstance().getReference().child("Projects");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Delete Project", "Update Project", "Exit"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Select The Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int item) {
                        if (item == 0) {
                            DataRef.orderByChild("PrimaryID")
                                    .equalTo((String) projectArrayList.get(position).getPrimaryID())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.hasChildren()) {
                                                DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                                firstChild.getRef().removeValue();
                                                projectArrayList.remove(position);
                                                //  Snackbar snackbar1 = Snackbar.make(v, "Image Deleted!",Snackbar.LENGTH_SHORT);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });

                            Toast.makeText(mContext, "Item Removed Refresh Screen", Toast.LENGTH_LONG).show();
                        } else if (item == 1) {
                            Intent intent = new Intent(mContext, UpdateSellingActivity.class);
                            intent.putExtra(UpdateSellingActivity.PROJECT_ID, projectArrayList.get(position).getPrimaryID());
                            mContext.startActivity(intent);
                        } else if (item == 2) {
                            dialog.dismiss();
                        } else {
                            dialog.cancel();
                        }

                    }
                });
                builder.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return projectArrayList.size();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {
        TextView projectName, teacherDepartment, teacherContactNo;
        CardView cardView;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.ProjectNameCardView);
            teacherDepartment = itemView.findViewById(R.id.TeacherContactCardView);
            teacherContactNo = itemView.findViewById(R.id.ProjectDepartmentCardView);
            cardView = itemView.findViewById(R.id.CardView);


        }
    }

    public static void updateTotalVotes(final String operation, final String keyDataabse) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference totalVotesRef = rootRef.child("Projects").child(keyDataabse).child("ProjectName");
        totalVotesRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                String votes = mutableData.getValue(Project.class).getProjectName();
                if (votes != null) {
                    mutableData.setValue(operation);
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("TagDatabase", databaseError.getMessage()); //Don't ignore errors!
            }
        });
    }
}
