package com.example.fyp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.Adapter.ShoesAdapter;
import com.example.fyp.Model.LipstickModel;
import com.example.fyp.Model.ShoesModel;

import java.util.ArrayList;

public class LipstickAdapter extends RecyclerView.Adapter<LipstickAdapter.ViewHolder>  {
    ArrayList<LipstickModel> lipstick_list = new ArrayList();
    public LipstickAdapter(ArrayList<LipstickModel> lipstick_list)
    {
        this.lipstick_list = lipstick_list;
    }
    @NonNull
    @Override
    public LipstickAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new LipstickAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LipstickAdapter.ViewHolder holder, int position) {
        holder.imagename.setText(lipstick_list.get(position).getImgname());
        holder.image.setImageResource(lipstick_list.get(position).getImgpath());
        try {
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Item clicked at = " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                    Intent shoeActivityIntent = new Intent(view.getContext(), FootActivity.class);
                    shoeActivityIntent.putExtra("clickedVal", position);
                    view.getContext().startActivity(shoeActivityIntent);
                }
            });}
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return lipstick_list.size();
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
}
