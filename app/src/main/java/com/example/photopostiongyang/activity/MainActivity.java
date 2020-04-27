package com.example.photopostiongyang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photopostiongyang.Adapter.MainAdapter;
import com.example.photopostiongyang.Model.Board;
import com.example.photopostiongyang.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mMainRecyclerView;
    private FirebaseFirestore mStore=FirebaseFirestore.getInstance();
    private MainAdapter mainAdapter;
    private List<Board> mBoardList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findViewById(R.id.imageButton1).setOnClickListener(this);
        //플로팅버튼
        findViewById(R.id.float_btn).setOnClickListener(this);
        mMainRecyclerView=findViewById(R.id.main_recycler_view);
        mMainRecyclerView.setHasFixedSize(true);
    //    mainAdapter=new MainAdapter(mBoardList);
   //     mMainRecyclerView.setAdapter(mainAdapter);


        //파이어베이스에서 읽어오기
        mBoardList=new ArrayList<>();

        mStore.collection("Board")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @NonNull FirebaseFirestoreException e) {
                        for(DocumentChange document:queryDocumentSnapshots.getDocumentChanges()){

                            // String id=(String) document.getDocument().get("id");

                            String title=(String)document.getDocument().get("Title");
                            String contents=(String)document.getDocument().get("contents");
                            // String name=(String)document.getDocument().get("name");
                            Board data=new Board(title,contents);
                            mBoardList.add(data);
                            mainAdapter=new MainAdapter(mBoardList);
                            mainAdapter.setOnItemClickListner(new MainAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int pos) {
                                    startActivity(new Intent(getApplicationContext(),temp.class));

                                }
                            });
                            mMainRecyclerView.setAdapter(mainAdapter);
                        }

                    }
                });

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
