package com.example.fyp.Adapter;

import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.PopularModel;
import com.example.fyp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    ArrayList<PopularModel> popular_list = new ArrayList();
    public PopularAdapter(ArrayList<PopularModel> popular_list)
    {
        this.popular_list = popular_list;
    }
    @NonNull
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_popular, parent, false);
        return new PopularAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
        String imagepath = popular_list.get(position).getImgpath();
        holder.imagename.setText(popular_list.get(position).getImgname());
        Picasso.get().load(imagepath).placeholder(R.drawable.ic_home).into(holder.image);
        try {
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });}
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView imagename;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            imagename = itemView.findViewById(R.id.imagenametxt);
        }
    }
    @Override
    public int getItemCount() {
        return popular_list.size();
    }
}
