package com.agmosoft.shaziya.matrixgame;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * main activity for the game app.
 */
public class MainActivity extends Activity {
    //Declaring and Assigning Global variables
    public Button playButton,settingsButton;
    boolean toggleButton,toggleButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Getting id's of Views of MainActivity
        playButton=(Button) findViewById(R.id.buttonplay);
        settingsButton= (Button) findViewById(R.id.buttonSettings);
        //SharedPreference for saving status of toggle button sound
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        toggleButton=sharedPreferences.getBoolean("toggleButton", false);
        toggleButton1=sharedPreferences.getBoolean("toggleButton", true);
        if (toggleButton1==true) {
            Intent intent = new Intent(MainActivity.this, MusicService.class);
            startService(intent);
        }else if(toggleButton==false) {
            Intent intent = new Intent(MainActivity.this, MusicService.class);
            stopService(intent);
        }

    }
    //method to handle click of play button
    public void playClick(View view)
    {
        Intent intent=new Intent(MainActivity.this, LevelActivity.class);
        startActivity(intent);
    }
    //method to handle click of setting button
    public void settingsClick(View view)
    {
        Intent intent=new Intent(MainActivity.this, SettingsActivity.class);
        intent.putExtra("toggleButton" , true);
        startActivity(intent);
    }
    //onkeydown method if back key is pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            //creating a dialog, whether the player want to play the game or want to exit the game
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Confirm Exit");
            builder.setMessage("Do you want to exit game?");
            builder.setPositiveButton("Quit game",new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1)
                {
                    finishAffinity();
                    Intent intent=new Intent(MainActivity.this,MusicService.class);
                    stopService(intent);
                }
            });

            builder.setNegativeButton("Play on",new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1)
                {
                    arg0.dismiss();

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return true;
    }
    //getting status of toggle sound on pause of the level activity and giving commands to play or stop music on given conditions
   @Override
    public void onPause() {
        super.onPause();
            Intent intent = new Intent(MainActivity.this, MusicService.class);
            stopService(intent);
    }
    //getting status of toggle sound on resume of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onResume() {
        super.onResume();
        if (toggleButton1==true) {
            Intent intent = new Intent(MainActivity.this, MusicService.class);
            startService(intent);
        }else if(toggleButton==false){
            Intent intent = new Intent(MainActivity.this, MusicService.class);
            stopService(intent);
        }
    }
    //getting status of toggle sound on destroy of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (toggleButton1==true) {
            Intent intent = new Intent(MainActivity.this, MusicService.class);
            stopService(intent);
        }
    }
}
