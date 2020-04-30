package com.example.photopostiongyang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photopostiongyang.Adapter.MainAdapter;
import com.example.photopostiongyang.Model.PostingInfo;
import com.example.photopostiongyang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mMainRecyclerView;
    private FirebaseFirestore mStore=FirebaseFirestore.getInstance();
    private MainAdapter mainAdapter;
    private List<PostingInfo> mPostingInfoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findViewById(R.id.imageButton1).setOnClickListener(this);
        //플로팅버튼
        findViewById(R.id.float_btn).setOnClickListener(this);
        mMainRecyclerView=findViewById(R.id.main_recycler_view);
        mMainRecyclerView.setHasFixedSize(true);
    //


        //파이어베이스에서 읽어오기
        mPostingInfoList=new ArrayList<>();
        /*
        DocumentReference documentReference=mStore.collection("Testing").document("users");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if(e !=null){
                        Log.w("ddd", "Listen failed.", e);
                        return;
                    }
                if (documentSnapshot != null && documentSnapshot.exists()) {

                    Log.d("success", "Current data: " + documentSnapshot.getData());
                    PostingInfo postingInfo = documentSnapshot.toObject(PostingInfo.class);
                     mPostingInfoList.add(postingInfo);
                   // ArrayList<String> imageStringlist = postingInfo.getImageStringlist();
                   mainAdapter=new MainAdapter(mPostingInfoList,MainActivity.this);
                    mMainRecyclerView.setAdapter(mainAdapter);
                } else {
                    Log.d("data=null", "Current data: null");
                }
            }
        });
        */ //여기는 변화만 불러오는작업

        mStore.collection("Testing").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                       PostingInfo postingInfo=document.toObject(PostingInfo.class);
                        mPostingInfoList.add(postingInfo);
                        mainAdapter=new MainAdapter(mPostingInfoList,MainActivity.this);
                        mMainRecyclerView.setAdapter(mainAdapter);
                    }
                }else{

                }
            }
        });
          //업데이트 될떄마다 가져오기





        //MainAdapter adapter=new MainAdapter(mBoardList);


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.float_btn:
                startActivity(new Intent(this, WriteActivity.class));
                break;

        }
    }
}
