package com.fenil.hackathon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

        if (getAndroidID().equals("null")) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    showTermsOfService();
                }
            });
            saveAndroidID();
            Toast.makeText(MainActivity.this,getAndroidID(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(MainActivity.this,getAndroidID(),Toast.LENGTH_SHORT).show();
        posts = new ArrayList<>();
        recyclerView = findViewById(R.id.post_recyclerview);

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
        SharedPreferences sharedPreferences = getSharedPreferences("androidID", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("androidID", Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        editor.commit();
    }

    public String getAndroidID() {
        SharedPreferences sharedPreferences = getSharedPreferences("androidID", MODE_PRIVATE);
        return sharedPreferences.getString("androidID", "null");
    }

    public void showTermsOfService() {
        AlertDialog.Builder termsOfService = new AlertDialog.Builder(this);
        termsOfService.setMessage("This app does not store your data on any local or cloud storage. Your data remains solely yours and will never get interfered with. Do you agree to our terms of service?");
        termsOfService.setPositiveButton("Agree",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        termsOfService.setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = termsOfService.create();
        alertDialog.show();
    }

}