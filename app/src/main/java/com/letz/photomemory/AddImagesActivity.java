package com.letz.photomemory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddImagesActivity extends AppCompatActivity {
    private ImageView imageViewAddImage;
    private EditText editTextAddImageTitle, editTextAddImageDescription;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Image");
        setContentView(R.layout.activity_add_images);

        imageViewAddImage = findViewById(R.id.imageViewAddImage);
        editTextAddImageTitle = findViewById(R.id.editTextAddIamgeTitle);
        editTextAddImageDescription= findViewById(R.id.editTextAddIamgeTitle);
        btnSave = findViewById(R.id.btnSave);
        imageViewAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 퍼미션 아직 안 줬으면
                if (ContextCompat.checkSelfPermission(AddImagesActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(AddImagesActivity.this
                            ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                            ,1);
                }
                // 퍼미션 이미 줬으면
                else
                {
                    Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(imageIntent,2);
                }

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}