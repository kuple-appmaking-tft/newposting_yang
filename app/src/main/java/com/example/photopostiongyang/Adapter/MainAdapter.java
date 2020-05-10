package com.example.photopostiongyang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photopostiongyang.Model.PostingInfo;
import com.example.photopostiongyang.Model.SliderItem;
import com.example.photopostiongyang.R;
import com.example.photopostiongyang.activity.MainActivity;
import com.google.android.exoplayer2.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.Date;
import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{
    private Context mContext;
    private List<PostingInfo> mPostingInfoList;
    private List<String> mDocumentIdList;
    //커스텀 리스터 정의
///////////////////////////클릭리스너
    public interface OnItemClickListener{
        void onitemClick(View v,int pos);
    }
    private OnItemClickListener mListener=null;
    public void setOnIemlClickListner(OnItemClickListener listner){
        this.mListener=listner;
    }
////////////////////////////////
    public MainAdapter(List<PostingInfo> mPostingInfoList,Context mContext,List<String> mDocumentIdList) {
        this.mContext = mContext;
        this.mPostingInfoList = mPostingInfoList;
        this.mDocumentIdList=mDocumentIdList;
    }
    class MainViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitleTextView;        //item_main의 객체를 불러옴...작은네모칸에 들어갈 얘들 선언
        private TextView mNameTextView;
        private TextView mContentsTextView;
        private SliderView mImageSliderView;
        private TextView mDateTextView;
        private LikeButton mLikeButton;
        private TextView mLikeButton_count;
        private ImageView mImageview;//매뉴클릭릭
        private ImageView mShareImageView;
        private ImageView mNewDateImageView;


       public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView=itemView.findViewById(R.id.item_title_text);
            mNameTextView=itemView.findViewById(R.id.item_name_text);
            mContentsTextView=itemView.findViewById(R.id.item_contents_text);
            mImageSliderView=itemView.findViewById(R.id.item_imageslider);
            mImageview=itemView.findViewById(R.id.item_menudot_imageview);
            mLikeButton=itemView.findViewById(R.id.item_likeButton_likeButton);
            mLikeButton_count=itemView.findViewById(R.id.item_likeButton_textView);
            mShareImageView=itemView.findViewById(R.id.item_share_imageview);
            mDateTextView=itemView.findViewById(R.id.item_date);
           mNewDateImageView=itemView.findViewById(R.id.item_dateN_ImageView);
            mImageSliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            mImageSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            mImageSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
            mImageSliderView.setIndicatorSelectedColor(Color.WHITE);
            mImageSliderView.setIndicatorUnselectedColor(Color.GRAY);
            mImageSliderView.setScrollTimeInSec(3);
            mImageSliderView.setAutoCycle(false);

            //////클릭리스너
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.onitemClick(v,pos);
                        }
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false));//아이템메뉴는 작은내모가 확장되있는거
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {//class MainViewHolder의 holder <Board>형식의 data값을 참조.
        PostingInfo data = mPostingInfoList.get(position);
        String documentId = mDocumentIdList.get(position);

        holder.mTitleTextView.setText(data.getTitle());
        holder.mNameTextView.setText(data.getNickname());
        holder.mContentsTextView.setText(data.getContents());
        SliderAdapterExample sliderAdapterExample = new SliderAdapterExample(mContext);
        for (int i = 0; i < data.getImageStringlist().size(); i++) {
            sliderAdapterExample.addItem(new SliderItem(data.getImageStringlist().get(i)));
        }
        holder.mImageSliderView.setSliderAdapter(sliderAdapterExample);
        FirebaseFirestore mStore = FirebaseFirestore.getInstance();
        holder.mLikeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                mStore.collection("Testing").document(documentId).update("likebutton_count", FieldValue.increment(1));
            }
            @Override
            public void unLiked(LikeButton likeButton) {
                mStore.collection("Testing").document(documentId).update("likebutton_count", FieldValue.increment(-1));
            }
        });
        holder.mLikeButton_count.setText(String.valueOf(data.getLikebutton_count()));
        holder.mImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v,documentId);
            }
        });
        holder.mShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        String date=data.getDate().toString();
        String date1=date.substring(11,16);
        String date2=date.substring(0,13)+" "+date.substring(30,34);
        Log.d("dateYY", date2);
        String[] split1=date1.split(":");
        Log.d("date", date1);
        holder.mDateTextView.setText(date1);

//        Calendar calendar=Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("ee MMM dd :HH:mm yyyy");
//        String dateTime=simpleDateFormat.format(new Date());
        String dateTime=new Date().toString();
        dateTime=dateTime.substring(0,13)+" "+date.substring(30,34);
        Log.d("dateYY", dateTime);
       // Log.d("date", dateTime);
        holder.mNewDateImageView.setVisibility(View.INVISIBLE);
        if(dateTime.equals(date2)){
            holder.mNewDateImageView.setVisibility(View.VISIBLE);
        }











    }
    public void showPopup(View v,String documentId) {
        FirebaseFirestore mStore=FirebaseFirestore.getInstance();
        PopupMenu popup = new PopupMenu(mContext, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.modify:
                        Toast.makeText(mContext,"무슨수정이냐 그냥 쳐 삭제해라",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.remove:
                        mStore.collection("Testing").document(documentId)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(mContext,"데이터베이스에서 삭제됨.새로고침안해도댐",Toast.LENGTH_LONG).show();
                                        Intent intent1 =new Intent(v.getContext(),MainActivity.class);
                                        intent1.putExtra("Refresh","success");
                                        mContext.startActivity(intent1);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG", "Error deleting document", e);
                                    }
                                });
                        return true;
                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.post, popup.getMenu());
        popup.show();
    }


    @Override
    public int getItemCount() {
        return mPostingInfoList.size();
    }



}


