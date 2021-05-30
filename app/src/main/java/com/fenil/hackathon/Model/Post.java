package com.fenil.hackathon.Model;

import java.util.ArrayList;
import java.util.List;

public class Post{
    String content;
    String id;
    String timestamp;
    String hashtags;

    public Post() {
    }

    public Post(String content, String id, String timestamp, String hashtags) {
        this.content = content;
        this.id = id;
        this.timestamp = timestamp;
        this.hashtags = hashtags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }
}
