package com.example.fyp.fragment;
// Created By Junaid Nawab

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.fyp.Adapter.PopularAdapter;
import com.example.fyp.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ViewFlipper viewFlipper;
    RecyclerView popularRecyclerview;
    RecyclerView recyclerview_new;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        //Popular
       popularRecyclerview = view.findViewById(R.id.popular_recyclerview);
       ArrayList<String> popularlist = new ArrayList<>();
       popularlist.add("https://vkcadda.com/admin_assets/images/vendor/f431e4f5a4ee21dd548382e4509059ca.jpg");
       popularlist.add("https://vkcadda.com/admin_assets/images/vendor/f431e4f5a4ee21dd548382e4509059ca.jpg");
       popularlist.add("https://vkcadda.com/admin_assets/images/vendor/f431e4f5a4ee21dd548382e4509059ca.jpg");
       popularlist.add("https://vkcadda.com/admin_assets/images/vendor/f431e4f5a4ee21dd548382e4509059ca.jpg"); popularlist.add("https://vkcadda.com/admin_assets/images/vendor/f431e4f5a4ee21dd548382e4509059ca.jpg");
       popularlist.add("https://vkcadda.com/admin_assets/images/vendor/f431e4f5a4ee21dd548382e4509059ca.jpg"); popularlist.add("https://vkcadda.com/admin_assets/images/vendor/f431e4f5a4ee21dd548382e4509059ca.jpg");
       popularlist.add("https://vkcadda.com/admin_assets/images/vendor/f431e4f5a4ee21dd548382e4509059ca.jpg");
       PopularAdapter adapter = new PopularAdapter(popularlist);
       popularRecyclerview.setHasFixedSize(true);
       popularRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
       popularRecyclerview.setAdapter(adapter);
        //

        //New
        recyclerview_new = view.findViewById(R.id.recyclerview_new);
        PopularAdapter new_adapter = new PopularAdapter(popularlist);
        recyclerview_new.setHasFixedSize(true);
        recyclerview_new.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerview_new.setAdapter(new_adapter);


        //ViewFlipper
        int images[] = {R.drawable.ic_home,R.drawable.ic_home,R.drawable.ic_home};
        for (int image : images) {
            image_slider(image);
        }
        //
        return view;
    }

    private void init(View view) {
        viewFlipper = view.findViewById(R.id.home_view_pager);
    }

    public void image_slider(int images)
        {
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(images);
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.setAutoStart(true);
            viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
            viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
        }

}