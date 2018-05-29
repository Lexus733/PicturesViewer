package com.example.dmitry.picturesviewer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.ViewHolder> {

    public interface OnItemLongClickListener{
        boolean OnItemLongClick(Image item);
    }

    public interface OnItemClickListener{
        void OnItemClick(Image item);
    }


    private LayoutInflater inflater;
    private List<Image> images;
    private OnItemClickListener listener;
    private OnItemLongClickListener listenerLong;


    public PicturesAdapter(Context context, List<Image> images, OnItemClickListener listener, OnItemLongClickListener listenerLong) {
        this.images = images;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.listenerLong = listenerLong;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.picture_view_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicturesAdapter.ViewHolder holder, final int position) {
        final Image image = images.get(position);
        holder.imageView.setImageBitmap(image.getImage());
        holder.bind(image,listener,listenerLong);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ImageView imageView;

        ViewHolder(View v){
            super(v);
            imageView = (ImageView) v.findViewById(R.id.picture_item);
        }

        public void bind(final Image image,final OnItemClickListener listener, final OnItemLongClickListener listenerLong){
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
