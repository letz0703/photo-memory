package com.letz.photomemory;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_images")
public class MyImages {
    @PrimaryKey(autoGenerate = true)
    public int image_id;
    public String image_name;
    public String image_descriptoin;
    public byte[] image;

    public MyImages(String image_name, String image_descriptoin, byte[] image) {

        this.image_name = image_name;
        this.image_descriptoin = image_descriptoin;
        this.image = image;
    }

    public int getImage_id() {
        return image_id;
    }

    public String getImage_name() {
        return image_name;
    }

    public String getImage_descriptoin() {
        return image_descriptoin;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }
}
