package com.example.photopostiongyang.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photopostiongyang.Model.ReplyInfo;
import com.example.photopostiongyang.R;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {
    public DetailAdapter(List<ReplyInfo> list){mReplyList=list;}
    private List<ReplyInfo> mReplyList=new ArrayList<>();
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {//보여주는곳
        ReplyInfo replyInfo=mReplyList.get(position);
        holder.mNicknameTextView.setText(replyInfo.getNickname());
        holder.mReplyTextView.setText(replyInfo.getReply());
    }
    public void addItem(ReplyInfo replyInfo) {
        this.mReplyList.add(replyInfo);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mReplyList.size();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView mNicknameTextView;
        private TextView mReplyTextView;
        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            mNicknameTextView=itemView.findViewById(R.id.item_detail_name);
            mReplyTextView=itemView.findViewById(R.id.item_detail_reply);
        }
    }
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail,parent,false));//아이템메뉴는 작은내모가 확장되있는거
    }
}
