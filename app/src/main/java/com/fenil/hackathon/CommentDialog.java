package com.fenil.hackathon;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fenil.hackathon.Adapter.CommentRecyclerViewAdapter;

import java.util.ArrayList;

public class CommentDialog extends AppCompatActivity {

    RecyclerView commentRecyclerView;
    CommentRecyclerViewAdapter commentRecyclerViewAdapter;
    EditText commentEditText;
    Button submit;
    String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        id = getIntent().getStringExtra("id");
        ArrayList<String> comments = new ArrayList<>();

        commentRecyclerViewAdapter = new CommentRecyclerViewAdapter(this,comments);
        commentRecyclerView = findViewById(R.id.comments_recyclerview);
        commentEditText = findViewById(R.id.comment_edittext);
        submit = findViewById(R.id.comment_submit);

        commentRecyclerView.setHasFixedSize(true);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentRecyclerView.setAdapter(commentRecyclerViewAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(commentEditText.getText().toString()))
                    Toast.makeText(CommentDialog.this,"Please enter a comment",Toast.LENGTH_SHORT).show();
                else
                {
                    //TODO: Update comment on the post
                }
            }
        });
    }
}
