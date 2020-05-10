package com.example.photopostiongyang.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photopostiongyang.Adapter.SliderAdapterExample;
import com.example.photopostiongyang.Model.PostingInfo;
import com.example.photopostiongyang.Model.SliderItem;
import com.example.photopostiongyang.R;
import com.google.android.exoplayer2.util.Log;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private FirebaseFirestore mStore=FirebaseFirestore.getInstance();
    //받아올것들
    private TextView mNameTextView;
    private TextView mDateTextView;
    private TextView mTitleTextView;
    private TextView mContentsTextView;
    private SliderView mImageSliderView;
    private ArrayList<String> mImageArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String documentId=getIntent().getStringExtra("DocumentId");
        Log.d("DocumentId",documentId);
        final DocumentReference documentReference=mStore.collection("Testing").document(documentId);
        mNameTextView=findViewById(R.id.detail_name_TextVIew);
        mDateTextView=findViewById(R.id.detail_date_TextView);
        mTitleTextView=findViewById(R.id.detail_title_TextView);
        mContentsTextView=findViewById(R.id.detail_contents_TextView);
        mImageSliderView=findViewById(R.id.detail_imageSliderView);
        mImageSliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        mImageSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        mImageSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        mImageSliderView.setIndicatorSelectedColor(Color.WHITE);
        mImageSliderView.setIndicatorUnselectedColor(Color.GRAY);
        mImageSliderView.setScrollTimeInSec(3);
        mImageSliderView.setAutoCycle(false);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mImageArrayList=new ArrayList<>();
                PostingInfo postingInfo = documentSnapshot.toObject(PostingInfo.class);
                mNameTextView.setText(postingInfo.getNickname());
                mDateTextView.setText(postingInfo.getDate().toString());
                mTitleTextView.setText(postingInfo.getTitle());
                mContentsTextView.setText(postingInfo.getContents());
                mImageArrayList=postingInfo.getImageStringlist();
                SliderAdapterExample sliderAdapterExample = new SliderAdapterExample(DetailActivity.this);
                for (int i = 0; i < mImageArrayList.size(); i++) {
                    sliderAdapterExample.addItem(new SliderItem(mImageArrayList.get(i)));
                }
                mImageSliderView.setSliderAdapter(sliderAdapterExample);

            }
        });


    }



}
