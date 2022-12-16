package com.example.myapplicationproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomAdapter_c extends RecyclerView.Adapter<CustomAdapter_c.CustomViewHolder> {
    private ArrayList<FoodListData> foodList;
    private Context context;
    public static String day_text;

    private FirebaseAuth mFirebaseAuth; //파이어 베이스 인증
    private FirebaseDatabase database;
    private DatabaseReference databaseReference2;
    private FirebaseUser CurrentUser;

    public CustomAdapter_c(ArrayList<FoodListData> fooList, Context context) {
        this.foodList = fooList;
        this.context = context;
        this.day_text=day_text;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_c,parent,false);
        CustomViewHolder holder=new CustomViewHolder(view);


        mFirebaseAuth = FirebaseAuth.getInstance();
        CurrentUser = mFirebaseAuth.getCurrentUser();
        FirebaseUser CurrentUser = mFirebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference2 = database.getReference("식단관리");





        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tv_name.setText(foodList.get(position).getf_name());
        holder.tv_once.setText(foodList.get(position).getf_once());
        holder.tv_kcal.setText(String.valueOf(foodList.get(position).getf_kcal()));
        holder.tv_protein.setText(String.valueOf(foodList.get(position).getf_protein()));
        holder.tv_fat.setText(String.valueOf(foodList.get(position).getf_fat()));
        holder.tv_cabo.setText(String.valueOf(foodList.get(position).getf_cabo()));
        holder.tv_sugar.setText(String.valueOf(foodList.get(position).getf_sugar()));
        holder.tv_natrium.setText(String.valueOf(foodList.get(position).getf_natrium()));

    }

    @Override
    public int getItemCount() {
        return (foodList!=null?foodList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_once;
        TextView tv_kcal;
        TextView tv_protein;
        TextView tv_fat;
        TextView tv_cabo;
        TextView tv_sugar;
        TextView tv_natrium;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_name=itemView.findViewById(R.id.tv_name);
            this.tv_once=itemView.findViewById(R.id.tv_once);
            this.tv_kcal=itemView.findViewById(R.id.tv_kcal);
            this.tv_protein=itemView.findViewById(R.id.tv_protein);
            this.tv_fat=itemView.findViewById(R.id.tv_fat);
            this.tv_cabo=itemView.findViewById(R.id.tv_cabo);
            this.tv_sugar=itemView.findViewById(R.id.tv_sugar);
            this.tv_natrium=itemView.findViewById(R.id.tv_natrium);

        }
    }

}