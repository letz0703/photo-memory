package com.letz.photomemory;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

        fab.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, AddImagesActivity.class);
            startARLauncher.launch(intent);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                myImagesViewModel.delete(viewHolder.getAdapterPosition() );
                myImagesViewModel.delete(adapter.getIamgeAtPosition(viewHolder.getAdapterPosition()));

            }
        }).attachToRecyclerView(rv);

        adapter.setListener(new MyImagesAdapter.onImageClickListener()
        {
            @Override
            public void onImageClick(MyImages myImages) {
                Intent intent = new Intent(MainActivity.this, UpdateImagesActivity.class);
                intent.putExtra("id", myImages.getImage_id());
                intent.putExtra("title", myImages.getImage_name());
                intent.putExtra("description", myImages.getImage_descriptoin());
                intent.putExtra("image", myImages.getImage());
                launchUpdate.launch(intent);
            }
        });

//        fab.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AddImagesActivity.class);
//                startARLauncher.launch(intent);
//            }
//
//        });
    }

    ActivityResultLauncher<Intent> launchUpdate = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> startARLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        String title = data.getStringExtra("title");
                        String description = data.getStringExtra("description");
                        byte[] image = data.getByteArrayExtra("image");

                        MyImages myImages = new MyImages(title, description, image);
                        myImagesViewModel.insert(myImages); // save to roomDB
                    }

                }
            }
    );

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 3 && requestCode == RESULT_OK && data != null) {
//
//            String title = data.getStringExtra("title");
//            String description = data.getStringExtra("description");
//            byte[] image = data.getByteArrayExtra("image");
//
//            MyImages myImages = new MyImages(title, description, image);
//            myImagesViewModel.insert(myImages); // save to roomDB
//        }
//    }
}