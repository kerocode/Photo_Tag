package com.example.greenplatypus.phototag1;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
/*
 * Created by Casey White on 10/26/2015.
 * <p/>
 * User is to take a Selfie for game
 * Possible UI overlay on front facing Camera
 * Button that takes user to front facing Camera
 * after selfie is taken, use face++ to check that picture is of a face
 * if positive pass user to main page
 * if negative redisplay take selfie page
 */

@RunWith(AndroidJUnit4.class)
public class TakeSelfieTest  {
    @Rule
    public ActivityTestRule<TakeSelfie> mActivityRule =
            new ActivityTestRule<>(TakeSelfie.class);




        @Test
        public void TapToTakeSelfie() {
            // test tapSelfie button
            onView(withId(R.id.buttonTakeSelfieAction))
                    .perform(click());

        }



}





