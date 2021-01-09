package com.example.CSC_Mudassir.olxselling.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CSC_Mudassir.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView_card;
    TextView textView_card;
    View v;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView_card=(ImageView)itemView.findViewById(R.id.image_card_view);
        textView_card=(TextView)itemView.findViewById(R.id.text_card_view);
          v=itemView;
    }
}
