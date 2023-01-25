package com.example.recyclermulti;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recyclermulti.databinding.FullImageBinding;
import com.example.recyclermulti.databinding.ImageRecyclerviewBinding;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Holder> {
    Activity activity;
    List<Uri> images;

    public ImageAdapter(Activity activity, List<Uri> images) {
        this.activity = activity;
        this.images = images;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ImageRecyclerviewBinding.inflate(LayoutInflater.from(activity)));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Glide
                .with(activity)
                .load(images.get(position))
                .into(holder.binding.ivRecycler);

        holder.binding.ivDelete.setOnClickListener(view -> {
            images.remove(position);
            notifyDataSetChanged();
        });
        holder.binding.ivRecycler.setOnClickListener(view -> {
                    Dialog dialog;
                    FullImageBinding imageBinding = FullImageBinding.inflate(LayoutInflater.from(activity));
                    dialog = new Dialog(activity, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(imageBinding.getRoot());
                    Glide .with(activity)
                            .load(images.get(holder.getAdapterPosition()))
                            .into(imageBinding.myImage);
                    dialog.setCancelable(true);
                    dialog.show();
                    imageBinding.iv1.setOnClickListener(view1 -> {
                        dialog.dismiss();

                });

        });


        //check the file extensions


    }



    @Override
    public int getItemCount() {
        return images.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageRecyclerviewBinding binding;
        public Holder(ImageRecyclerviewBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
