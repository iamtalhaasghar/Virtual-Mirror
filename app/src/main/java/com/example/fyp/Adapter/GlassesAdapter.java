package com.example.fyp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.AugmentedActivity;
import com.example.fyp.Model.GlassesModel;
import com.example.fyp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GlassesAdapter extends RecyclerView.Adapter<GlassesAdapter.ViewHolder> {
    ArrayList<GlassesModel> glasses_list = new ArrayList();
    public GlassesAdapter(ArrayList<GlassesModel> popular_list)
    {
        this.glasses_list = popular_list;
    }
    @NonNull
    @Override
    public GlassesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new GlassesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GlassesAdapter.ViewHolder holder, int position) {
        holder.imagename.setText(glasses_list.get(position).getImgname());
        holder.image.setImageResource(glasses_list.get(position).getImgpath());
        try {
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Item clicked at = " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                    Intent shoeActivityIntent = new Intent(view.getContext(), AugmentedActivity.class);
                    shoeActivityIntent.putExtra("clickedVal", position);
                    view.getContext().startActivity(shoeActivityIntent);
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
        return glasses_list.size();
    }
}
