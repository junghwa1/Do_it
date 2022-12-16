package com.example.myapplicationproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<FoodListData> foodList;
    private Context context;
    public static String day_text;

    private FirebaseAuth mFirebaseAuth; //파이어 베이스 인증
    private FirebaseDatabase database;
    private DatabaseReference databaseReference2;
    private FirebaseUser CurrentUser;
    private EditText editText;
    double eat;

    public CustomAdapter(ArrayList<FoodListData> fooList, Context context, String day_text) {
        this.foodList = fooList;
        this.context = context;
        this.day_text=day_text;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        CustomViewHolder holder=new CustomViewHolder(view);


        mFirebaseAuth = FirebaseAuth.getInstance();
        CurrentUser = mFirebaseAuth.getCurrentUser();
        FirebaseUser CurrentUser = mFirebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        Button button = view.findViewById(R.id.button2);
        databaseReference2 = database.getReference("식단관리");
        editText = view.findViewById(R.id.ed_eat);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= holder.getAdapterPosition();
                String text = "50";
                eat = 0;
                eat = Double.parseDouble(text);
                String a = foodList.get(pos).getf_once();
                a = a.replaceAll("mL","");
                a = a.replaceAll("g","");
                eat = eat/Double.valueOf(a).doubleValue();

                double ca = eat*Double.valueOf(foodList.get(pos).getf_cabo()).doubleValue();
                double ka = eat*Double.valueOf(foodList.get(pos).getf_kcal()).doubleValue();
                double pro = eat*Double.valueOf(foodList.get(pos).getf_protein()).doubleValue();
                double fa = eat*Double.valueOf(foodList.get(pos).getf_fat()).doubleValue();
                double su = eat*Double.valueOf(foodList.get(pos).getf_sugar()).doubleValue();
                double na = eat*Double.valueOf(foodList.get(pos).getf_natrium()).doubleValue();

                foodList.get(pos).setf_cabo((long)ca);
                foodList.get(pos).setf_kcal((long)ka);
                foodList.get(pos).setf_protein((long)pro);
                foodList.get(pos).setf_fat((long)fa);
                foodList.get(pos).setf_sugar((long)su);
                foodList.get(pos).setf_natrium((long)na);

                if (pos != RecyclerView.NO_POSITION){
                    Toast myToast = Toast.makeText(context,day_text, Toast.LENGTH_SHORT);
                    myToast.show();

                    databaseReference2.child("UserAccount").child(CurrentUser.getUid()).child(day_text).push().setValue(foodList.get(pos));

                }


            }
        });


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
            this.tv_kcal=itemView.findViewById(R.id.kcal);
            this.tv_protein=itemView.findViewById(R.id.protein);
            this.tv_fat=itemView.findViewById(R.id.fat);
            this.tv_cabo=itemView.findViewById(R.id.cabo);
            this.tv_sugar=itemView.findViewById(R.id.sugar);
            this.tv_natrium=itemView.findViewById(R.id.natrium);

        }
    }

}