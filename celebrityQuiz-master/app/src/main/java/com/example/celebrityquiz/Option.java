package com.example.celebrityquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Option extends AppCompatActivity {

    private Button backBtn, loginBtn;
    private SwitchCompat switchCompat_bgm;
    private SharedPreferences.Editor editor_bgm;
    private Intent OPtoMA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        switchCompat_bgm = findViewById(R.id.switchButton_bgm);
        OPtoMA = new Intent(Option.this, MainActivity.class);
        Intent i = getIntent();

        //bgm
        switchCompat_bgm.setChecked(i.getBooleanExtra("BGMisChecked", false));
        SharedPreferences sharedPreferences = getSharedPreferences("save_bgm", MODE_PRIVATE);
        editor_bgm = getSharedPreferences("save_bgm",  MODE_PRIVATE).edit();
        switchCompat_bgm.setChecked(sharedPreferences.getBoolean("value", false));
        switchCompat_bgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchCompat_bgm.isChecked()){
                    editor_bgm.putBoolean("value", true);
                    editor_bgm.apply();
                    switchCompat_bgm.setChecked(true);
                    startService(new Intent(getApplicationContext(), BgmService.class));
                    OPtoMA.putExtra("BGMisChecked", true);
                }
                else {
                    editor_bgm.putBoolean("value", false);
                    editor_bgm.apply();
                    switchCompat_bgm.setChecked(false);
                    stopService(new Intent(getApplicationContext(), BgmService.class));
                    OPtoMA.putExtra("BGMisChecked", false);
                }
            }
        });

        loginBtn = findViewById(R.id.button_option_login);
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

        backBtn = findViewById(R.id.button_optionpre);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(OPtoMA);
            }
        });
    }

    private void login(){
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent intent = new Intent(Option.this, SignInActivity.class);
            startActivity(intent);
        }
        else{
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Option.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}