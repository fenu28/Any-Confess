package com.fenil.hackathon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fenil.hackathon.Model.CommentResponse;
import com.fenil.hackathon.R;

import java.util.ArrayList;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {

    ArrayList<CommentResponse> comments;
    Context context;

    public CommentRecyclerViewAdapter(Context context, ArrayList<CommentResponse> comments) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.card_comment, parent, false);
        return new CommentRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.comment.setText(comments.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView comment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.comment_textview);
        }
    }

    public void updateList(ArrayList<CommentResponse> newlist)
    {
        this.comments = newlist;
    }
}
