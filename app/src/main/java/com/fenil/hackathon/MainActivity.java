package com.fenil.hackathon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
    public static String timestamp;
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
        }

        timestamp = getTimestamp();
        if (timestamp.equals("null")) {
            timestamp = "1970-01-01T14:06Z";
        }
        Log.v("timestamp",timestamp);

        index = (AnyConfessApp.get(this)).getIndex();
        posts = new ArrayList<>();
        searchedPosts = new ArrayList<>();
        recyclerView = findViewById(R.id.post_recyclerview);
        fab = findViewById(R.id.fab_add);
        searchBar = findViewById(R.id.search_bar);
        postRecyclerViewAdapter = new PostRecyclerViewAdapter(this, posts);
        apiViewModel = new ViewModelProvider(this).get(ApiViewModel.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreatePost.class);
                startActivity(intent);
            }
        });

        postRecyclerViewAdapter = new PostRecyclerViewAdapter(this,posts);
        recyclerView.setAdapter(postRecyclerViewAdapter);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);


        showRecyclerView();
        getPosts();
    }

    private String getTimestamp() {
        SharedPreferences sf = getSharedPreferences("timestamp", MODE_PRIVATE);
        return sf.getString("timestamp", "null");
    }

    private void updateTimestamp(Context context, String timestamp) {
        SharedPreferences sf = context.getSharedPreferences("timestamp", MODE_PRIVATE);
        SharedPreferences.Editor sfEditor = sf.edit();
        sfEditor.putString("timestamp", timestamp);
        sfEditor.apply();
    }

    private void getPosts() {
        apiViewModel.init();

        apiViewModel.getAllPosts().observe(this, new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> postsop) {
                posts = postsop;
                postRecyclerViewAdapter.updateList(postsop);
                postRecyclerViewAdapter.notifyDataSetChanged();
//                String newTimestamp = createTimestamp();
//
//                if (TextUtils.isEmpty(newTimestamp))
//                    newTimestamp = "1970-01-01T14:06Z";
//                updateTimestamp(MainActivity.this, newTimestamp);
            }
        });

        apiViewModel.fetchPostsFromApi();

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

    private String createTimestamp() {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        dateFormat.setTimeZone(timeZone);
        String updatedTimestamp = "";
        try {
            if (posts.size() > 0) {
                Date date = dateFormat.parse(posts.get(posts.size() - 1).getTimestamp());
                Log.v("Date",date.toString());
                assert date != null;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String year = String.valueOf(calendar.get(Calendar.YEAR));
                String month = String.valueOf(calendar.get(Calendar.MONTH));
                String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                String minutes = String.valueOf(calendar.get(Calendar.MINUTE));
                if (hour >= 2)
                    hour -= 2;
                String stringHour = String.valueOf(hour);
                updatedTimestamp = year + "-" + month + "-" + day + "T" + stringHour + ":" + minutes + "Z";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return updatedTimestamp;
    }

    private void showRecyclerView() {
        recyclerView.setAdapter(postRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void saveAndroidID() {
        SharedPreferences sharedPreferences = getSharedPreferences("androidID", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("androidID", Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        editor.apply();
    }

    private String getAndroidID() {
        SharedPreferences sharedPreferences = getSharedPreferences("androidID", MODE_PRIVATE);
        return sharedPreferences.getString("androidID", "null");
    }

    private void showTermsOfService() {
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
                    searchedPosts.add(new Post(jsb.getString("text"),jsb.getString("_id"),jsb.getString("timestamp"),jsb.get("hashtags").toString()));
                }

                postRecyclerViewAdapter.updateList(searchedPosts);
                postRecyclerViewAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

}