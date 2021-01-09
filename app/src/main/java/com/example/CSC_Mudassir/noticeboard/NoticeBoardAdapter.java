package com.example.CSC_Mudassir.noticeboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CSC_Mudassir.olxselling.model.datamodel.Items;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class NoticeBoardAdapter extends RecyclerView.Adapter<NoticeBoardAdapter.MyNoticeBoardViewHolder> {

    Context mContext;
    private ArrayList<NoticeBoard> itemsArrayList;

    public NoticeBoardAdapter(Context context) {
        this.mContext = context;
    }

    void setNoticeBoard(ArrayList<NoticeBoard> mItemsArrayList) {
        this.itemsArrayList = mItemsArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyNoticeBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyNoticeBoardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    public class MyNoticeBoardViewHolder extends RecyclerView.ViewHolder {

        public MyNoticeBoardViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
