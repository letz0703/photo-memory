package com.letz.photomemory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}