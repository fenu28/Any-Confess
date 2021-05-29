package com.fenil.hackathon.Model;

import java.util.ArrayList;
import java.util.List;

public class Post{
    String content;
    String id;
    String timestamp;
    List<String> hashtags;

    public Post(){

    }

    public Post(String content,String id,String timestamp,List<String> hashtags)
    {
        this.content = content;
        this.id = id;
        this.timestamp = timestamp;
        this.hashtags = hashtags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
