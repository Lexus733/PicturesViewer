package com.example.dmitry.picturesviewer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Image> images;

    public PicturesAdapter(Context context, List<Image> images) {
        this.images = images;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.picture_view_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicturesAdapter.ViewHolder holder, final int position) {
        final Image image = images.get(position);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(inflater.getContext(),"Image tapped: " + images.get(position),Toast.LENGTH_SHORT).show();
                Intent  i = new Intent(inflater.getContext(),PicturesView.class);
                i.putExtra("image",image.getImage());
                inflater.getContext().startActivity(i);
            }
        });
        holder.imageView.setImageResource(image.getImage());
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
    }
}
