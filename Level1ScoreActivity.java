package com.agmosoft.shaziya.matrixgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

/**
 * Level1 score activity stores score of level 1 game of the player and handles the music to play during different states of lifecyle of activity
 */
public class Level1ScoreActivity extends Activity {
    //Declaring and Assigning global variables
    boolean toggleButton, toggleButton1;
    ListView show;
    ArrayList<String> addArray=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1score);
        Intent intent = getIntent();
        //Getting id's for Views in relative layout of Level1Activity
        show= (ListView) findViewById(R.id.listView);
        //SharedPreference for saving status of toggle button sound
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        toggleButton = sharedPreferences.getBoolean("toggleButton", false);
        toggleButton1 = sharedPreferences.getBoolean("toggleButton", true);
        DatabaseHandler db=new DatabaseHandler(this);
        // Reading all scores
        Log.d("Reading: ", "Reading all contacts..");
        List<Score> scores = db.getAllScores();

        for (Score sc : scores) {
            String log = sc.getName() + "   -   " +
                    sc.getTime()+ "   -   " +sc.getScores()  ;
            // Writing scores to log
            addArray.add(log);
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(Level1ScoreActivity.this, android.R.layout.simple_list_item_1, addArray);
            show.setAdapter(adapter);
        }
    }
    //onkeydown method if back key is pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Level1ScoreActivity.this,HighScoreActivity.class);
            startActivity(intent);
        }
        return true;
    }
    //getting status of toggle sound on pause of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onPause() {
        super.onPause();
            Intent intent = new Intent(Level1ScoreActivity.this, MusicService.class);
            stopService(intent);
    }
    //getting status of toggle sound on resume of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onResume() {
        super.onResume();
        if (toggleButton1 == true) {
            Intent intent = new Intent(Level1ScoreActivity.this, MusicService.class);
            startService(intent);
        } else if (toggleButton == false) {
            Intent intent = new Intent(Level1ScoreActivity.this, MusicService.class);
            stopService(intent);
        }
    }
    //getting status of toggle sound on destroy of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (toggleButton1 == true) {
            Intent intent = new Intent(Level1ScoreActivity.this, MusicService.class);
            startService(intent);
        } else if (toggleButton == false) {
            Intent intent = new Intent(Level1ScoreActivity.this, MusicService.class);
            stopService(intent);
        }
    }
}
