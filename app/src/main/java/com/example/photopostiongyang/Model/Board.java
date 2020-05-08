package com.example.photopostiongyang.Model;


import android.net.Uri;

public class Board {

    private String id;
    private String title;
    private String contents;
    private String name;
    private String mImageUri;

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", name='" + name + '\'' +
                ", mImageUri=" + mImageUri +
                '}';
    }



    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }

    public String getmImageUri() {
        return mImageUri;
    }



    public Board(String id, String title, String contents, String name, String mImageUri) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.name = name;
        this.mImageUri = mImageUri;
    }


    public Board(){

    }
    public Board(String mImageUri){
        this.mImageUri=mImageUri;
    }

    public Board( String title, String name) {

        this.title = title;
        this.name = name;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
