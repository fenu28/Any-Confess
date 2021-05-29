package com.fenil.hackathon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.fenil.hackathon.Adapter.PostRecyclerViewAdapter;
import com.fenil.hackathon.Model.Post;
import com.fenil.hackathon.ViewModel.ApiViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.harsh.searchwidget.SearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PostRecyclerViewAdapter postRecyclerViewAdapter;
    ArrayList<Post> posts;
    ArrayList<Post> searchedPosts;
    String androidID;
    FloatingActionButton fab;
    Index index;

    SearchBar searchBar;
    private List<String> lastSearches;

    ApiViewModel apiViewModel;


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

        index = (AnyConfessApp.get(this)).getIndex();

        posts = new ArrayList<>();
        searchedPosts = new ArrayList<>();
        recyclerView = findViewById(R.id.post_recyclerview);
        fab = findViewById(R.id.fab_add);
        searchBar = findViewById(R.id.search_bar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreatePost.class);
                startActivity(intent);
            }
        });

        postRecyclerViewAdapter = new PostRecyclerViewAdapter(this,posts);
        recyclerView.setAdapter(postRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        apiViewModel = new ViewModelProvider(this).get(ApiViewModel.class);
        apiViewModel.init();

        apiViewModel.getAllPosts().observe(this, new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> postsop) {
                posts = postsop;
                postRecyclerViewAdapter.updateList(postsop);
                postRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        apiViewModel.fetchPostsFromApi();

        searchBar.setOnSearchActionListener(new SearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                String state = enabled ? "enabled" : "disabled";
                Toast.makeText(getApplicationContext(), "Search bar is " + state, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Toast.makeText(getApplicationContext(), "Search query is: " + text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                switch (buttonCode) {
                    case SearchBar.BUTTON_BACK:
                        Toast.makeText(getApplicationContext(), "Back button pressed", Toast.LENGTH_SHORT).show();
                        break;
                    case SearchBar.BUTTON_NAVIGATION:
                        Toast.makeText(getApplicationContext(), "Open Navigation Drawer", Toast.LENGTH_SHORT).show();
                        break;
                    case SearchBar.BUTTON_SPEECH:
                        Toast.makeText(getApplicationContext(), "Start voice recognition module", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=0) {
                    index.searchAsync(new Query(s.toString()),
                            completionHandler);
                }
                else
                {
                    postRecyclerViewAdapter.updateList(posts);
                    postRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void saveAndroidID() {
        SharedPreferences sharedPreferences = getSharedPreferences("androidID", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("androidID", Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        editor.apply();
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

    CompletionHandler completionHandler = new CompletionHandler() {
        @Override
        public void requestCompleted(JSONObject content, AlgoliaException error) {
            assert content != null;
            try {
                searchedPosts.clear();
                JSONArray jsonArray = content.getJSONArray("hits");
                for(int i=0; i< jsonArray.length(); i++)
                {
                    JSONObject jsb = jsonArray.getJSONObject(i).getJSONObject("_doc");
                    searchedPosts.add(new Post(jsb.getString("text")));
                }

                postRecyclerViewAdapter.updateList(searchedPosts);
                postRecyclerViewAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

}