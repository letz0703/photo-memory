package com.letz.photomemory;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddImagesActivity extends AppCompatActivity
{
    private ImageView imageViewAddImage;
    private EditText editTextAddImageTitle, editTextAddImageDescription;
    private Button btnSaveAddImage;

    private Bitmap selectedImage;
    private Bitmap scaledImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_images);
        imageViewAddImage = findViewById(R.id.imageViewAddImage);
        editTextAddImageTitle = findViewById(R.id.editTextAddIamgeTitle);
        editTextAddImageDescription = findViewById(R.id.editTextAddImageDescription);
        btnSaveAddImage = findViewById(R.id.btnSaveAddImage);

//        String addedImageTitle = editTextAddImageTitle.getText().toString();
//        String addedImageDescription = editTextAddImageDescription.getText().toString();

        imageViewAddImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddImagesActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddImagesActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
                // 퍼미션 이미 줬으면
                else {
                    Intent AddImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    AddImageLauncher.launch(AddImageIntent);
                }
            }
        });

        btnSaveAddImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if (selectedImage == null) {
                    Toast.makeText(AddImagesActivity.this, "Select a pic!", Toast.LENGTH_SHORT).show();
                } else {
                    String addedImageTitle = editTextAddImageTitle.getText().toString();
                    String addedIageDescription = editTextAddImageDescription.getText().toString();
                    // convert image to byte type
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                    scaledImage = makeImageSmall(selectedImage, 300);

                    //compress image -> 포맷, 이미지 퀄러티,OutputStream Object
                    scaledImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
                    byte[] image = outputStream.toByteArray();

                    Intent intent = new Intent();

                    intent.putExtra("title", addedImageTitle);
                    intent.putExtra("description", addedIageDescription);
                    intent.putExtra("image", image);
                    setResult(Activity.RESULT_OK, intent);

                    Toast.makeText(AddImagesActivity.this, "picture added", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }

            private Bitmap makeImageSmall(Bitmap image, int maxSize) {

                int width = image.getWidth();
                int height = image.getHeight();
                float ratio = (float) width / (float) height;

                if (ratio > 1) {
                    width = maxSize;
                    height = (int) (width / ratio);
                } else {
                    width = (int) (height * ratio);
                    height = maxSize;
                }

                return Bitmap.createScaledBitmap(image, width, height, true);
            }


        });
    }

    //}
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