package com.agmosoft.shaziya.matrixgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
/**
 * Level activity contains different level of game for the player and handles the music to play during different states of lifecyle of activity
 */

public class LevelActivity extends Activity {
    //Declaring and Assigning global variables\
    Button buttonL1, buttonL2, buttonL3;
    boolean toggleButton, toggleButton1, button, button1,button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        Intent intent = getIntent();
        //Getting id's for Views in relative layout of Level1Activity
        buttonL1 = (Button) findViewById(R.id.button1);
        buttonL2 = (Button) findViewById(R.id.button2);
        buttonL3 = (Button) findViewById(R.id.button3);
        //changing color and disable button click of level
        buttonL2.setEnabled(false);
        buttonL2.setBackgroundResource(R.drawable.levelgrey_buttons);
        buttonL3.setEnabled(false);
        buttonL3.setBackgroundResource(R.drawable.levelgrey_buttons);
        //SharedPreference for saving status of toggle button sound
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        toggleButton = sharedPreferences.getBoolean("toggleButton", false);
        toggleButton1 = sharedPreferences.getBoolean("toggleButton", true);
        //SharedPreference for saving status of button level
        button = sharedPreferences.getBoolean("button", false);
        button1 = sharedPreferences.getBoolean("button1", false);
        button2=sharedPreferences.getBoolean("button2", false);
        button3=sharedPreferences.getBoolean("button3", false);
    }

    public void level1Select(View view) {
        Intent intent = new Intent(this, Level1Activity.class);
        startActivity(intent);
    }

    public void level2Select(View view) {
        Intent intent = new Intent(this, Level2Activity.class);
        startActivity(intent);
    }

    public void level3Select(View view) {
        Intent intent = new Intent(this, Level3Activity.class);
        startActivity(intent);
    }

    //onkeydown method if back key is pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(LevelActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }

    //getting status of toggle sound on pause of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onPause() {
        super.onPause();
        Intent intent = new Intent(LevelActivity.this, MusicService.class);
        stopService(intent);
        if (button == true) {
            buttonL2.setEnabled(true);
            buttonL2.setBackgroundResource(R.drawable.all_buttons);
        } else if (button2 == false) {
            buttonL2.setEnabled(false);
            buttonL2.setBackgroundResource(R.drawable.levelgrey_buttons);
        }
        if (button1 == true) {
            buttonL3.setEnabled(true);
            buttonL3.setBackgroundResource(R.drawable.all_buttons);
        } else if (button3 == false) {
            buttonL3.setEnabled(false);
            buttonL3.setBackgroundResource(R.drawable.levelgrey_buttons);
        }
    }

    //getting status of toggle sound on resume of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onResume() {
        super.onResume();
       if (button == true) {
            buttonL2.setEnabled(true);
            buttonL2.setBackgroundResource(R.drawable.all_buttons);
        } else if (button2 == false) {
            buttonL2.setEnabled(false);
            buttonL2.setBackgroundResource(R.drawable.levelgrey_buttons);
        }
        if (button1 == true) {
            buttonL3.setEnabled(true);
            buttonL3.setBackgroundResource(R.drawable.all_buttons);
        } else if (button3 == false) {
            buttonL3.setEnabled(false);
            buttonL3.setBackgroundResource(R.drawable.levelgrey_buttons);
        }
        if (toggleButton1 == true) {
            Intent intent = new Intent(LevelActivity.this, MusicService.class);
            startService(intent);
        } else if (toggleButton == false) {
            Intent intent = new Intent(LevelActivity.this, MusicService.class);
            stopService(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
         if (button == true) {
            buttonL2.setEnabled(true);
            buttonL2.setBackgroundResource(R.drawable.all_buttons);
        } else if (button2 == false) {
            buttonL2.setEnabled(false);
            buttonL2.setBackgroundResource(R.drawable.levelgrey_buttons);
        }
    }

    //getting status of toggle sound on destroy of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onDestroy() {
        super.onDestroy();
       if (button == true) {
            buttonL2.setEnabled(true);
            buttonL2.setBackgroundResource(R.drawable.all_buttons);
        } else if (button2 == false) {
            buttonL2.setEnabled(false);
            buttonL2.setBackgroundResource(R.drawable.levelgrey_buttons);
        }
        if (button1 == true) {
            buttonL3.setEnabled(true);
            buttonL3.setBackgroundResource(R.drawable.all_buttons);
        } else if (button3 == false) {
            buttonL3.setEnabled(false);
            buttonL3.setBackgroundResource(R.drawable.levelgrey_buttons);
        }
        if (toggleButton1 == true) {
            Intent intent = new Intent(LevelActivity.this, MusicService.class);
            startService(intent);
        } else if (toggleButton == false) {
            Intent intent = new Intent(LevelActivity.this, MusicService.class);
            stopService(intent);
        }
    }
}