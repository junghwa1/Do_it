package com.example.myapplicationproject;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;

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


public class frag2 extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<FoodListData> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUser;
    private int i = 0;
    private FoodListData foodlist;
    double sum_kcal=0.0;
    double sum_protein=0.0;
    double sum_fat=0.0;
    double sum_cabo=0.0;
    double sum_sugar=0.0;
    double sum_natrium=0.0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View V =inflater.inflate(R.layout.frag2, container, false);

        recyclerView = V.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser();
        databaseReference = database.getReference("식단관리").child("UserAccount").child(CurrentUser.getUid());


        TextView text_kcal = V.findViewById(R.id.kcal);
        TextView text_protein = V.findViewById(R.id.protein);
        TextView text_fat = V.findViewById(R.id.fat);
        TextView text_cabo = V.findViewById(R.id.cabo);
        TextView text_sugar = V.findViewById(R.id.sugar);
        TextView text_natrium = V.findViewById(R.id.natrium);




        CalendarView calendarView = V.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView View, int year, int month, int day) {

                String text = String.valueOf(year*10000+(month+1)*100+day);
                Log.d(".MainActivity", text);

                databaseReference.child(text).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arrayList.clear();
                        sum_kcal=0.0;
                        sum_protein=0.0;
                        sum_fat=0.0;
                        sum_cabo=0.0;
                        sum_sugar=0.0;
                        sum_natrium=0.0;
                        for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {

                            foodlist = snapshot1.getValue(FoodListData.class);

                            Log.d(".MainActivity", foodlist.getf_name());
                            // 검색된 데이터를 리스트에 추가한다.
                            arrayList.add(foodlist);
                            i++;
                            sum_kcal+=foodlist.getf_kcal();
                            sum_protein+=foodlist.getf_protein();
                            sum_fat+=foodlist.getf_fat();
                            sum_cabo+=foodlist.getf_cabo();
                            sum_sugar+=foodlist.getf_sugar();
                            sum_natrium+=foodlist.getf_natrium();

                            if (i == 20) {
                                i = 0;
                                break;
                            }
                        }
                        text_kcal.setText(String.valueOf(sum_kcal));
                        text_protein.setText(String.valueOf(sum_protein));
                        text_fat.setText(String.valueOf(sum_fat));
                        text_cabo.setText(String.valueOf(sum_cabo));
                        text_sugar.setText(String.valueOf(sum_sugar));
                        text_natrium.setText(String.valueOf(sum_natrium));

                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        adapter = new CustomAdapter_c(arrayList, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);

        return V;
    }







}
