package com.example.fyp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fyp.Model.GlassesModel;
import com.example.fyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    TextView user_email;
    TextView user_address;
    TextView user_name;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        user_email = view.findViewById(R.id.profile_mail_edtxt);
        user_address = view.findViewById(R.id.profile_address_edtxt);
        user_name = view.findViewById(R.id.profile_name_edtxt);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        user_email.setText(user.getEmail());
        getdata();
        return view;
    }

    private void getdata() {

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        Log.d("test123",user.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user_address.setText(dataSnapshot.child("address").getValue(String.class));
                    user_name.setText(dataSnapshot.child("name").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("test123",error.getMessage());
            }

        });
    }
}