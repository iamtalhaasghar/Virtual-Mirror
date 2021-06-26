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
import com.example.fyp.LipstickAdapter;
import com.example.fyp.Model.GlassesModel;
import com.example.fyp.Model.LipstickModel;
import com.example.fyp.Model.ShoesModel;
import com.example.fyp.R;
import com.example.fyp.RegisterActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ViewFlipper viewFlipper;
    RecyclerView glassesRecyclerview;
    RecyclerView recyclerview_shoes;
    RecyclerView recyclerview_lipstick;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);


        //Glasses
       glassesRecyclerview = view.findViewById(R.id.glasses_recyclerview);
       ArrayList<GlassesModel> popularlist = new ArrayList<>();
       GlassesModel model = new GlassesModel("1",R.drawable.glasses1,"Eye Glasses");
       popularlist.add(model);
        GlassesModel model1 = new GlassesModel("2",R.drawable.glasses2,"Glasses");
        popularlist.add(model1);
        GlassesModel model2 = new GlassesModel("3",R.drawable.glasses3,"Air Glasses");
        popularlist.add(model2);
        GlassesModel mode2 = new GlassesModel("4",R.drawable.glasses4,"Addidas Glasses");
        popularlist.add(mode2);

       GlassesAdapter adapter = new GlassesAdapter(popularlist);
       glassesRecyclerview.setHasFixedSize(true);
       glassesRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
       glassesRecyclerview.setAdapter(adapter);
        //

        //Shoes
        recyclerview_shoes = view.findViewById(R.id.recyclerview_shoes);
        ArrayList<ShoesModel> shoeslist = new ArrayList<>();
        ShoesModel shoes1 = new ShoesModel("1",R.drawable.shoes1,"Nike Shoes");
        shoeslist.add(shoes1);
        ShoesModel shoes2 = new ShoesModel("2",R.drawable.shoes2,"Addidas Shoes");
        shoeslist.add(shoes2);
        ShoesModel shoes3 = new ShoesModel("3",R.drawable.shoes3,"Bata Shoes");
        shoeslist.add(shoes3);
        ShoesAdapter shoes_adapter = new ShoesAdapter(shoeslist);
        recyclerview_shoes.setHasFixedSize(true);
        recyclerview_shoes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerview_shoes.setAdapter(shoes_adapter);

        //LipStick
        recyclerview_lipstick = view.findViewById(R.id.recyclerview_lipsticks);
        ArrayList<LipstickModel> lipsticklist = new ArrayList<>();
        LipstickModel lipstick1 = new LipstickModel("1",R.drawable.makeup,"Premium Stick");
        lipsticklist.add(lipstick1);
        LipstickModel lipstick2 = new LipstickModel("2",R.drawable.makeup2,"New Lipstick");
        lipsticklist.add(lipstick2);
        LipstickAdapter lipstick_adapter = new LipstickAdapter(lipsticklist);
        recyclerview_lipstick.setHasFixedSize(true);
        recyclerview_lipstick.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerview_lipstick.setAdapter(lipstick_adapter);


        //ViewFlipper
        int images[] = {R.drawable.banner1,R.drawable.banner2,R.drawable.banner3};
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