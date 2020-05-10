package com.example.photopostiongyang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.photopostiongyang.Adapter.MainAdapter;
import com.example.photopostiongyang.Model.PostingInfo;
import com.example.photopostiongyang.R;
import com.google.android.exoplayer2.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mMainRecyclerView;
    private FirebaseFirestore mStore=FirebaseFirestore.getInstance();
    private MainAdapter mainAdapter;
    private List<PostingInfo> mPostingInfoList;
    private List<String> mDocumentIdList;
    private SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findViewById(R.id.imageButton1).setOnClickListener(this);
        //플로팅버튼
        findViewById(R.id.float_btn).setOnClickListener(this);
        mMainRecyclerView=findViewById(R.id.main_recycler_view);
        mMainRecyclerView.setHasFixedSize(true);
        swipeRefreshLayout=findViewById(R.id.main_SwipeRefreshLayout);
        retreive_Testing();
        deepLinkSwitcher();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retreive_Testing();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        String getString=getIntent().getStringExtra("Refresh");
       try {//업로드 하자마자 자동 refresh
           if(getString.equals("success")){
               mainAdapter.notifyDataSetChanged();
           }
       }catch (Exception e){
          Log.d("eror", e.toString());
       }





    }
    private void deepLinkSwitcher() {//딥링크받음
        Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {
            String intentData = intent.getData().toString();
            Log.d("deepData", intentData.toString());
            String appstring="https://www.photopostiongyang.com/";
            String documentId=intentData.substring(34,intentData.length());

            if (intentData != null && !intentData.isEmpty()) {//파이어베이스에서 만든 링크는 잘 들어옴.
                /*
                Here
                SCHEME = https
                HOST = www.pexels.com
                PATH PATTERN = /@md-emran-hossain-emran-11822
                * */
                Intent documentIdIntent=new Intent(MainActivity.this,DetailActivity.class);
                documentIdIntent.putExtra("DocumentId",documentId);
                startActivity(documentIdIntent);
            }
        }
    }//end function
    //가져오기만하기
    public void retreive_Testing(){
        mPostingInfoList=new ArrayList<>();
        mDocumentIdList=new ArrayList<>();
        mStore.collection("Testing")
                .orderBy("date", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //        for (QueryDocumentSnapshot document : task.getResult()) {
                        //Log.d("yangseongyeal",document.getId());
                        PostingInfo postingInfo = document.toObject(PostingInfo.class);
                        mDocumentIdList.add(document.getId());
                        mPostingInfoList.add(postingInfo);
                    }
                    mainAdapter = new MainAdapter(mPostingInfoList, MainActivity.this, mDocumentIdList);
                    mainAdapter.setOnIemlClickListner(new MainAdapter.OnItemClickListener() {//클릭됬을때
                        @Override
                        public void onitemClick(View v, int pos) {
                            Intent intent=new Intent(MainActivity.this,DetailActivity.class);
                            intent.putExtra("DocumentId",mDocumentIdList.get(pos));
                            Log.d("DocumentId",mDocumentIdList.get(pos));
                            startActivity(intent);


                        }
                    });
                    mMainRecyclerView.setAdapter(mainAdapter);
                } else {
                    Log.d("ttt", "Error getting documents: ", task.getException());
                }
            }
        });
    }
    //업데이트 된거 가져오기
    public void retreive_Testing_update(){//실시간업로드 근데 사용안할듯
        mPostingInfoList=new ArrayList<>();
        mDocumentIdList=new ArrayList<>();

        DocumentReference documentReference=mStore.collection("Testing").document();
        mStore.collection("Testing")
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {

                            return;
                        }
                        if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {

                            mPostingInfoList.clear();
                            mDocumentIdList.clear();
                            for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                                PostingInfo postingInfo=documentSnapshot.toObject(PostingInfo.class);
                                String documentId=documentSnapshot.getId();

                                mDocumentIdList.add(documentId);
                                mPostingInfoList.add(postingInfo);
                            }
                            mainAdapter=new MainAdapter(mPostingInfoList,MainActivity.this,mDocumentIdList);

//                    mainAdapter.setOnIemlClickListner(new MainAdapter.OnItemClickListener() {
//                        @Override
//                        public void onitemClick(View v, int pos) {
//                        클릭하면 커지는거...
//                        }
//                    });
                            mMainRecyclerView.setAdapter(mainAdapter);
                        } else {
                            Log.d("양성열", "Current data: null");
                        }
                    }
                });
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
