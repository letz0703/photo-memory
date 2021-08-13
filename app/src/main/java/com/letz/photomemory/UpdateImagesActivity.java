package com.letz.photomemory;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateImagesActivity extends AppCompatActivity
{
    private ImageView imageViewUpdateImage;
    private EditText editTextUpdateImageTitle, editTextUpdateImageDescription;
    private Button btnUpdate;

    private String title, description;
    private byte[] images;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Update Image");
        setContentView(R.layout.activity_update_images);

        imageViewUpdateImage = findViewById(R.id.imageViewUpdateImage);
        editTextUpdateImageTitle = findViewById(R.id.editTextUpdateIamgeTitle);
        editTextUpdateImageDescription = findViewById(R.id.editTextUpdateImageDescription);
        btnUpdate = findViewById(R.id.btnUpdate);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        images = getIntent().getByteArrayExtra("image");

        editTextUpdateImageTitle.setText(title);
        editTextUpdateImageDescription.setText(description);
        imageViewUpdateImage.setImageBitmap(BitmapFactory.decodeByteArray(images, 0, images.length));

        imageViewUpdateImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

            }
        });
    }

}