package com.example.photopostiongyang.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.photopostiongyang.Adapter.SliderAdapterExample;
import com.example.photopostiongyang.Model.PostingInfo;
import com.example.photopostiongyang.Model.SliderItem;
import com.example.photopostiongyang.R;
import com.google.android.exoplayer2.util.Log;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WriteActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "제발...";
    private StorageReference mStorage;
    private static final int RESULT_LOAD_IMAGE = 1;
    private ArrayList<String> imageStringList;
    private List<Uri> imageUriList;
    private FirebaseFirestore mStore ;
    private StorageReference mStorageRef;//이건 파일 업로드할떄
    private DatabaseReference mDatabaseRef;//업로드한파일 데이터베이스에도 저장 원래는 스토리지에만 저장되잇음
    private EditText mWriteTitle;
    private EditText mWriteContentsText;
    private SliderAdapterExample sliderAdapterExample;
    private ProgressDialog loadingbar;
    private ArrayList<String> mDownloadURI;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

       //스토리지 추가되는  폴더  이름
        mStore = FirebaseFirestore.getInstance();
       // mDatabaseRef = FirebaseDatabase.getInstance().getReference("images");//데이터베이스에 추가되는 폴더 이름


        findViewById(R.id.write_upload_botton).setOnClickListener(this);
      //  ivPreview = (ImageView) findViewById(R.id.iv_preview);
       // uploadListAdapter = new UploadListAdapter(fileNameList, fileDoneList);


        //이미지 슬라이더
        SliderView sliderView=findViewById(R.id.write_imageslider);
        sliderAdapterExample = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(sliderAdapterExample);


        ///sliderAdapterExample.addItem(<SliderItem>);데이터 넣는과정//////////////




        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(false);


        findViewById(R.id.write_imagechoose_imageButton).setOnClickListener(this);

        mWriteTitle = findViewById(R.id.write_title_text);
        mWriteContentsText = findViewById(R.id.write_contents_text);
        loadingbar=new ProgressDialog(this);

        imageStringList=new ArrayList<>();//이거없으면안댐
        imageUriList=new ArrayList<>();//이거없으면안댐



    }




    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.write_upload_botton:
                        uploadFile();


                break;
            case R.id.write_imagechoose_imageButton://겔러리랑연동
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);
                break;
        }
    }
    //업로드파일


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        //Log.i("result", String.valueOf(resultCode));
        if (resultCode == Activity.RESULT_OK) {
            //ArrayList imageList = new ArrayList<>();

            // 멀티 선택을 지원하지 않는 기기에서는 getClipdata()가 없음 => getData()로 접근해야 함
            if (data.getClipData() == null) {
               // Log.i("1. single choice", String.valueOf(data.getData()));
                imageStringList.add(String.valueOf(data.getData()));
                imageUriList.add(data.getData());
                sliderAdapterExample.addItem(new SliderItem(String.valueOf(data.getData())));
            } else {

                ClipData clipData = data.getClipData();
                if (clipData.getItemCount() > 10) {
                    Toast.makeText(WriteActivity.this, "사진은 10개까지 선택가능 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 멀티 선택에서 하나만 선택했을 경우
                else if (clipData.getItemCount() == 1) {
                    String dataStr = String.valueOf(clipData.getItemAt(0).getUri());
                    imageStringList.add(dataStr);
                    imageUriList.add(data.getData());
                    sliderAdapterExample.addItem(new SliderItem(dataStr));

                } else if (clipData.getItemCount() > 1 && clipData.getItemCount() < 10) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        //     Log.i("3. single choice", String.valueOf(clipData.getItemAt(i).getUri()));
                        imageStringList.add(String.valueOf(clipData.getItemAt(i).getUri()));
                        imageUriList.add(clipData.getItemAt(i).getUri());
                        sliderAdapterExample.addItem(new SliderItem(String.valueOf(clipData.getItemAt(i).getUri())));
                    }
                }
            }
        } else {
            Toast.makeText(WriteActivity.this, "사진 선택을 취소하였습니다.", Toast.LENGTH_SHORT).show();
        }
    }




    ///////////////////////////////업로드 과정\
    private void uploadFile() {
        //업로드할 파일이 있으면 수행
        loadingbar.setTitle("Set profile image");
        loadingbar.setMessage("pleas wait업로딩중");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();
        mDownloadURI = new ArrayList<>();
        Date time = new Date();

        mStorageRef = FirebaseStorage.getInstance().getReference("image");//여기에 아이디적으면댐
        for (int i = 0; i < imageUriList.size(); i++) {
            Uri imageUri = imageUriList.get(i);
            StorageReference imgref = mStorageRef.child(imageUri.getLastPathSegment()+time.toString());//images파일 또있으면 적용안댐 아이디에 추가적으로 이미지저장
            UploadTask uploadTask = imgref.putFile(imageUri);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return imgref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        mDownloadURI.add(downloadUri.toString());
                        Log.d("yang",mDownloadURI.get(0).toString());

                        if(mDownloadURI.size()==imageUriList.size()){
                            String dynamiclink="test";
                            PostingInfo postingInfo = new PostingInfo(
                                    mDownloadURI
                                    , mWriteTitle.getText().toString()
                                    , mWriteContentsText.getText().toString()
                                    , new String("name")
                                    , 0
                                    , time
                                    ,dynamiclink);
                            Log.d("성공","성공");
                            storeUpload(postingInfo);
                        }
                    }
                }
            });

        }//for문끝  스토리지에 저장만함

    }
    private void storeUpload(PostingInfo postingInfo){


        DocumentReference newTestingRef=mStore.collection("Testing").document();
        String documentId=newTestingRef.getId();

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.photopostiongyang.com/"+documentId))
                .setDomainUriPrefix("https://yangseongyeal.page.link")
                // Set parameters
                // ...
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            postingInfo.setDynamicLink(shortLink.toString());//uri로 바궈주자
                            Uri flowchartLink = task.getResult().getPreviewLink();

                            newTestingRef
                                    .set(postingInfo)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            Toast.makeText(WriteActivity.this, "파이어스토어에저장완료 새로고침 ㄱㄱ", Toast.LENGTH_LONG).show();
                                            Log.d("test", "스토어저장");
                                            loadingbar.dismiss();
                                            imageStringList.clear();
                                            Log.d("도큐먼트",newTestingRef.getId());

                                            Intent intent1 =new Intent(getApplicationContext(),MainActivity.class);
                                            intent1.putExtra("Refresh","success");
                                            startActivity(intent1);


                                            //finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(WriteActivity.this, "파이어스토어에 저장실패", Toast.LENGTH_LONG).show();
                                            loadingbar.dismiss();
                                            finish();
                                        }
                                    });

                        } else {
                            // Error
                            // ...
                        }
                    }
                });

    }


    /////////////업로드

}

