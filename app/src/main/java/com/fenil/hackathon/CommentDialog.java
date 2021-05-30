package com.fenil.hackathon;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fenil.hackathon.Adapter.CommentRecyclerViewAdapter;
import com.fenil.hackathon.Model.AddCommentRequest;
import com.fenil.hackathon.Model.AddCommentResponse;
import com.fenil.hackathon.Model.CommentResponse;
import com.fenil.hackathon.Model.GetCommentsResponse;
import com.fenil.hackathon.ViewModel.ApiCommentViewModel;
import com.fenil.hackathon.ViewModel.ApiViewModel;

import java.util.ArrayList;

public class CommentDialog extends AppCompatActivity {

    RecyclerView commentRecyclerView;
    CommentRecyclerViewAdapter commentRecyclerViewAdapter;
    EditText commentEditText;
    Button submit;
    String id;
    ApiCommentViewModel apiCommentViewModel;
    ArrayList<CommentResponse> comments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        id = getIntent().getStringExtra("id");

        Log.e("IIIIDDD>>>",id);

        comments = new ArrayList<>();

        apiCommentViewModel = new ViewModelProvider(this).get(ApiCommentViewModel.class);
        apiCommentViewModel.initGetComment();
        apiCommentViewModel.initAddComment();

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
                if(commentEditText.getText().toString().isEmpty()) {
                    Toast.makeText(CommentDialog.this, "Please enter a comment", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AddComment(id,commentEditText.getText().toString());
                }
            }
        });

        apiCommentViewModel.getCommentResponse(id).observe(this, new Observer<GetCommentsResponse>() {
            @Override
            public void onChanged(GetCommentsResponse getCommentsResponse) {
                comments = getCommentsResponse.getPostlist();
                commentRecyclerViewAdapter.updateList(comments);
                commentRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        apiCommentViewModel.getComments();
    }

    void  AddComment(String postid,String text)
    {
        apiCommentViewModel.getAddCommentResponse(postid,new AddCommentRequest(getAndroidID(),text)).observe(this, new Observer<AddCommentResponse>() {
            @Override
            public void onChanged(AddCommentResponse addCommentResponse) {
                comments.add(addCommentResponse.getCreatedPost());
                commentRecyclerViewAdapter.updateList(comments);
                commentRecyclerViewAdapter.notifyDataSetChanged();
                commentEditText.setText("");
                Toast.makeText(CommentDialog.this,addCommentResponse.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        apiCommentViewModel.addComment();
    }

    public String getAndroidID() {
        SharedPreferences sharedPreferences = getSharedPreferences("androidID", MODE_PRIVATE);
        return sharedPreferences.getString("androidID", "null");
    }
}
