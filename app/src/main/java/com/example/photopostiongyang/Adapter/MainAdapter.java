package com.example.photopostiongyang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
<<<<<<< HEAD
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
=======
import android.view.View;
import android.view.ViewGroup;
>>>>>>> f680a193d2a34204a75b05f0e4d5395ff9ec1779
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photopostiongyang.Model.PostingInfo;
import com.example.photopostiongyang.Model.SliderItem;
import com.example.photopostiongyang.R;
<<<<<<< HEAD
import com.google.android.exoplayer2.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.like.LikeButton;
import com.like.OnLikeListener;
=======
>>>>>>> f680a193d2a34204a75b05f0e4d5395ff9ec1779
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{
    private Context mContext;
    private List<PostingInfo> mPostingInfoList;
<<<<<<< HEAD
    private List<String> mDocumentIdList;
    //커스텀 리스터 정의

    public interface OnItemClickListener{
        void onitemClick(View v,int pos);
    }
    private OnItemClickListener mListener=null;
    public void setOnIemlClickListner(OnItemClickListener listner){
        this.mListener=listner;
    }
    //
=======

    public interface OnItemClickListener{
        void onItemClick(View v,int pos);
    }
    private OnItemClickListener mListener=null;
    public void setOnItemClickListner(OnItemClickListener listner){
        this.mListener=listner;
    }
>>>>>>> f680a193d2a34204a75b05f0e4d5395ff9ec1779

     //데이터를 리스트형식 (model형으로 제네릭)

    public MainAdapter(List<PostingInfo> mPostingInfoList, Context mContext ) {//데이터 들어오면 저장해주는 생성자
        this.mPostingInfoList=mPostingInfoList;
        this.mContext=mContext;
    }

<<<<<<< HEAD
    public MainAdapter(List<PostingInfo> mPostingInfoList,Context mContext,List<String> mDocumentIdList) {
        this.mContext = mContext;
        this.mPostingInfoList = mPostingInfoList;
        this.mDocumentIdList=mDocumentIdList;
    }



=======
>>>>>>> f680a193d2a34204a75b05f0e4d5395ff9ec1779
    class MainViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitleTextView;        //item_main의 객체를 불러옴...작은네모칸에 들어갈 얘들 선언
        private TextView mNameTextView;
        private TextView mContentsTextView;
        private SliderView mImageSliderView;

<<<<<<< HEAD
        private LikeButton mLikeButton;
        private TextView mLikeButton_count;
        private TextView mStarButton_count;
        private int REQUEST_TEST = 1;
        private String documentId;
        private ImageView mImageview;//매뉴클릭릭


       public MainViewHolder(@NonNull View itemView) {
            super(itemView);

=======

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.onItemClick(v,pos);
                        }
                    }
                }
            });
>>>>>>> f680a193d2a34204a75b05f0e4d5395ff9ec1779
            mTitleTextView=itemView.findViewById(R.id.item_title_text);
            mNameTextView=itemView.findViewById(R.id.item_name_text);
            mContentsTextView=itemView.findViewById(R.id.item_contents_text);
            mImageSliderView=itemView.findViewById(R.id.item_imageslider);
<<<<<<< HEAD
            mImageview=itemView.findViewById(R.id.item_menudot_imageview);

            FirebaseFirestore mStore=FirebaseFirestore.getInstance();

            mLikeButton=itemView.findViewById(R.id.item_likeButton_likeButton);
            mLikeButton_count=itemView.findViewById(R.id.item_likeButton_textView);
=======
>>>>>>> f680a193d2a34204a75b05f0e4d5395ff9ec1779

            mImageSliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            mImageSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            mImageSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
            mImageSliderView.setIndicatorSelectedColor(Color.WHITE);
            mImageSliderView.setIndicatorUnselectedColor(Color.GRAY);
            mImageSliderView.setScrollTimeInSec(3);
            mImageSliderView.setAutoCycle(false);

<<<<<<< HEAD
//            mImageview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showPopup();
//                }
//            });

        }
        
    }
    

=======
        }
    }
>>>>>>> f680a193d2a34204a75b05f0e4d5395ff9ec1779


    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false));//아이템메뉴는 작은내모가 확장되있는거
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {//class MainViewHolder의 holder <Board>형식의 data값을 참조.
<<<<<<< HEAD
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



    }
    public void showPopup(View v,String documentId) {
        FirebaseFirestore mStore=FirebaseFirestore.getInstance();
        PopupMenu popup = new PopupMenu(mContext, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.modify:

                        return true;
                    case R.id.remove:
                        mStore.collection("Testing").document(documentId)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
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

=======
        PostingInfo data=mPostingInfoList.get(position);

            holder.mTitleTextView.setText(data.getTitle());
            holder.mNameTextView.setText(data.getNickname());
            holder.mContentsTextView.setText(data.getContents());
            SliderAdapterExample sliderAdapterExample = new SliderAdapterExample(mContext);
            for (int i = 0; i < data.getImageStringlist().size(); i++) {
                sliderAdapterExample.addItem(new SliderItem(data.getImageStringlist().get(i)));
            }
            holder.mImageSliderView.setSliderAdapter(sliderAdapterExample);


    }
>>>>>>> f680a193d2a34204a75b05f0e4d5395ff9ec1779

    @Override
    public int getItemCount() {
        return mPostingInfoList.size();
    }


<<<<<<< HEAD

}


=======
}

>>>>>>> f680a193d2a34204a75b05f0e4d5395ff9ec1779
