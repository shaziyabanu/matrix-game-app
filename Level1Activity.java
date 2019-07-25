package com.agmosoft.shaziya.matrixgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;

/**
 *Level 1 Activity which implements drag and touch listener for views, handles the music to play during different states of lifecyle methods of activity
 */
public class Level1Activity extends Activity implements View.OnDragListener,View.OnTouchListener{
    //Declaring and Assigning global variables
    boolean toggleButton,toggleButton1;
    int num=0, numrex=0,score=0;
    String name="";
    RelativeLayout mainLay;
    TextView scoreTextView, timeTextView;
    SharedPreferences sharedPreferences;
    DatabaseHandler db;
    CounterClass timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);
        Intent intent=getIntent();
        //Getting id's for Views in relative layout of Level1Activity
        scoreTextView = (TextView) findViewById(R.id.textViewScore);
        timeTextView = (TextView) findViewById(R.id.time);
        mainLay = (RelativeLayout) findViewById(R.id.level1Activity);

        findViewById(R.id.frameNum2).setOnDragListener(this);
        findViewById(R.id.frameNum3).setOnDragListener(this);
        findViewById(R.id.frameNum4).setOnDragListener(this);
        findViewById(R.id.frameNum5).setOnDragListener(this);
        findViewById(R.id.frameNum7).setOnDragListener(this);
        findViewById(R.id.frameNum8).setOnDragListener(this);
        findViewById(R.id.frameNum9).setOnDragListener(this);
        findViewById(R.id.Num2).setOnTouchListener(this);
        findViewById(R.id.Num3).setOnTouchListener(this);
        findViewById(R.id.Num5).setOnTouchListener(this);
        findViewById(R.id.Num6).setOnTouchListener(this);
        findViewById(R.id.Num7).setOnTouchListener(this);
        findViewById(R.id.Num8).setOnTouchListener(this);
        findViewById(R.id.Num9).setOnTouchListener(this);
        //SharedPreference for saving status of toggle button sound
        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        toggleButton = sharedPreferences.getBoolean("toggleButton", false);
        toggleButton1 = sharedPreferences.getBoolean("toggleButton", true);
        //setting timetextview for required time
        timeTextView.setText("02:00");
        //Creating database handler object for storing scores of the player
        db = new DatabaseHandler(this);
        //la = new LevelActivity();
        //Creating a counter class object for countdown timer
        timer = new CounterClass(120000, 1);
        //Starting countdown timer
        timer.start();
    }
        //counterclass for handling countdown timer
         public class CounterClass extends CountDownTimer {
        //constructor for counter class
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        //handling countdown time and assigning time for the timertextview
        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            timeTextView.setText(hms);
        }
        //on complition of timer, dialog called to display game over
        @Override
        public void onFinish() {
            AlertDialog.Builder builder = new AlertDialog.Builder(Level1Activity.this);
            builder.setTitle("              Game Over");
            builder.setMessage("Do you want to play again?");
            builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
//if positive button is clicked then a new level1 activity s created
                @Override
                public void onClick(DialogInterface arg0, int arg1)
                {
                    recreate();
                }
            });

            builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
//if negative button is clicked then go back to level selection Activity
                @Override
                public void onClick(DialogInterface arg0, int arg1)
                {
                    Intent intent=new Intent(Level1Activity.this, LevelActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    //ontouch method for dragging a view , creating a shadow of view
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            view.setVisibility(View.VISIBLE);
            return true;
        } else {
            return false;
        }
    }
    //ondrag method to handle the event that occur during drag and drop of views
    public boolean onDrag(View v, DragEvent dragevent) {
        int action = dragevent.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            //handling drop action
            case DragEvent.ACTION_DROP:
                //getting the state of view and remove the view from parent if already exits
                View view = (View) dragevent.getLocalState();
                ViewGroup owner = (ViewGroup) view.getParent();
                owner.removeView(view);
                //creating a container for relative layout to drop view
                RelativeLayout container = (RelativeLayout) v;
                //counting the number of childs in a container
                int count = container.getChildCount();
                //if count is 0 then add view in the container and increase score after adding a view to the container
                if (count == 0) {
                    container.addView(view);
                    //making a view visible after drop
                    view.setVisibility(View.VISIBLE);
                    //getting tag of the view drop and parsing to integer
                    String buttonText = view.getTag().toString();
                    int b1 = Integer.parseInt(buttonText);
                    //getting tag from parent in container and parsing to integer value
                    String parenttag = container.getTag().toString();
                    int t1 = Integer.parseInt(parenttag);
                    //comparing tags of view child and parent in container
                    if (b1 == t1) {
                        //changing the background of dropped tile if correct number is dropped to the target
                        view.setBackgroundResource(R.drawable.tiles);
                        //displaying toast message if a correct drop done (if tag of view and parent matches) and incrementing score
                        Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();
                        score+=200;
                        //String numrex1 = span.toString();
                        //checking condition if all drops done are correct and incrementing score a s bonus after complition of level
                        String numrex1="Correct";
                        if (numrex1 == "Correct") {
                            numrex = num++;
                            if (numrex == 6) {
                                score+=2000;
                                timer.cancel();
                                SharedPreferences sharedPreferences = PreferenceManager
                                        .getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("button", true);
                                editor.commit();
                                //creating a alertdialog after the level is complete and to get the player name from the player through edittext
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Level1Activity.this);
                                builder.setTitle("You cleared Level 1");
                                // Setting Dialog Message
                                builder.setMessage("Enter your Name");
                                //creating a edittext in the dialog
                                final EditText input=new EditText(Level1Activity.this);
                                //setting hint for edittext
                                input.setHint("Unknown");
                               // setting layout params for displaying edit text in dialog
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                builder.setView(input);
                                //on click of positive button in dialog score , time and player name is stored in the sqlite database
                                builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        //getting text from edit text
                                        name = input.getText().toString();
                                        if (name.isEmpty()) {
                                            name = "Unknown";
                                        }
                                        //converting integer value of score to string
                                        String value = Integer.toString(score);
                                        //getting string value of time
                                        String time = timeTextView.getText().toString();
                                        //adding score time and player name to the database in sglite
                                        db.addScore(new Score(name, time, value));
                                        //if database storage increases more than 10 and if score less than provided score
                                        //then delete the database score, name, time of the given condition
                                        if (db.getScoresCount() >= 10) {
                                            if (score <= 4000) {
                                                db.deleteScore(new Score(name, time, value));
                                            } else if (score <= 7000) {
                                                db.deleteScore(new Score(name, time, value));
                                            } else if (score <= 15000) {
                                                db.deleteScore(new Score(name, time, value));
                                            }
                                        }
                                        Intent intent1=new Intent(Level1Activity.this, Level1ScoreActivity.class);
                                        startActivity(intent1);
                                    }
                                });
                                    //if a back keydown is pressed without entering name of player
                                // then moving directly to highscore activity withought storing the player details
                                builder.setOnKeyListener(new Dialog.OnKeyListener() {

                                    @Override
                                    public boolean onKey(DialogInterface arg0, int keyCode,
                                                         KeyEvent event){
                                            // TODO Auto-generated method stub
                                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                                Intent intent = new Intent(Level1Activity.this, Level1ScoreActivity.class);
                                                startActivity(intent);
                                            }
                                            return false;

                                        }
                                });

                                //creating dialog and displaying
                                AlertDialog d1 = builder.create();
                                d1.show();
                            }
                        }
                    }
                    else{
                        //displaying toast message if a wrong drop done (if tag of view and parent mismatches)
                        Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show();
                        view.setBackgroundResource(R.drawable.wrongtiles);
                    }
                }else {
                    //displaying toast message if a wrong drop done (if tag of view and parent mismatches)
                    Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show();
                    //remove view from container if wrong view is dropped and moving it back to mainlayout
                    container.removeView(view);
                    mainLay.addView(view);
                }
                //displaying score in the score text view
               scoreTextView.setText("Score: "+score);
                break;

            default:
                break;
        }
        return true;
    }
    //onkeydown method if back key is pressed
    @Override
        public boolean onKeyDown (int keyCode, KeyEvent event){
            final CharSequence[] items = {"Resume Level", "Restart Level ", "exit level"};
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                //creating dialog if back key pressed and proving the player
                // to select options to restart level or resume level or to exit level
                    AlertDialog.Builder builder = new AlertDialog.Builder(Level1Activity.this);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            switch (item) {
                                case 0:
                                    dialog.dismiss();
                                    break;
                                case 1:
                                    recreate();
                                    break;
                                case 2:
                                    SharedPreferences sharedPreferences = PreferenceManager
                                            .getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("button2", false);
                                    editor.commit();
                                    finish();
                                    break;
                                default:
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            return true;
        }
    //getting status of toggle sound on pause of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onPause() {
        super.onPause();
            Intent intent = new Intent(Level1Activity.this, MusicService.class);
            stopService(intent);
    }
    //getting status of toggle sound on resume of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onResume() {
        super.onResume();
        if (toggleButton1==true) {
            Intent intent = new Intent(Level1Activity.this, MusicService.class);
            startService(intent);
        }else if(toggleButton==false){
            Intent intent = new Intent(Level1Activity.this, MusicService.class);
            stopService(intent);
        }
    }
    //getting status of toggle sound on destroy of the level activity and giving commands to play or stop music on given conditions
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (toggleButton1==true) {
            Intent intent = new Intent(Level1Activity.this, MusicService.class);
            startService(intent);
        }else if(toggleButton==false){
            Intent intent=new Intent(Level1Activity.this, MusicService.class);
            stopService(intent);
        }
    }

}