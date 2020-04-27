package com.example.photopostiongyang.Model;

import java.util.ArrayList;

public class PostingInfo {
    private ArrayList<String> imageStringlist;
    private String title;
    private String contents;
    private String nickname;
    public PostingInfo(){}

    public PostingInfo(ArrayList<String> imageStringlist, String title, String contents, String nickname) {
        this.imageStringlist = imageStringlist;
        this.title = title;
        this.contents = contents;
        this.nickname = nickname;
    }

    public ArrayList<String> getImageStringlist() {
        return imageStringlist;
    }

    public void setImageStringlist(ArrayList<String> imageStringlist) {
        this.imageStringlist = imageStringlist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "PostingInfo{" +
                "imageStringlist=" + imageStringlist +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
