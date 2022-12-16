package com.example.myapplicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어 베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터 베이스
    private EditText Email, Pw, name, tall, weight, birth; //회원가입 입력
    private Button mBtnReg; //회원가입 버튼
    private RadioGroup radioGroup;
    private String strsex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        //라디오 그룹 설정
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);



        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("식단관리");




        Email = findViewById(R.id.et_email);
        Pw = findViewById(R.id.et_pw2);
        name = findViewById(R.id.et_Name);

        birth = findViewById(R.id.et_birth);
        weight = findViewById(R.id.et_weight);
        tall = findViewById(R.id.et_tall);
        mBtnReg = findViewById(R.id.btm_reg);

        mBtnReg.setOnClickListener(new View.OnClickListener() { //가입 버튼을 우클릭 했을때
            @Override
            public void onClick(View view) {
                //회원가입 처리
                String strEmail = Email.getText().toString(); //사용자가 입력한 값을 가져온다. toString()은 문자열로 변환
                String strPwd = Pw.getText().toString();
                String strname = name.getText().toString();
                String strtall = tall.getText().toString();
                String strweight = weight.getText().toString();
                String strbirth = birth.getText().toString();





                //파이어베이스 어스 인증 처리리
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) { //회원가능이 성공이 되었을때
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);
                            account.setName(strname);
                            account.setTall(strtall);
                            account.setWeight(strweight);
                            account.setBirth(strbirth);
                            account.setSex(strsex);


                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(RegActivity.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegActivity.this, LogimActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(RegActivity.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(i == R.id.r_btn1){
                strsex = "남성";
            }
            else if(i == R.id.r_btn2){
                strsex = "여성";
            }
        }
    };
}