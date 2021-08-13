package com.letz.photomemory;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyImagesAdapter extends RecyclerView.Adapter<MyImagesAdapter.myImagesHolder>
{
    private List<MyImages> imagesList = new ArrayList<>();
    private onImageClickListener listener;

    public void setListener(onImageClickListener listener) {
        this.listener = listener;
    }

    public void setImagesList(List<MyImages> imagesList) {
        this.imagesList = imagesList;
        notifyDataSetChanged();
    }

    public interface onImageClickListener
    {
        void onImageClick(MyImages myImages);
    }

    @NonNull
    @NotNull
    @Override
    public myImagesHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_card, parent, false);
        return new myImagesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull myImagesHolder holder, int position) {
        MyImages myImages = imagesList.get(position);
        holder.textViewTitle.setText(myImages.getImage_name());
        holder.textViewDescription.setText(myImages.getImage_descriptoin());
        holder.imageView.setImageBitmap(BitmapFactory
                .decodeByteArray(myImages.getImage()
                        , 0, myImages.getImage().length));
    }

    public MyImages getIamgeAtPosition(int position) {
        return imagesList.get(position);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class myImagesHolder extends RecyclerView.ViewHolder
    { // image_card에 있는 아이템들을 정의 한다
        private ImageView imageView;
        private TextView textViewTitle, textViewDescription;

        public myImagesHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onImageClick(imagesList.get(position));
                    }
                }
            });
        }
    }


}
