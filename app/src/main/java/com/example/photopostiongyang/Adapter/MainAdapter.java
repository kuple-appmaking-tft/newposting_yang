package com.example.photopostiongyang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photopostiongyang.Model.PostingInfo;
import com.example.photopostiongyang.Model.SliderItem;
import com.example.photopostiongyang.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{
    private Context mContext;
    private List<PostingInfo> mPostingInfoList;

    public interface OnItemClickListener{
        void onItemClick(View v,int pos);
    }
    private OnItemClickListener mListener=null;
    public void setOnItemClickListner(OnItemClickListener listner){
        this.mListener=listner;
    }

     //데이터를 리스트형식 (model형으로 제네릭)

    public MainAdapter(List<PostingInfo> mPostingInfoList, Context mContext ) {//데이터 들어오면 저장해주는 생성자
        this.mPostingInfoList=mPostingInfoList;
        this.mContext=mContext;
    }

    class MainViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitleTextView;        //item_main의 객체를 불러옴...작은네모칸에 들어갈 얘들 선언
        private TextView mNameTextView;
        private TextView mContentsTextView;
        private SliderView mImageSliderView;


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
            mTitleTextView=itemView.findViewById(R.id.item_title_text);
            mNameTextView=itemView.findViewById(R.id.item_name_text);
            mContentsTextView=itemView.findViewById(R.id.item_contents_text);
            mImageSliderView=itemView.findViewById(R.id.item_imageslider);

            mImageSliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            mImageSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            mImageSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
            mImageSliderView.setIndicatorSelectedColor(Color.WHITE);
            mImageSliderView.setIndicatorUnselectedColor(Color.GRAY);
            mImageSliderView.setScrollTimeInSec(3);
            mImageSliderView.setAutoCycle(false);

        }
    }


    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false));//아이템메뉴는 작은내모가 확장되있는거
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {//class MainViewHolder의 holder <Board>형식의 data값을 참조.
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

    @Override
    public int getItemCount() {
        return mPostingInfoList.size();
    }


}

