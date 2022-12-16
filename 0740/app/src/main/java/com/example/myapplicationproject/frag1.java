package com.example.myapplicationproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class frag1 extends Fragment {


    private View view;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<FoodListData> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUser;
    private EditText editText;
    private EditText editday;
    private int i = 0;
    private FoodListData foodlist;
    public String text2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag1, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        editText = view.findViewById(R.id.search_btn);
        editday = view.findViewById(R.id.setday);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("식단관리").child("food");
        databaseReference2 = database.getReference("식단관리").child("UserAccount");
        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser();
        text2="0";

        Button btn1 = view.findViewById(R.id.button1);
        Button btn2 = view.findViewById(R.id.button2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arrayList.clear();
                        for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {

                            foodlist = snapshot1.getValue(FoodListData.class);
                            Log.d(".MainActivity", foodlist.getf_name());
                            if (foodlist.getf_name().toLowerCase().contains(text)) {
                                // 검색된 데이터를 리스트에 추가한다.
                                arrayList.add(foodlist);
                                i++;
                            }

                            if (i == 20) {
                                i = 0;
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text2 = editday.getText().toString();
                Toast myToast = Toast.makeText(getActivity().getApplicationContext(),text2, Toast.LENGTH_SHORT);
                myToast.show();
                adapter = new CustomAdapter(arrayList, getActivity().getApplicationContext(),text2);
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }
}
