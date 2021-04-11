package com.example.celebrityquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // Declare Variables
    private RadioButton radioButtonLevelOne;
    private RadioButton radioButtonLevelTwo;
    private RadioButton radioButtonLevelThree;
    private RadioButton radioButton30;
    private RadioButton radioButton60;
    private RadioButton radioButton90;
    private ProgressBar progressBarDownload;
    private Button buttonStartQuiz, tmp;
    public int level;
    public int seconds;
    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define Level views
        radioButtonLevelOne = findViewById(R.id.radioButtonLevelOne);
        radioButtonLevelTwo = findViewById(R.id.radioButtonLevelTwo);
        radioButtonLevelThree = findViewById(R.id.radioButtonLevelThree);
        radioButtonLevelOne.setChecked(true);
        radioButtonLevelTwo.setChecked(false);
        radioButtonLevelThree.setChecked(false);

        // Define Time views
        radioButton30 = findViewById(R.id.radioButton30);
        radioButton60 = findViewById(R.id.radioButton60);
        radioButton90 = findViewById(R.id.radioButton90);
        radioButton30.setChecked(true);
        radioButton60.setChecked(false);
        radioButton90.setChecked(false);

        // Define Download views
        progressBarDownload = findViewById(R.id.progressBarDownload);
        progressBarDownload.setMax(100);

        // Define Update and Starting buttons
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonStartQuiz = findViewById(R.id.buttonStartQuiz);

        //나중에 삭제
        tmp = findViewById(R.id.tmp);

        buttonUpdate.setEnabled(true);
        buttonStartQuiz.setEnabled(false);
        downloadTask = null; // Always initialize task to null
    }

    private DownloadTask downloadTask;

    // Define Download methods
    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            progressBarDownload.setProgress(progress);
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            progressBarDownload.setProgress(progressBarDownload.getMax());
            buttonStartQuiz.setEnabled(true); // Enable Start button when download is successful
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            //when download failed, close the foreground notification and create a new one about the failure
            Toast.makeText(getApplicationContext(), "Download Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(getApplicationContext(), "Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    //update 버튼
        public void onButtonUpdate(View view) {
            if(downloadTask == null) {
                // Import data from internet
                String jsonUrl = "https://api.jsonbin.io/b/5e8f60bb172eb6438960f731";
                downloadTask = new DownloadTask(downloadListener, this);
                downloadTask.execute(jsonUrl);
            }
    }

    // Start QuizActivity with user settings/choices
    public void onButtonStartQuiz(View view) {
        if(radioButtonLevelOne.isChecked()) level = 1;
        if(radioButtonLevelTwo.isChecked()) level = 2;
        if(radioButtonLevelThree.isChecked()) level = 3;

        if(radioButton30.isChecked()) seconds = 30;
        if(radioButton60.isChecked()) seconds = 60;
        if(radioButton90.isChecked()) seconds = 90;

        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("level", level);
        intent.putExtra("seconds", seconds);
        startActivity(intent);
    }

    // Start QuizActivity with user settings/choices
    public void login(View view) {
        Intent intent = new Intent(this, TempActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }
}