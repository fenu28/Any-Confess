package com.fenil.hackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.fenil.hackathon.Adapter.PostRecyclerViewAdapter;
import com.fenil.hackathon.Model.Post;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PostRecyclerViewAdapter postRecyclerViewAdapter;
    ArrayList<Post> posts;
    String androidID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        posts = new ArrayList<>();
        recyclerView = findViewById(R.id.post_recyclerview);

        if(getAndroidID().equals("null"))
            saveAndroidID();
        androidID = getAndroidID();
        Toast.makeText(this,androidID,Toast.LENGTH_SHORT).show();
        Post post = new Post("How to build an Android App?","This article is a beginning of something very exciting. Keep checking out this space for updates and news");

        for(int i = 1;i<=20;i++)
        {
            posts.add(post);
        }

        postRecyclerViewAdapter = new PostRecyclerViewAdapter(this,posts);
        recyclerView.setAdapter(postRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    public void saveAndroidID() {
        SharedPreferences sharedPreferences = getSharedPreferences("androidID",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("androidID", Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        editor.commit();
    }
    public String getAndroidID()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("androidID",MODE_PRIVATE);
        return sharedPreferences.getString("androidID","null");
    }
}