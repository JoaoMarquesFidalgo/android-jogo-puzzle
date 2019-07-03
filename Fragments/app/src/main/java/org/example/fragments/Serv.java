package org.example.fragments;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class Serv extends Service {

    MediaPlayer mp;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    public void onCreate()
    {
        mp = MediaPlayer.create(this, R.raw.a_97202_mp4);
        mp.setLooping(true);
        mp.setVolume(100,100);
    }
    public void onDestroy()
    {
        mp.stop();
    }

    public void onStart(Intent intent,int startid){

        Log.d("song", "On start1");
        mp.start();
    }
}