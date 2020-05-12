package com.example.photopostiongyang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photopostiongyang.Adapter.DetailAdapter;
import com.example.photopostiongyang.Adapter.SliderAdapterExample;
import com.example.photopostiongyang.Model.PostingInfo;
import com.example.photopostiongyang.Model.ReplyInfo;
import com.example.photopostiongyang.Model.SliderItem;
import com.example.photopostiongyang.R;
import com.google.android.exoplayer2.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseFirestore mStore=FirebaseFirestore.getInstance();
    //받아올것들
    private TextView mNameTextView;
    private TextView mDateTextView;
    private TextView mTitleTextView;
    private TextView mContentsTextView;
    private SliderView mImageSliderView;
    private ArrayList<String> mImageArrayList;
    private String mDocumentId;
    private TextInputLayout mTextInputLayout;
    private TextInputEditText mTextInputEditText;
    private List<ReplyInfo> mReplyInfoList=new ArrayList<>();
    private RecyclerView mReplyRecyclerView;
    private DetailAdapter mDetailAdapter;
    private String userId="여기에유저아이디";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String documentId=getIntent().getStringExtra("DocumentId");//받아오는값 한마디로 이 액티비티는 반드시 startActivity와 pustExtra와 함꼐 해야함
        mDocumentId=documentId;
        Log.d("DocumentId",documentId);
        final DocumentReference documentReference=mStore.collection("Testing").document(mDocumentId);
        mNameTextView=findViewById(R.id.detail_name_TextVIew);
        mDateTextView=findViewById(R.id.detail_date_TextView);
        mTitleTextView=findViewById(R.id.detail_title_TextView);
        mContentsTextView=findViewById(R.id.detail_contents_TextView);
        mTextInputLayout=findViewById(R.id.detail_TextIputLayout);
        mTextInputEditText=findViewById(R.id.detail_TextIputEditText);
        mImageSliderView=findViewById(R.id.detail_imageSliderView);

        mReplyRecyclerView=findViewById(R.id.detail_Reply_RecyclerView);

        mImageSliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        mImageSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        mImageSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        mImageSliderView.setIndicatorSelectedColor(Color.WHITE);
        mImageSliderView.setIndicatorUnselectedColor(Color.GRAY);
        mImageSliderView.setScrollTimeInSec(3);
        mImageSliderView.setAutoCycle(false);
        retreiveDocumentReference(documentReference);



        findViewById(R.id.detail_backtoMain).setOnClickListener(this);
        findViewById(R.id.detail_menu_imageView).setOnClickListener(this);



        mTextInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Log.d("upload", "reply_string");

                        mStore=FirebaseFirestore.getInstance();
                        final DocumentReference replyDocumentreference = mStore.collection("Testing").document(documentId).collection("reply").document();
                        String reply_string = mTextInputEditText.getText().toString();
                        ReplyInfo replyInfo=new ReplyInfo(userId,reply_string);
//                        mReplyInfoList.add(replyInfo);
                        Log.d("upload", reply_string);
//
                        Log.d("upload", reply_string);
                        replyDocumentreference.set(replyInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("upload", "success");
                                mDetailAdapter.addItem(replyInfo);
                                mTextInputEditText.setText("");
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(mTextInputEditText.getWindowToken(), 0);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("upload", "fail");
                            }
                        });
            }
        });
    }
    //Posting Info 가져와서 그려줌
    private void retreiveDocumentReference(DocumentReference documentReference) {
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mImageArrayList = new ArrayList<>();
                PostingInfo postingInfo = documentSnapshot.toObject(PostingInfo.class);
                mNameTextView.setText(postingInfo.getNickname());
                mDateTextView.setText(postingInfo.getDate().toString());
                mTitleTextView.setText(postingInfo.getTitle());
                mContentsTextView.setText(postingInfo.getContents());
                mImageArrayList = postingInfo.getImageStringlist();
                SliderAdapterExample sliderAdapterExample = new SliderAdapterExample(DetailActivity.this);
                for (int i = 0; i < mImageArrayList.size(); i++) {
                    sliderAdapterExample.addItem(new SliderItem(mImageArrayList.get(i)));
                }
                mImageSliderView.setSliderAdapter(sliderAdapterExample);
                //댓글가져오기
                CollectionReference collectionReference =documentReference.collection("reply");

                collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.getResult()!=null){
                            for(DocumentSnapshot document:task.getResult()){
                                ReplyInfo replyInfo=document.toObject(ReplyInfo.class);
                                mReplyInfoList.add(replyInfo);
                               // mDetailAdapter.addItem(replyInfo);
                            }
                            mDetailAdapter=new DetailAdapter(mReplyInfoList);
                            mReplyRecyclerView.setAdapter(mDetailAdapter);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_backtoMain:
                startActivity(new Intent(DetailActivity.this,MainActivity.class));
                break;
            case R.id.detail_menu_imageView:
                showPopup(v,mDocumentId);
                break;
        }
    }
    public void showPopup(View v,String documentId) {
        FirebaseFirestore mStore=FirebaseFirestore.getInstance();
        PopupMenu popup = new PopupMenu(DetailActivity.this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.modify:
                        Toast.makeText(DetailActivity.this,"무슨수정이냐 그냥 쳐 삭제해라",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.remove:
                        mStore.collection("Testing").document(documentId)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(DetailActivity.this,"데이터베이스에서 삭제됨.새로고침안해도댐",Toast.LENGTH_LONG).show();
                                        Intent intent1 =new Intent(v.getContext(),MainActivity.class);
                                        intent1.putExtra("Refresh","success");
                                        DetailActivity.this.startActivity(intent1);
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
}
