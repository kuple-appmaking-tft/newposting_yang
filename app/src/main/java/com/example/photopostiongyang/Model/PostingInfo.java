package com.example.photopostiongyang.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;

public class PostingInfo {
    private ArrayList<String> imageStringlist;
    private String title;
    private String contents;
    private String nickname;
    private int likebutton_count;

    public PostingInfo(ArrayList<String> imageStringlist, String title, String contents, String nickname, int likebutton_count, Date date) {
        this.imageStringlist = imageStringlist;
        this.title = title;
        this.contents = contents;
        this.nickname = nickname;
        this.likebutton_count = likebutton_count;
        this.date = date;
    }


    @ServerTimestamp
    private Date date;
    public PostingInfo(){}

//    public PostingInfo(ArrayList<String> imageStringlist, String title, String contents, String nickname, Date date) {
////        this.imageStringlist = imageStringlist;
////        this.title = title;
////        this.contents = contents;
////        this.nickname = nickname;
////        this.date = date;
////    }

    public int getLikebutton_count() {
        return likebutton_count;
    }

    public void setLikebutton_count(int likebutton_count) {
        this.likebutton_count = likebutton_count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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



}
