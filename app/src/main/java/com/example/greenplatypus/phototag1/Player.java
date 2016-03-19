package com.example.greenplatypus.phototag1;

import android.graphics.Bitmap;

/**
 * Created by Keith Herbert on 10/26/15.
 */

public class Player {

    // Fields /////////////////////////////////////////////////////////////////
    private String name;
    private int score = 0;
    private Bitmap selfie;
    private String password;

    public Player(){}

    // Constructors ///////////////////////////////////////////////////////////
    public Player(String name, int score, Bitmap selfie) {
        this.name = name;
        this.score = score;
        this.selfie = selfie;
    }
    public Player(String name)          { this.name = name; }
    public Player(String name, String password)          {
        this.name = name;
        this.password = password;
    }


    //  Getters and Setters ///////////////////////////////////////////////////
    public String getName()             { return name;          }
    public void setName(String name)    { this.name = name;     }

    public int getScore()               { return score;         }
    public void setScore(int score)     { this.score = score;   }

    public Bitmap getSelfie()           { return selfie;        }
    public void setSelfie(Bitmap selfie){ this.selfie = selfie; }


    // Score incrementing ////////////////////////////////////////////////////
    public String getPassword()                 { return password;          }
    public void setPassword(String password)    { this.password = password;     }

    public int incrementScore() {
        this.score++;
        return score;
    }
    public int incrementScore(int byHowMuch) {
        this.score += byHowMuch;
        return score;
    }

}
