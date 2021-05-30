package com.fenil.hackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fenil.hackathon.Model.CreatePostRequest;
import com.fenil.hackathon.Model.CreatePostResponse;
import com.fenil.hackathon.ViewModel.ApiViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreatePost extends AppCompatActivity {

    EditText content,hashtags;
    TextView remaining_chars;
    Button submit;
    FloatingActionButton fab;
    ApiViewModel apiViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        content = findViewById(R.id.content_edittext);
        hashtags = findViewById(R.id.hashtags_edittext);
        submit = findViewById(R.id.submit_button);
        remaining_chars = findViewById(R.id.remaining_chars);

        apiViewModel = new ViewModelProvider(this).get(ApiViewModel.class);
        apiViewModel.initCreatePost();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(content.getText().toString()))
                    Toast.makeText(CreatePost.this,"Content is required",Toast.LENGTH_SHORT).show();
                else
                {
                    CreatePost(content.getText().toString(),hashtags.getText().toString());
                }
            }
        });

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                remaining_chars.setText(String.valueOf(512 - s.length()) + " characters remaining");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void CreatePost(String content_text, String hashtags_string)
    {
        Matcher matcher = Pattern.compile("(#[^#\\s]*)")
                .matcher(hashtags_string);

        ArrayList<String> tags = new ArrayList<>();
        while (matcher.find()) {
            tags.add(matcher.group());
        }

        apiViewModel.getCreatePostResponse(new CreatePostRequest(getAndroidID(),content_text,tags)).observe(this, new Observer<CreatePostResponse>() {
            @Override
            public void onChanged(CreatePostResponse createPostResponse) {
               Toast.makeText(CreatePost.this,createPostResponse.getMessage(),Toast.LENGTH_SHORT).show();
               content.setText("");
               hashtags.setText("");
            }
        });

        apiViewModel.createPost();
    }

    public String getAndroidID() {
        SharedPreferences sharedPreferences = getSharedPreferences("androidID", MODE_PRIVATE);
        return sharedPreferences.getString("androidID", "null");
    }
}