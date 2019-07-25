package com.agmosoft.shaziya.matrixgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Help activity explains how to play game in detail for the player and handles the music to play during different states of lifecyle of activity
 */
public class HelpActivity extends Activity {
    //Declaring global variables
    boolean toggleButton,toggleButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Intent intent=getIntent();
        //SharedPreference for saving status of toggle button sound
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        toggleButton=sharedPreferences.getBoolean("toggleButton", false);
        toggleButton1=sharedPreferences.getBoolean("toggleButton", true);

    }
    //getting status of toggle sound on pause of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onPause() {
        super.onPause();
        Intent intent = new Intent(HelpActivity.this, MusicService.class);
        stopService(intent);
    }
    //getting status of toggle sound on resume of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onResume() {
        super.onResume();
        if(toggleButton1==true){
            Intent intent=new Intent(HelpActivity.this, MusicService.class);
            startService(intent);
        }else if(toggleButton==false){
            Intent intent = new Intent(HelpActivity.this, MusicService.class);
            stopService(intent);
        }
    }
    //getting status of toggle sound on destroy of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (toggleButton1==true) {
            Intent intent = new Intent(HelpActivity.this, MusicService.class);
            startService(intent);
        }else if(toggleButton==false){
            Intent intent=new Intent(HelpActivity.this, MusicService.class);
            stopService(intent);
        }
    }
}