package com.example.vb.firebaseblogapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

public class PostActivity extends AppCompatActivity {

    private ImageButton mSelectImage;
    private static final int Gallery_Request =1;
    private EditText mPostTitle;
    private EditText mPostDesc;
    Uri imgUri;
    private StorageReference mStorageRef;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        load();

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ımage buttona basılınca galeryi çalışıtırp bir resim seçtiriyoruz
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_Request);

            }
        });

    }

    private void load() {
        //text butonlarımızı tanıdğımız blok

        mProgress=new ProgressDialog(this);
        mSelectImage= (ImageButton) findViewById(R.id.imgSelect);
        mPostDesc= (EditText) findViewById(R.id.DescField);
        mPostTitle = (EditText) findViewById(R.id.titleField);
        //firebase mizle bağlantı kuruyoruz
        //blog yazılarmızın oldugu bir dal olsutuyoruz
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        mStorageRef= FirebaseStorage.getInstance().getReference();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //gelen değerin kontrolünü sağlıyoruz
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gallery_Request) {
            //uri nesneleri kaynağı belirlemek için kullanılır
            imgUri = data.getData();
            mSelectImage.setImageURI(imgUri);

        }

    }

    public void post(View view) {
    //gelen yazıları ve resmi şimdi firebase gönderme

        mProgress.setMessage("Yayınlanıyor...");
        mProgress.show();
        //gelen textlerimizi düzeltip gönderiyoruz
        final String title_val = mPostTitle.getText().toString().trim();
        final String desc_val = mPostDesc.getText().toString().trim();

        //gelen değerlerin boşmu kontorlü
        if (!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(title_val)) {
            StorageReference filepath = mStorageRef.child("Blog_images").child(imgUri.getLastPathSegment());

            //Blog images adında bir dal oluşturup içersine resmimizi gönderiyoruz
            filepath.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri dowloadUri = taskSnapshot.getDownloadUrl();
                   //databasemize göndermek icin bir nesne olsutyoruz
                    DatabaseReference newPost = mDatabase.push();

                    //dallarına title ve desc diye başlık açarak değerleri gömüyoruz
                    newPost.child("title").setValue(title_val);
                    newPost.child("desc").setValue(desc_val);
                    newPost.child("image").setValue(dowloadUri.toString());

                    //yeni gelen her nesneyide bu kalıba göre oluşturuyoruz
                    mProgress.dismiss();
                    startActivity(new Intent(PostActivity.this,MainActivity.class));
                }
            });
        }
    }

}
