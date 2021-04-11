package com.example.celebrityquiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//문제 정답 표기 화면
public class SolutionActivity extends AppCompatActivity{

    private DatabaseReference mDatabase;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        // Define Navigation // 메뉴바 뒤로가기 화살표 설정
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Results");
        }

        // Interface instance to get values from QuizActivity // QuizActivity로 부터 받은 intent
        int scoreValue = getIntent().getIntExtra("score", 0);

        // 추가 시작
        int level = getIntent().getIntExtra("level", 0);
        int times = getIntent().getIntExtra("time", 0);

        if(level == 0){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null){
                String email = user.getEmail();
                writeNewUser(email, scoreValue, times);
            }
        }
        // 추가 종료

        List<Quiz> quizList = (List<Quiz>) getIntent().getSerializableExtra("quizList");

        // Set view and display scoreValue
        TextView scoreView = findViewById(R.id.scoreTextView);
        scoreView.setText(String.valueOf(scoreValue));

        // Set score out-of view
        TextView scoreTotalView = findViewById(R.id.scoreTotalTextView);
        scoreTotalView.setText(String.valueOf(5));

        // See function
        displayWellDone(scoreValue);

        // RecycleView definitions //리사이클러뷰 : 어댑터가 필요함, 리스트 재활용 기능
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        SolutionAdapter solutionAdapter = new SolutionAdapter(quizList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(solutionAdapter);
    }

    // Function to display well done image if user gets all correct | also settings for total value
    public void displayWellDone(int score) {

        // Set view for well done image
        ImageView imageView = findViewById(R.id.wellDoneImage);
        imageView.setVisibility(View.INVISIBLE); // set image invisible

        // display well done image if user gets all correct
        if (score == 5) imageView.setVisibility(View.VISIBLE);
    }

    //추가 시작
    public void writeNewUser(String email, int collect, int time) {
        Ranking ranking = new Ranking(email, collect, time);
        String user = email.substring(0, email.indexOf('@'));
        mDatabase.child("ranking").child(user).setValue(ranking);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    //추가 종료
}
