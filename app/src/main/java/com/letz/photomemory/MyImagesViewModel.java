package com.letz.photomemory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyImagesViewModel extends AndroidViewModel {
    // 2개의 정의 for this class
    // repository 와
    // imagesList 어레이
    private MyImagesRepository repository;
    private LiveData<List<MyImages>> imagesList;

    public MyImagesViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new MyImagesRepository(application);
        imagesList = repository.getAllImages();
    }
    // database 작동 within this class(View Model)
    public void insert(MyImages myImages)
    {
        repository.insert(myImages);
    }

    public void delete(MyImages myImages)
    {
        repository.delete(myImages);
    }

    public void update(MyImages myImages)
    {
        repository.update(myImages);
    }

    public LiveData<List<MyImages>> getAllImages()
    {
        return imagesList;
    }

}
