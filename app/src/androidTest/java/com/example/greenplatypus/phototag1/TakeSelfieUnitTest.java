package com.example.greenplatypus.phototag1;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;

import org.junit.Rule;

/**
 * Created by kero on 11/25/2015.
 */
public class TakeSelfieUnitTest extends ActivityUnitTestCase<WantedScene> {

    @Rule
    public ActivityTestRule<WantedScene> Rule =
            new ActivityTestRule<>(WantedScene.class);
   private Intent mLaunchIntent;
    public TakeSelfieUnitTest() {
        super(WantedScene.class);
    }


    @Override
    protected void setUp() throws Exception     {
        super.setUp();

        // In setUp, you can create any shared test data, or set up mock components to inject
        // into your Activity.  But do not call startActivity() until the actual test methods.
        mLaunchIntent = new Intent(getInstrumentation().getTargetContext(),
                TakeSelfie.class);
    }



    @MediumTest
    public void testSuLaunch() {
        startActivity(mLaunchIntent, null, null);
       final Button mButton = (Button) getActivity().findViewById(R.id.buttonTakeSelfieToWanted);

        // This test confirms that when you click the button, the activity attempts to open
        // another activity (by calling startActivity) and close itself (by calling finish()).
        mButton.performClick();

        assertNotNull(getStartedActivityIntent());
        assertEquals(true, isFinishCalled());
    }

    @LargeTest
    public void testNextActivityWasLaunchedWithIntent() {
        startActivity(mLaunchIntent, null, null);
        final Button launchNextButton = (Button) getActivity().findViewById(R.id.buttonTakeSelfieToWanted);
        //Because this is an isolated ActivityUnitTestCase we have to directly click the
        //button from code
        launchNextButton.performClick();

// Get the intent for the next started activity
        final Intent launchIntent = getStartedActivityIntent();
        //Verify the intent was not null.
        assertNotNull(launchIntent);
        //Verify that LaunchActivity was finished after button click
        assertTrue(isFinishCalled());

    }


}

