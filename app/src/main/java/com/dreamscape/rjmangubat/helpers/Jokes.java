package com.dreamscape.rjmangubat.helpers;

import java.io.Serializable;

/**
 * Class to be used as  model containing properties for the different types of jokes used
 * Created by rj mangubat on 4/26/2015.
 */
public class Jokes implements Serializable{
    private String mJokeResult;


    public String getJoke() {
        return mJokeResult;
    }

    public void setJoke(String joke) {
        this.mJokeResult = joke;
    }
}
