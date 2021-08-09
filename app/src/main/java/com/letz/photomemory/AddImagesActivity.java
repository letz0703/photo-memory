package com.letz.photomemory;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class AddImagesActivity extends AppCompatActivity
{

    private static final int GALLERY_REQUEST_CODE = 123;

    private static int RESULT_LOAD_IMAGE = 7;

    private ImageView imageViewAddImage;
    private EditText editTextAddImageTitle, editTextAddImageDescription;
    private Button btnSave;

    private Bitmap selectedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Image");
        setContentView(R.layout.activity_add_images);

        imageViewAddImage = findViewById(R.id.imageViewAddImage);
        editTextAddImageTitle = findViewById(R.id.editTextAddIamgeTitle);
        editTextAddImageDescription = findViewById(R.id.editTextAddIamgeTitle);
        btnSave = findViewById(R.id.btnSave);

//        Glide.with(this).load("http://goo.go/gEgYUd").into(imageViewAddImage);



        imageViewAddImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 사용자가 퍼미션 아직 안 줬으면
                if (ContextCompat.checkSelfPermission(AddImagesActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddImagesActivity.this
                            , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                            , 1);
                }
                // 퍼미션 이미 줬으면
                else {
                    Intent AddImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(AddImage,2);
                    AddImageLauncher.launch(AddImageIntent);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Toast.makeText(AddImagesActivity.this, "picture added", Toast.LENGTH_SHORT).show();
                backToMainActivity();
            }

            private void backToMainActivity()
            {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
//        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent AddImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(AddImage,2);
            AddImageLauncher.launch(AddImageIntent);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    ActivityResultLauncher< Intent > AddImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        try {
                            if (Build.VERSION.SDK_INT >= 28) {
                                ImageDecoder.Source source
                                        = ImageDecoder.createSource(AddImagesActivity.this.getContentResolver(), data.getData());
                                selectedImage = ImageDecoder.decodeBitmap(source);
                            } else {
                                selectedImage = MediaStore.Images.Media.getBitmap(AddImagesActivity.this.getContentResolver(), data.getData());
                            }

                            imageViewAddImage.setImageBitmap(selectedImage);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
    );




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
//    {
//        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
//            try {
//                if (Build.VERSION.SDK_INT >= 28) {
//                    ImageDecoder.Source source
//                            = ImageDecoder.createSource(this.getContentResolver(), data.getData());
//                    selectedImage = ImageDecoder.decodeBitmap(source);
//                } else {
//                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
//                }
//
//                imageViewAddImage.setImageBitmap(selectedImage);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}