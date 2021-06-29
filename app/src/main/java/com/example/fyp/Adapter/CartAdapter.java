package com.example.fyp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.Model.GlassesModel;
import com.example.fyp.R;
import com.example.fyp.fragment.CartFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context ctx;
    LayoutInflater inflater;
    private ArrayList<GlassesModel> list_of_items = new ArrayList<>();
    DatabaseReference reference;
    String userID;
    FirebaseAuth firebaseAuth;
    int item_position;

    public CartAdapter(Context ctx, ArrayList<GlassesModel> list_of_items){
        this.list_of_items = list_of_items;
        this.ctx = ctx;
        this.inflater = LayoutInflater.from(ctx);


    }
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {

        holder.imageView.setImageResource(Integer.parseInt(list_of_items.get(position).getImgpath()));
        holder.title_txt.setText(list_of_items.get(position).getImgname());
        holder.price_txt.setText("Rs"+list_of_items.get(position).getPrice());
        holder.quantity_txt.setText(list_of_items.get(position).getQuantity());
        holder.plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(list_of_items.get(position).getQuantity());
                quantity = quantity+1;
                if(quantity<=5) {
                    list_of_items.get(position).setQuantity(String.valueOf(quantity));
                    String prices = CartFragment.total_txt.getText().toString();
                    int price = Integer.parseInt(prices.substring(prices.lastIndexOf("Rs")+2));
                    price = price + Integer.parseInt(list_of_items.get(position).getPrice());
                    CartFragment.total_txt.setText("Rs"+price);
                    plusquantity(position);
                    notifyDataSetChanged();
                }
            }
        });
        holder.item_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                removeSingleItemDialog(position);
                return false;
            }
        });
        holder.minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(list_of_items.get(position).getQuantity());
                quantity = quantity - 1;
                if(quantity!=0) {
                    list_of_items.get(position).setQuantity(String.valueOf(quantity));
                    String prices = CartFragment.total_txt.getText().toString();
                    int price = Integer.parseInt(prices.substring(prices.lastIndexOf("Rs")+2));
                    price = price - Integer.parseInt(list_of_items.get(position).getPrice());
                    CartFragment.total_txt.setText("Rs"+price);
                    minusquantiy(position);
                    notifyDataSetChanged();
                }
            }
        });
    }

    private void minusquantiy(int position) {
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Cart");
        ref.orderByChild("id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        Log.d("test123", datas.getValue().toString());
                        String key = datas.getKey();
                        String id = datas.child("id").getValue().toString();
                        int quantity = Integer.valueOf(datas.child("quantity").getValue().toString());
                        quantity = quantity - 1;
                        Log.d("test123", "id" +list_of_items.get(position).getId());
                        if (id.equals(list_of_items.get(position).getId())) {
                            ref.child(key).child("quantity").setValue(String.valueOf(quantity));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });

    }

    private void plusquantity(int position) {
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Cart");
        ref.orderByChild("id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        Log.d("test123", datas.getValue().toString());
                        String key = datas.getKey();
                        String id = datas.child("id").getValue().toString();
                        int quantity = Integer.valueOf(datas.child("quantity").getValue().toString());
                        quantity = quantity + 1;
                        Log.d("test123", "id" +list_of_items.get(position).getId());
                        if (id.equals(list_of_items.get(position).getId())) {
                            ref.child(key).child("quantity").setValue(String.valueOf(quantity));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });

    }

    private void removeSingleItemDialog(int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
        dialog.setTitle("Remove This Item");
        item_position = position;
        dialog.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebaseAuth = FirebaseAuth.getInstance();
                userID = firebaseAuth.getUid();
                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Cart");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            if(snapshot.hasChild("id")) {
                                if (snapshot.child("id").getValue(String.class).equals(list_of_items.get(item_position).getId())) {
                                    removeitem(snapshot.getKey());
                                    Toast.makeText(ctx, "Item Removed Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(ctx,"No item in Cart",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ctx,"Item Not Removed Successfully",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void removeitem(String key) {
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        db.child("Cart").child(key).removeValue();
        list_of_items.remove(item_position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list_of_items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title_txt;
        TextView price_txt;
        ImageView plus_btn;
        ImageView minus_btn;
        TextView quantity_txt;
        LinearLayout item_layout;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_image);
            title_txt = itemView.findViewById(R.id.title_txt);
            price_txt = itemView.findViewById(R.id.price_text);
            plus_btn = itemView.findViewById(R.id.cart_plus_img);
            minus_btn = itemView.findViewById(R.id.cart_minus_img);
            quantity_txt = itemView.findViewById(R.id.cart_product_quantity_tv);
            item_layout = itemView.findViewById(R.id.cart_list_layout);
        }


    }
}
