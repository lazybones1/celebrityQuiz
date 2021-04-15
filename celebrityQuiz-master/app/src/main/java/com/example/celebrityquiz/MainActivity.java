package com.example.celebrityquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button startBtn, optionBtn, loginBtn;
    private DrawerLayout drawerLayout;
    private View drawerView;
    private SwitchCompat switchCompat_bgm;
    private SharedPreferences.Editor editor;
    private Intent MAtoOP;

    //추가---
    private long backKeyPressedTime = 0;

    //---
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.hamburger);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //bgm
        switchCompat_bgm = findViewById(R.id.switchButton_bgm);
        MAtoOP = new Intent(MainActivity.this, Option.class);

        //navigation drawer
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);

        //bgm
        Intent i = getIntent();
        switchCompat_bgm.setChecked(i.getBooleanExtra("BGMisChecked", false));
        SharedPreferences sharedPreferences = getSharedPreferences("save_bgm", MODE_PRIVATE);
        editor = getSharedPreferences("save_bgm",  MODE_PRIVATE).edit();
        switchCompat_bgm.setChecked(sharedPreferences.getBoolean("value", false));
        switchCompat_bgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchCompat_bgm.isChecked()){
                    startService(new Intent(getApplicationContext(), BgmService.class));
                    editor.putBoolean("value", true);
                    editor.apply();
                    switchCompat_bgm.setChecked(true);
                    MAtoOP.putExtra("BGMisChecked", true);
                }
                else {
                    stopService(new Intent(getApplicationContext(), BgmService.class));
                    editor.putBoolean("value", false);
                    editor.apply();
                    switchCompat_bgm.setChecked(false);
                    MAtoOP.putExtra("BGMisChecked", false);
                }
            }
        });

        //button to category
        startBtn = findViewById(R.id.button_start);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Category.class);
                startActivity(intent);
            }
        });


        //button to ranking
        Button rankingBtn = findViewById(R.id.button_ranking);
        rankingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RankingHome.class);
                startActivity(intent);
            }
        });


        //button to option
        optionBtn = findViewById(R.id.button_option);
        optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MAtoOP);
            }
        });

        loginBtn = findViewById(R.id.button_login);
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            loginBtn.setText("로그인");
        }
        else {
            loginBtn.setText("로그아웃");
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        //navigation drawer
        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void login(){
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        }
        else{
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }


    @Override  //exit ment toast
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast toast = Toast.makeText(this,
                    "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }

    @Override //hamburger tab click -> navigation drawer
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //navigation drawer
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        }
        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
        }
        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
        }
        @Override
        public void onDrawerStateChanged(int newState) {
        }
    };
}