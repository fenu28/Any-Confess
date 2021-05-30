package com.fenil.hackathon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fenil.hackathon.CommentDialog;
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
    public void onBindViewHolder(@NonNull PostRecyclerViewHolder holder, int p) {

        int position = posts.size() - (p+1);

        String content = posts.get(position).getContent();

        holder.content.setText(content);
        holder.hashtags.setText(posts.get(position).getHashtags());

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentDialog.class);
                intent.putExtra("id",posts.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView content,hashtags;
        ImageButton comment;

        public PostRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.post_textview);
            comment = itemView.findViewById(R.id.post_comment);
            hashtags = itemView.findViewById(R.id.post_hashtags);
        }
    }
    public void updateList(ArrayList<Post> newList) {
        this.posts = newList;
    }
}
