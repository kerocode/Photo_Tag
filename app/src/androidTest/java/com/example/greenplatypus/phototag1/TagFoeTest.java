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
 * Created by Casey White on 10/26/2015.
 *
 * User is shown a full screen image of players selfie
 * top left corner name is displayed
 * top right corner score is displayed
 * bottom of screen is 'tag foe' button that pulls up back camera
 * pic and selected player selfie sent to face++
 * after pic is taken user is passed back to main screen
 */

@RunWith(AndroidJUnit4.class)
public class TagFoeTest {

    @Rule
    public ActivityTestRule<TagFoe> mActivityRule =
            new ActivityTestRule<>(TagFoe.class);
    @Test
    public void testTagFoe()
    {
        // test select button
        onView(withId(R.id.tagFoeButton))
                .perform(ViewActions.click());
    }






}
