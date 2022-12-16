package com.example.myapplicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class setActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private EditText  name, tall, weight, birth;
    private Button mBtnReg;
    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUser;
    private UserAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("식단관리").child("UserAccount");

        name = findViewById(R.id.et_Name);
        birth = findViewById(R.id.et_birth);
        weight = findViewById(R.id.et_weight);
        tall = findViewById(R.id.et_tall);
        mBtnReg = findViewById(R.id.btm_se);
        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser();



        mBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {

                            account = snapshot1.getValue(UserAccount.class);

                            if (account.getIdToken().equals(CurrentUser.getUid())) {
                                String strname = name.getText().toString();
                                Log.d(".MainActivity", strname);
                                String strtall = tall.getText().toString();
                                String strweight = weight.getText().toString();
                                String strbirth = birth.getText().toString();

                                account.setName(strname);
                                account.setTall(strtall);
                                account.setWeight(strweight);
                                account.setBirth(strbirth);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                databaseReference.child(CurrentUser.getUid()).setValue(account);
                Toast.makeText(setActivity.this, "정보수정 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(setActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}