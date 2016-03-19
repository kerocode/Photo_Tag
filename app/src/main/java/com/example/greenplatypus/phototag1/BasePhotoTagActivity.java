package com.example.greenplatypus.phototag1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Login screen has username and password fields.
 * test play button is 'greyed' (unusable) until at least three characters are in username field
 * Username and password is passed to player database
 * check to see if username already has a selfie in database
 * press play if selfie exists pass user to main screen
 * press play if selfie does not exist pass user to take a selfie screen
 */

public abstract class BasePhotoTagActivity extends AppCompatActivity {
    protected Navigator navigator = null;
    protected static final String SELFIE_STORAGE_KEY = "viewbitmap";
    protected static final String SELFIE_EXISTS_STORAGE_KEY = "imageviewvisibility";

    Bundle passedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.navigator = Navigator.getInstance();
        this.passedState = savedInstanceState;
    }
    Object getSelfie(){
        Object selfie = null;
        if(passedState != null && passedState.getBoolean(SELFIE_EXISTS_STORAGE_KEY)) {
            selfie = passedState.getParcelable(SELFIE_STORAGE_KEY);
        }
        return selfie;
    }
}




