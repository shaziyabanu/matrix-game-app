package com.agmosoft.shaziya.matrixgame;

/**
 * score class to handle getters and setters of player name score and time
 */
public class Score {
        int _id;
        String _name;
        String _time;
        String _score;
    public Score(){   }
    public Score(int id, String name, String _time,String score){
            this._id = id;
            this._name = name;
            this._time = _time;
            this._score = score;
    }

    public Score(String name,String _time,String score){
            this._name = name;
            this._time = _time;
            this._score = score;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }


    public String getName(){
            return this._name;
        }

    public void setName(String name){
            this._name = name;
        }

    public String getTime(){
            return this._time;
        }

    public void setTime(String time){
            this._time = time;
        }

    public String getScores(){
        return this._score;
    }

    public void setScores(String score){
        this._score = score;
    }
    }
