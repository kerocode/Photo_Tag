package com.example.greenplatypus.phototag1;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by kero on 11/25/2015.
 */
@RunWith(AndroidJUnit4.class)
public class WantedListButtonTest {

    @Rule
    public ActivityTestRule<TakeSelfie> ActivityRule =
            new ActivityTestRule<>(TakeSelfie.class);

    @Test
    public void wantedlistButton(){
        onView(withId(R.id.buttonTakeSelfieToWanted)).perform(ViewActions.click());
    }
}
