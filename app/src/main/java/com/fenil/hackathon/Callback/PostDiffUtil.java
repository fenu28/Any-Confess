package com.fenil.hackathon.Callback;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.fenil.hackathon.Model.Post;

import java.util.ArrayList;

public class PostDiffUtil extends DiffUtil.Callback {

    ArrayList<Post> oldPosts;
    ArrayList<Post> newPosts;

    public PostDiffUtil(ArrayList<Post> oldPosts,ArrayList<Post> newPosts)
    {
        this.oldPosts = oldPosts;
        this.newPosts = newPosts;
    }
    @Override
    public int getOldListSize() {
        return oldPosts.size();
    }

    @Override
    public int getNewListSize() {
        return newPosts.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPosts.get(oldItemPosition).equals(newPosts.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
