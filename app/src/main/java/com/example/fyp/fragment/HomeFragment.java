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

import com.example.fyp.Adapter.ShoesAdapter;
import com.example.fyp.Adapter.GlassesAdapter;
import com.example.fyp.Model.GlassesModel;
import com.example.fyp.Model.ShoesModel;
import com.example.fyp.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ViewFlipper viewFlipper;
    RecyclerView glassesRecyclerview;
    RecyclerView recyclerview_shoes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        //Glasses
       glassesRecyclerview = view.findViewById(R.id.glasses_recyclerview);
       ArrayList<GlassesModel> popularlist = new ArrayList<>();
       GlassesModel model = new GlassesModel("1","https://media.self.com/photos/5dd44cf5d0525b0009f5edf6/4:3/w_2560%2Cc_limit/AdobeStock_208000726.jpeg","Eye Glasses");
       popularlist.add(model);
        GlassesModel model1 = new GlassesModel("2","https://media.self.com/photos/5dd44cf5d0525b0009f5edf6/4:3/w_2560%2Cc_limit/AdobeStock_208000726.jpeg","Eye Glasses");
        popularlist.add(model1);
        GlassesModel model2 = new GlassesModel("3","https://media.self.com/photos/5dd44cf5d0525b0009f5edf6/4:3/w_2560%2Cc_limit/AdobeStock_208000726.jpeg","Eye Glasses");
        popularlist.add(model2);
        GlassesModel mode2 = new GlassesModel("4","https://media.self.com/photos/5dd44cf5d0525b0009f5edf6/4:3/w_2560%2Cc_limit/AdobeStock_208000726.jpeg","Eye Glasses");
        popularlist.add(mode2);

       GlassesAdapter adapter = new GlassesAdapter(popularlist);
       glassesRecyclerview.setHasFixedSize(true);
       glassesRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
       glassesRecyclerview.setAdapter(adapter);
        //

        //Shoes
        recyclerview_shoes = view.findViewById(R.id.recyclerview_new);
        ArrayList<ShoesModel> shoeslist = new ArrayList<>();
        ShoesModel shoes1 = new ShoesModel("1","https://edited.beautybay.com/wp-content/uploads/2019/10/BestPinkLipsticks_Edited_Landscape_1-1-scaled.jpg","LipSticks");
        shoeslist.add(shoes1);
        ShoesModel shoes2 = new ShoesModel("1","https://edited.beautybay.com/wp-content/uploads/2019/10/BestPinkLipsticks_Edited_Landscape_1-1-scaled.jpg","LipSticks");
        shoeslist.add(shoes2);
        ShoesModel shoes3 = new ShoesModel("1","https://edited.beautybay.com/wp-content/uploads/2019/10/BestPinkLipsticks_Edited_Landscape_1-1-scaled.jpg","LipSticks");
        shoeslist.add(shoes3);
        ShoesAdapter shoes_adapter = new ShoesAdapter(shoeslist);
        recyclerview_shoes.setHasFixedSize(true);
        recyclerview_shoes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerview_shoes.setAdapter(shoes_adapter);


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