package com.letz.photomemory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateImagesActivity extends AppCompatActivity {
    private ImageView imageViewUpdateImage;
    private EditText editTextUpdateImageTitle, editTextUpdateImageDescription;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Update Image");
        setContentView(R.layout.activity_add_images);

        imageViewUpdateImage = findViewById(R.id.imageViewUpdateImage);
        editTextUpdateImageTitle = findViewById(R.id.editTextUpdateIamgeTitle);
        editTextUpdateImageDescription= findViewById(R.id.editTextUpdateIamgeTitle);
        btnUpdate = findViewById(R.id.btnUpdate);
        imageViewUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}