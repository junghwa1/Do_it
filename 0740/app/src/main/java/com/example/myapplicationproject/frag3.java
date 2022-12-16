package com.example.myapplicationproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class frag3 extends Fragment {

    private View view;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUser;

    private TextView name, sex, tall, age, weight, bmi;
    private UserAccount account;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.frag3, container, false);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("식단관리").child("UserAccount");

        name = view.findViewById(R.id.text_name);
        sex = view.findViewById(R.id.text_sex);
        tall = view.findViewById(R.id.text_tall);
        age = view.findViewById(R.id.text_age);
        weight = view.findViewById(R.id.text_weight);
        bmi = view.findViewById(R.id.text_bmi);
        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser();




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {

                    account = snapshot1.getValue(UserAccount.class);

                    Log.d(".MainActivity", CurrentUser.getUid());
                    if (account.getIdToken().equals(CurrentUser.getUid())) {
                        name.setText(account.getName());
                        sex.setText(account.getSex());
                        tall.setText(account.getTall());
                        int year=2022-Integer.valueOf(account.getBirth())/10000+1;
                        age.setText(String.valueOf(year));
                        weight.setText(account.getWeight());
                        double kg=Double.valueOf(account.getWeight());
                        double cm=Double.valueOf(account.getTall());
                        double m=cm/100;
                        bmi.setText(String.format("%.2f",kg/(m*m)));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
