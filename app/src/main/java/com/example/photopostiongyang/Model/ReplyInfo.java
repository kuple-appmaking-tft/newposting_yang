package com.example.photopostiongyang.Model;

public class ReplyInfo {
    private String nickname;
    private String reply;

    public ReplyInfo(String nickname, String reply) {
        this.nickname = nickname;
        this.reply = reply;
    }
    public ReplyInfo(){}

    @Override
    public String toString() {
        return "ReplyInfo{" +
                "nickname='" + nickname + '\'' +
                ", reply='" + reply + '\'' +
                '}';
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
