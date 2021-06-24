package com.example.fyp.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.Adapter.PopularAdapter;
import com.example.fyp.PopularModel;
import com.example.fyp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ViewHolder> {
    ArrayList<PopularModel> new_list = new ArrayList();
    public NewAdapter(ArrayList<PopularModel> new_list)
    {
        this.new_list = new_list;
    }
    @NonNull
    @Override
    public NewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_popular, parent, false);
        return new NewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imagepath = new_list.get(position).getImgpath();
        holder.imagename.setText(new_list.get(position).getImgname());
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


    @Override
    public int getItemCount() {
        return new_list.size();
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
