package com.example.fyp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.FootActivity;
import com.example.fyp.Model.ShoesModel;
import com.example.fyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class ShoesAdapter extends RecyclerView.Adapter<ShoesAdapter.ViewHolder> {
    ArrayList<ShoesModel> shoes_list = new ArrayList();

    public ShoesAdapter(ArrayList<ShoesModel> shoes_list)
    {
        this.shoes_list = shoes_list;
    }
    @NonNull
    @Override
    public ShoesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ShoesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imagename.setText(shoes_list.get(position).getImgname());
        holder.image.setImageResource(shoes_list.get(position).getImgpath());
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
        return shoes_list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView imagename;
        public Button addcartbtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            imagename = itemView.findViewById(R.id.imagenametxt);

        }
    }
}
