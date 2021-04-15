package com.example.celebrityquiz;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BgmService extends Service {

    MediaPlayer mediaplayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaplayer = MediaPlayer.create(this, R.raw.bgm);
        mediaplayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mediaplayer.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        mediaplayer.stop();

        super.onDestroy();
    }
}

