package com.example.celebrityquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Category extends AppCompatActivity {

    private Button  GTHbtn, Singer, MovieStar, Influencer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Singer = findViewById(R.id.button_singer);
        MovieStar = findViewById(R.id.button_moviestar);
        Influencer = findViewById(R.id.button_influencer);
        GTHbtn = findViewById(R.id.button_category_to_home);

        Singer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Category.this, QuizHome.class);
                intent.putExtra("json","https://api.jsonbin.io/b/607538660ed6f819beaa8970/3");
                startActivity(intent);
            }
        });

        MovieStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Category.this, QuizHome.class);
                intent.putExtra("json","https://api.jsonbin.io/b/6075aaf25b165e19f61f22b6");
                startActivity(intent);
            }
        });

        Influencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Category.this, QuizHome.class);
                intent.putExtra("json","https://api.jsonbin.io/b/6075ab1a5b165e19f61f22ea");
                startActivity(intent);
            }
        });

        GTHbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}