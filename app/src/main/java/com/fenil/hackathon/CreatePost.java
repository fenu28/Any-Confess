package com.fenil.hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreatePost extends AppCompatActivity {

    EditText title,content,hashtags;
    Button submit;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        title = findViewById(R.id.title_edittext);
        content = findViewById(R.id.content_edittext);
        hashtags = findViewById(R.id.hashtags_edittext);
        submit = findViewById(R.id.submit_button);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(title.getText().toString()))
                    Toast.makeText(CreatePost.this,"Title is required",Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(content.getText().toString()))
                    Toast.makeText(CreatePost.this,"Content is required",Toast.LENGTH_SHORT).show();
                else
                {
                    //TODO: Implement submitting of confession
                    Toast.makeText(CreatePost.this, "Confession submitted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}