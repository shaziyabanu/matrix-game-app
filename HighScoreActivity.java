package com.agmosoft.shaziya.matrixgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;


/**
 * HighScore activity contains different button of level score and handles the music to play during different states of lifecyle of activity
 */
public class HighScoreActivity extends Activity {
    //Declaring global variables
    boolean toggleButton, toggleButton1;
    Button level1Score, level2Score, level3Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent intent = getIntent();
        //Getting id's for Views in relative layout of Level1Activity
        level1Score = (Button) findViewById(R.id.level1Score);
        level2Score = (Button) findViewById(R.id.level1Score);
        level3Score = (Button) findViewById(R.id.level1Score);
        //SharedPreference for saving status of toggle button sound
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        toggleButton = sharedPreferences.getBoolean("toggleButton", false);
        toggleButton1 = sharedPreferences.getBoolean("toggleButton", true);
    }
    //code to handle click of level 1 score button
    public void level1Score(View v) {
        Intent intent = new Intent(this, Level1ScoreActivity.class);
        startActivity(intent);
    }
    //code to handle click of level 2 score button
    public void level2Score(View v) {
        Intent intent = new Intent(this, Level2ScoreActivity.class);
        startActivity(intent);
    }
    //code to handle click of level 3 score button
    public void level3Score(View v) {
        Intent intent = new Intent(this, Level3ScoreActivity.class);
        startActivity(intent);
    }
    //onkeydown method if back key is pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(HighScoreActivity.this, SettingsActivity .class);
            startActivity(intent);
        }
        return true;
    }
    //getting status of toggle sound on pause of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onPause() {
        super.onPause();
        Intent intent = new Intent(HighScoreActivity.this, MusicService.class);
        stopService(intent);
    }
    //getting status of toggle sound on resume of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onResume() {
        super.onResume();
        if (toggleButton1 == true) {
            Intent intent = new Intent(HighScoreActivity.this, MusicService.class);
            startService(intent);
        } else if (toggleButton == false) {
            Intent intent = new Intent(HighScoreActivity.this, MusicService.class);
            stopService(intent);
        }
    }
    //getting status of toggle sound on destroy of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (toggleButton1 == true) {
            Intent intent = new Intent(HighScoreActivity.this, MusicService.class);
            startService(intent);
        } else if (toggleButton == false) {
            Intent intent = new Intent(HighScoreActivity.this, MusicService.class);
            stopService(intent);
        }
    }
}
