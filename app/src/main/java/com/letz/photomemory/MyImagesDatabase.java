package com.letz.photomemory;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = MyImages.class, version = 1)
public abstract class MyImagesDatabase extends RoomDatabase {
    private static MyImagesDatabase instance;

    public abstract MyImagesDAO myImagesDAO();

    public static synchronized MyImagesDatabase getInstance(Context context) {
        // instance가 null 인지 아닌지를 살핀다
        if (instance == null) {
//            null ? 생성
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , MyImagesDatabase.class, "my_image_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}