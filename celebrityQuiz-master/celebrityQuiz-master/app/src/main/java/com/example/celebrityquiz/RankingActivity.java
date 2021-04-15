package com.example.celebrityquiz;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    TextView rankingText, myRank;
    List<Ranking> rankingList = new ArrayList<>();;
    RecyclerView recyclerView;

    private DatabaseReference mDatabase;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        rankingText = findViewById(R.id.ranktext);
        myRank = findViewById(R.id.myindex);
        recyclerView = findViewById(R.id.rankingRecyclerView);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase = database.getReference("ranking");
        mDatabase.orderByChild("collect").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rankingList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Ranking get = postSnapshot.getValue(Ranking.class);
                    rankingList.add(get);
                    if(user != null){
                        if(get.email.equals(user.getEmail())){
                            myRank.setText(Integer.toString(rankingList.size()));
                        }
                    }
                }
                RankingAdapter rankingAdapter = new RankingAdapter(rankingList, RankingActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(RankingActivity.this));
                recyclerView.setAdapter(rankingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
