package com.example.fyp.fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Adapter.CartAdapter;
import com.example.fyp.JazzcashPayment;
import com.example.fyp.LoginActivity;
import com.example.fyp.Model.GlassesModel;
import com.example.fyp.R;
import com.example.fyp.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CartFragment extends Fragment {
    private View viewCartItems;
    private RecyclerView list_view;
    private ArrayList<GlassesModel> item_list = new ArrayList<>();
    private DatabaseReference listReference;
    private String userID;
    private FirebaseAuth firebaseAuth;
    private CartAdapter cartAdapter;
    GlassesModel item_model;
    int total_price_value = 0;
    public static TextView total_txt;
    Button place_order_btn;
    Button clear_cart_btn;
    RadioGroup payment_radiogroup;
    String selectedPaymentgateway = "Cash On Delivery";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getUid();
        total_txt = view.findViewById(R.id.total_txt);
        place_order_btn = view.findViewById(R.id.place_order_btn);
        clear_cart_btn = view.findViewById(R.id.clear_cart_btn);
        payment_radiogroup = view.findViewById(R.id.payment_gateway_radiobt);
        Log.d("test123", userID);
        viewCartItems = view;
        listReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Cart");
        InitializeList();
        displayList();
        payment_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(radioButtonID);
                int idx = radioGroup.indexOfChild(radioButton);
                RadioButton r = (RadioButton) radioGroup.getChildAt(idx);
                selectedPaymentgateway = r.getText().toString();

            }
        });

        place_order_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(total_txt.getText().toString().equals("Rs0"))
                {
                    Toast.makeText(getContext(),"Cart is empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (selectedPaymentgateway.equals("JazzCash")) {
                        Intent intent = new Intent(getContext(), JazzcashPayment.class);
                        intent.putExtra("price", "12.00");
                        startActivity(intent);
                    } else {
                        shownotification();
                        UploadOrderData();
                    }
                }
            }
        });
        clear_cart_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(total_txt.getText().toString().equals("Rs0"))
                {
                    Toast.makeText(getContext(),"Cart Already Cleared",Toast.LENGTH_SHORT).show();
                }
                else {
                    clearCart();
                    Toast.makeText(getContext(), "Cart Cleared", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void shownotification() {
        // Sets an ID for the notification, so it can be updated.
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "name", importance);
        Notification notification = new Notification.Builder(getContext())
                .setContentTitle("Congratualtions!")
                .setContentText("Your Order has been placed!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setChannelId(CHANNEL_ID)
                .build();
        NotificationManager mNotificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(mChannel);
        mNotificationManager.notify(notifyID , notification);
    }

    private void UploadOrderData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userID);
        map.put("items",item_list);
        map.put("paymentmethod",selectedPaymentgateway);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Orders").push();
        reference.setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(getContext(), "Order Placed Successfully",
                                    Toast.LENGTH_SHORT).show();
                            clearCart();

                        } else {
                            Toast.makeText(getContext(), "Error: " +
                                            task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void clearCart()
    {
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        total_price_value = 0;
        db.child("Cart").removeValue();
        item_list.clear();
        total_txt.setText("Rs0");
        cartAdapter.notifyDataSetChanged();
    }

    private void displayList() {
        listReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    item_model = postSnapshot.getValue(GlassesModel.class);
                    item_list.add(item_model);
                    total_price_value = total_price_value + Integer.parseInt(item_model.getPrice())* Integer.parseInt(item_model.getQuantity());
                    cartAdapter.notifyDataSetChanged();
                }
                total_txt.setText("Rs"+String.valueOf(total_price_value));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void InitializeList() {
        list_view =  viewCartItems.findViewById(R.id.cart_recyclerview);
        cartAdapter = new CartAdapter(getContext(),item_list);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list_view.setLayoutManager(llm);
        list_view.setAdapter(cartAdapter);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getContext(), "Payment Success", Toast.LENGTH_SHORT).show();
        // Check that it is the SecondActivity with an OK result
        if (requestCode == 0 && resultCode == getActivity().RESULT_OK) {
            // Get String data from Intent
            String ResponseCode = data.getStringExtra("pp_ResponseCode");
            System.out.println("DateFn: ResponseCode:" + ResponseCode);
            if(ResponseCode.equals("000")) {
                Toast.makeText(getContext(), "Payment Success", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getContext(), "Payment Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}