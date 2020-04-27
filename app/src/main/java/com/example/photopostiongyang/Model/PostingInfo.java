package com.example.photopostiongyang.Model;

import java.util.List;

public class PostingInfo {
    private List<String> imageStringlist;
    private String title;
    private String contents;
    private String nickname;
    public PostingInfo(){}

    public PostingInfo(List<String> imageStringlist, String title, String contents, String nickname) {
        this.imageStringlist = imageStringlist;
        this.title = title;
        this.contents = contents;
        this.nickname = nickname;
    }

    public List<String> getImageStringlist() {
        return imageStringlist;
    }

    public void setImageStringlist(List<String> imageStringlist) {
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
