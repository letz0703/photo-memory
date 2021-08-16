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
import android.graphics.BitmapFactory;
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

public class UpdateImagesActivity extends AppCompatActivity
{
    private ImageView imageViewUpdateImage;
    private EditText editTextUpdateImageTitle, editTextUpdateImageDescription;
    private Button btnUpdate;

    private String title, description;
    private byte[] image;
    private int id;

    private Bitmap updatedImage;
    private Bitmap scaledImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_images);
        imageViewUpdateImage = findViewById(R.id.imageViewUpdateImage);
        editTextUpdateImageTitle = findViewById(R.id.editTextUpdateIamgeTitle);
        editTextUpdateImageDescription = findViewById(R.id.editTextUpdateImageDescription);
        btnUpdate = findViewById(R.id.btnUpdateImage);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        image = getIntent().getByteArrayExtra("image");

        editTextUpdateImageTitle.setText(title);
        editTextUpdateImageDescription.setText(description);
        imageViewUpdateImage.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
//        imageViewUpdateImage.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        String addedImageTitle = editTextUpdateImageTitle.getText().toString();
//        String addedImageDescription = editTextUpdateImageDescription.getText().toString();

        imageViewUpdateImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UpdateImagesActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UpdateImagesActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
                // 퍼미션 이미 줬으면
                else {
                    Intent UpdateImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    UpdateImageLauncher.launch(UpdateImageIntent);
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (id == -1) {
                    Toast.makeText(UpdateImagesActivity.this, "There is a problem", Toast.LENGTH_SHORT).show();
                } else {

                    String updateTitle = editTextUpdateImageTitle.getText().toString();
                    String updateDescription = editTextUpdateImageDescription.getText().toString();
                    Intent updateToMainIntent = new Intent();

                    updateToMainIntent.putExtra("updateId", id);
                    updateToMainIntent.putExtra("updateTitle", updateTitle);
                    updateToMainIntent.putExtra("updateDescription", updateDescription);

                    if (updatedImage == null) {
                        updateToMainIntent.putExtra("updateImage", image);

                    } else {

                        // convert image to byte type
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                        scaledImage = makeImageSmall(updatedImage, 300);

                        //compress image -> 포맷, 이미지 퀄러티,OutputStream Object
                        scaledImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
                        byte[] image = outputStream.toByteArray();

                        updateToMainIntent.putExtra("updateImage", image);
                    }

                    setResult(Activity.RESULT_OK, updateToMainIntent);
                    Toast.makeText(UpdateImagesActivity.this, "picture updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            private Bitmap makeImageSmall(Bitmap updatedImage, int maxSize) {

                int width = updatedImage.getWidth();
                int height = updatedImage.getHeight();
                float ratio = (float) width / (float) height;

                if (ratio > 1) {
                    width = maxSize;
                    height = (int) (width / ratio);
                } else {
                    width = (int) (height * ratio);
                    height = maxSize;
                }

                return Bitmap.createScaledBitmap(updatedImage, width, height, true);
            }
        });
    }

    ActivityResultLauncher<Intent> UpdateImageLauncher = registerForActivityResult(
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
                                        = ImageDecoder.createSource(UpdateImagesActivity.this.getContentResolver(), data.getData());
                                updatedImage = ImageDecoder.decodeBitmap(source);
                            } else {
                                updatedImage = MediaStore.Images.Media.getBitmap(UpdateImagesActivity.this.getContentResolver(), data.getData());
                            }
                            imageViewUpdateImage.setImageBitmap(updatedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

}