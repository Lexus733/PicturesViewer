package com.example.dmitry.picturesviewer.presentation.generalscreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dmitry.picturesviewer.R;
import com.example.dmitry.picturesviewer.domain.Image;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.ViewHolder> {

    public interface OnItemLongClickListener {
        boolean OnItemLongClick(Image item);
    }

    public interface OnItemClickListener {
        void OnItemClick(Image item);
    }


    private LayoutInflater inflater;
    private List<Image> images;
    private OnItemClickListener listener;
    private OnItemLongClickListener listenerLong;


    PicturesAdapter(Context context, List<Image> images, OnItemClickListener listener, OnItemLongClickListener listenerLong) {
        this.images = images;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.listenerLong = listenerLong;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.picture_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicturesAdapter.ViewHolder holder, final int position) {
        final Image image = images.get(position);
        Picasso.get().load(new File(image.getPath())).placeholder(R.drawable.progress_animation).centerCrop().resize(240, 240).error(R.drawable.ic_warning_black_24dp).into(holder.imageView);
        holder.bind(image, listener, listenerLong);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;

        ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.picture_item);
        }

        private void bind(final Image image, final OnItemClickListener listener, final OnItemLongClickListener listenerLong) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(image);
                }
            });
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return listenerLong.OnItemLongClick(image);
                }
            });
        }
    }
}