package com.example.celebrityquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RankingHome extends AppCompatActivity {

    private Button showRankBtn, startRankBtn, GTHbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_home);

        showRankBtn = findViewById(R.id.button_ranking_view);
        showRankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRank();
            }
        });

        startRankBtn = findViewById(R.id.button_ranking_start);
        startRankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRank();
            }
        });

        GTHbtn = findViewById(R.id.button_ranking_to_home);
        GTHbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankingHome.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startRank(){
        Intent intent = new Intent(RankingHome.this, QuizActivity.class);
        startActivity(intent);
    }

    private void showRank(){
        Intent intent = new Intent(RankingHome.this, RankingActivity.class);
        startActivity(intent);
    }
}


