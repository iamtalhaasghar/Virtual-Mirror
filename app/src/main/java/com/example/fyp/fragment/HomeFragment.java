package com.example.fyp.fragment;
// Created By Junaid Nawab

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.fyp.Adapter.PopularAdapter;
import com.example.fyp.PopularModel;
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
       ArrayList<PopularModel> popularlist = new ArrayList<>();
       PopularModel model = new PopularModel("1","https://media.self.com/photos/5dd44cf5d0525b0009f5edf6/4:3/w_2560%2Cc_limit/AdobeStock_208000726.jpeg","Eye Glasses");
       popularlist.add(model);
       PopularAdapter adapter = new PopularAdapter(popularlist);
       popularRecyclerview.setHasFixedSize(true);
       popularRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
       popularRecyclerview.setAdapter(adapter);
        //

        //New
        recyclerview_new = view.findViewById(R.id.recyclerview_new);
        ArrayList<PopularModel> newlist = new ArrayList<>();
        PopularModel new_model = new PopularModel("2","https://edited.beautybay.com/wp-content/uploads/2019/10/BestPinkLipsticks_Edited_Landscape_1-1-scaled.jpg","LipSticks");
        newlist.add(new_model);
        NewAdapter new_adapter = new NewAdapter(newlist);
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