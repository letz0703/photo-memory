package com.letz.photomemory;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView rv;
    private FloatingActionButton fab;

    private MyImagesViewModel myImagesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        fab = findViewById(R.id.fab);

        MyImagesAdapter adapter = new MyImagesAdapter();
        rv.setAdapter(adapter);
        // display rv top to bottom
        rv.setLayoutManager(new LinearLayoutManager(this));

        myImagesViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(MyImagesViewModel.class);
        myImagesViewModel.getAllImages().observe(MainActivity.this, new Observer<List<MyImages>>()
        {
            @Override
            public void onChanged(List<MyImages> myImages) {
                adapter.setImagesList(myImages);
            }
        });


        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                move2AddImage();

            }

            private void move2AddImage() {
                Intent intent = new Intent(MainActivity.this, AddImagesActivity.class);
                startActivityForResult(intent, 3);
            }
        });
    }


}