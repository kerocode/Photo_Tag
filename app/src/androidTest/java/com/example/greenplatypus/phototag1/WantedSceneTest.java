package com.example.greenplatypus.phototag1;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;


/**
 * Created by kh on 10/27/15.
 */
public class WantedSceneTest extends ActivityInstrumentationTestCase2<WantedScene> {

    private WantedScene mActivity;

    public WantedSceneTest() {
        super(WantedScene.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }


    // Scroll to a cell, tap a cell, switch to TagFoe screen
    public void testGridView() {


        onData(anything()).inAdapterView(withId(R.id.gridView1)).atPosition(0).perform(ViewActions.click());


    }


    // Test if images are loaded on screen. This test should always fail (sad path).


}
