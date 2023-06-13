package com.github.cptzee.lovediary.Data.Image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.cptzee.lovediary.MainActivity;
import com.github.cptzee.lovediary.R;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<StorageReference> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        public ImageView getImg() {
            return img;
        }

        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.gallery_image);
        }

    }

    public ImageAdapter(List<StorageReference> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_image, viewGroup, false);
        return new ImageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder viewHolder, final int position) {
        localDataSet.get(position).getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(bytes -> {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    viewHolder.getImg().setImageBitmap(bitmap);
                })
                .addOnFailureListener(exception -> {
                    Log.e("GalleryHelper", "Error retrieving image: " + exception.getMessage());
                });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
