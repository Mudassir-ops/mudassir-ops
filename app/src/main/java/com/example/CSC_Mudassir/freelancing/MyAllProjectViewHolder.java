package com.example.CSC_Mudassir.freelancing;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CSC_Mudassir.R;

public class MyAllProjectViewHolder extends RecyclerView.ViewHolder {
    TextView projectName, teacherDepartment, teacherContactNo;
    CardView cardView;
    View view;
    public MyAllProjectViewHolder(@NonNull View itemView) {
        super(itemView);
        projectName = itemView.findViewById(R.id.ProjectNameCardView);
        teacherDepartment = itemView.findViewById(R.id.TeacherContactCardView);
        teacherContactNo = itemView.findViewById(R.id.ProjectDepartmentCardView);
        cardView = itemView.findViewById(R.id.CardView);
        view=itemView;
    }
}
