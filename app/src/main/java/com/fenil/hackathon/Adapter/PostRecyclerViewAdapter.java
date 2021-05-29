package com.fenil.hackathon.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fenil.hackathon.Callback.PostDiffUtil;
import com.fenil.hackathon.Model.Post;
import com.fenil.hackathon.R;

import java.util.ArrayList;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.PostRecyclerViewHolder>{

    Context context;
    ArrayList<Post> posts;

    public PostRecyclerViewAdapter(Context context, ArrayList<Post> posts)
    {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.card_post, parent, false);
        return new PostRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostRecyclerViewHolder holder, int position) {
        String content = posts.get(position).getContent();
        int commentCount = posts.get(position).getCommentCount();

        holder.content.setText(content);
        holder.commentCount.setText(String.valueOf(commentCount));

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Implement comment counter
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        TextView commentCount;
        ImageButton comment;

        public PostRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.post_textview);
            commentCount = itemView.findViewById(R.id.post_commentCount);
            comment = itemView.findViewById(R.id.post_comment);

        }
    }
    public void updateList(ArrayList<Post> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new PostDiffUtil(this.posts, newList));
        diffResult.dispatchUpdatesTo(this);
    }
}
