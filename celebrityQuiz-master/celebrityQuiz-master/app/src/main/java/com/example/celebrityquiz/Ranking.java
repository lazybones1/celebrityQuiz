package com.example.celebrityquiz;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Ranking {
    public String email;
    public int collect;
    public int times;

    public Ranking() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Ranking(String email, int collect, int times) {
        this.email = email;
        this.collect = collect;
        this.times = times;
    }
}
