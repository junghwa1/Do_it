package com.example.myapplicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogimActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어 베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터 베이스
    private EditText Email, Pw; //로그인인 입력


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logim);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("식단관리");

        Email = findViewById(R.id.et_email);
        Pw = findViewById(R.id.et_pw);

        Button btm_login = findViewById(R.id.btm_login);
        btm_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 요청
                String strEmail = Email.getText().toString(); //사용자가 입력한 값을 가져온다. toString()은 문자열로 변환
                String strPwd = Pw.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(LogimActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) { //회원가능이 성공이 되었을때
                            Intent intent = new Intent(LogimActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(LogimActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        Button btm_reg = findViewById(R.id.btm_reg);
        btm_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //회원가입 화면으로 이동
                Intent intent = new Intent(LogimActivity.this,RegActivity.class);
                startActivity(intent);
            }
        });
    }
}