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
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddImagesActivity extends AppCompatActivity
{
    private ImageView imageViewAddImage;
    private EditText editTextAddImageTitle, editTextAddImageDescription;
    private Button btnSave;

    private Bitmap selectedImage;
    private Bitmap scaledImage;

    String addedImageTitle = editTextAddImageTitle.getText().toString();
    String addedIageDescription = editTextAddImageDescription.getText().toString();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Image");
        setContentView(R.layout.activity_add_images);

        imageViewAddImage = findViewById(R.id.imageViewAddImage);
        editTextAddImageTitle = findViewById(R.id.editTextAddIamgeTitle);
        editTextAddImageDescription = findViewById(R.id.editTextAddIamgeTitle);
        btnSave = findViewById(R.id.btnSave);

        String addedImageTitle = editTextAddImageTitle.getText().toString();
        String addedIageDescription = editTextAddImageDescription.getText().toString();

        // convert image to byte type
        imageViewAddImage.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
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
                            AddImageLauncher.launch(AddImageIntent);
                        }
                    }
                }

        );

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String addedImageTitle = editTextAddImageTitle.getText().toString();
                String addedIageDescription = editTextAddImageDescription.getText().toString();
                // convert image to byte type
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                scaledImage = makeImageSmall(selectedImage, 300);

                //compress image -> 포맷, 이미지 퀄러티,OutputStream Object
                selectedImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);

                Toast.makeText(AddImagesActivity.this, "picture added", Toast.LENGTH_SHORT).show();
                backToMainActivity();
            }

            private void backToMainActivity() {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

    private Bitmap makeImageSmall(Bitmap selectedImage, int maxSize) {

        int width = selectedImage.getWidth();
        int height = selectedImage.getHeight();
        float ratio = (float) width / (float) height;

        if (ratio > 1) {
            width = maxSize;
            height = (int) (width / ratio);
        } else {
            width = (int) (height * ratio);
            height = maxSize;
        }

        return Bitmap.createScaledBitmap(selectedImage, width, height, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent AddImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(AddImage,2);
            AddImageLauncher.launch(AddImageIntent);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    ActivityResultLauncher<Intent> AddImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
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
}