package com.example.fyp.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.AugmentedActivity;
import com.example.fyp.LipstickAugmentedActivity;
import com.example.fyp.Model.GlassesModel;
import com.example.fyp.Model.LipstickModel;
import com.example.fyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LipstickAdapter extends RecyclerView.Adapter<LipstickAdapter.ViewHolder>  {
    ArrayList<LipstickModel> lipstick_list = new ArrayList();
    FirebaseAuth firebaseAuth;
    String userID;
    Boolean new_item = true;
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
        holder.list_price_txt.setText("Rs"+lipstick_list.get(position).getPrice());
        holder.image.setImageResource(Integer.parseInt(lipstick_list.get(position).getImgpath()));
        try {
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), LipstickAugmentedActivity.class);
                    intent.putExtra("clickedVal", position);
                    view.getContext().startActivity(intent);
                }
            });}
        catch (Exception e) {
            e.printStackTrace();
        }
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getUid();
        holder.addcartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Added to Cart",Toast.LENGTH_SHORT).show();
                addToCart(position);
            }
        });
    }
    private void addToCart(int position) {
        new_item = true;
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Cart");
        ref.orderByChild("id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        Log.d("test123",datas.getValue().toString());
                        String key=datas.getKey();
                        String id=datas.child("id").getValue().toString();
                        int quantity = Integer.valueOf(datas.child("quantity").getValue().toString());
                        quantity = quantity+1;
                        Log.d("test123","id"+lipstick_list.get(position).getId());
                        if(id.equals(lipstick_list.get(position).getId()))
                        {
                            ref.child(key).child("quantity").setValue(String.valueOf(quantity));
                            new_item = false;
                        }
                    }
                    if(new_item)
                    {
                        String name = lipstick_list.get(position).getImgname();
                        String price = lipstick_list.get(position).getPrice();
                        String imagepath = lipstick_list.get(position).getImgpath();
                        String item_id = lipstick_list.get(position).getId();
                        int item_quantity = Integer.parseInt(lipstick_list.get(position).getQuantity());
                        item_quantity = item_quantity + 1;
                        GlassesModel model = new GlassesModel(item_id, imagepath, name, price, String.valueOf(item_quantity));
                        ref.push().setValue(model);
                    }
                }
                else if(!dataSnapshot.exists()&&new_item)
                {
                    String name = lipstick_list.get(position).getImgname();
                    String price = lipstick_list.get(position).getPrice();
                    String imagepath = lipstick_list.get(position).getImgpath();
                    String item_id = lipstick_list.get(position).getId();
                    int item_quantity = Integer.parseInt(lipstick_list.get(position).getQuantity());
                    item_quantity = item_quantity + 1;
                    GlassesModel model = new GlassesModel(item_id, imagepath, name, price, String.valueOf(item_quantity));
                    ref.push().setValue(model);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test123",error.getMessage());
            }

        });

    }

    @Override
    public int getItemCount() {
        return lipstick_list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView imagename;
        public Button addcartbtn;
        TextView list_price_txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            imagename = itemView.findViewById(R.id.imagenametxt);
            addcartbtn = itemView.findViewById(R.id.addToCartBtn);
            list_price_txt = itemView.findViewById(R.id.price_item_txt);
        }
    }
}
