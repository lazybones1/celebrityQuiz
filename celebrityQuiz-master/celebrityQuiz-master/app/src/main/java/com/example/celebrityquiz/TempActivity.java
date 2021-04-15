package com.example.celebrityquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TempActivity extends Activity {
    Button login, rank, show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        login = findViewById(R.id.login);
        rank = findViewById(R.id.rank);
        show = findViewById(R.id.rank_see);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            login.setText("로그인");
        }
        else{
            login.setText("로그아웃");
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rank();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });
    }

    private void login(){
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent intent = new Intent(TempActivity.this, SignInActivity.class);
            startActivity(intent);
        }
        else{
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(TempActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void rank(){
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    private void show(){
        Intent intent = new Intent(this, RankingActivity.class);
        startActivity(intent);
    }

}
