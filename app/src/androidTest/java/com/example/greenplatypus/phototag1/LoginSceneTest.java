package com.example.greenplatypus.phototag1;

import android.support.test.espresso.action.CloseKeyboardAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


/**
 * Created by kero on 10/17/2015.
 * <p/>
 * Login screen has username and password fields.
 * test play button is 'greyed' (unusable) until at least three characters are in username field
 * Username and password is passed to player database
 * check to see if username already has a selfie in database
 * press play if selfie exists pass user to main screen
 * press play if selfie does not exist pass user to take a selfie screen
 */

@RunWith(AndroidJUnit4.class)
public class LoginSceneTest {

    @Rule
    public ActivityTestRule<LoginScene> mActivityRule =
            new ActivityTestRule<>(LoginScene.class);

    @Test
    public void testUsername() {
        //tests usernameonView(withText("Name"));

    }

    @Test
    public void testPasswordInput() {
        //tests Password Input
        onView(withId(R.id.loginScreenPassword)).perform(ViewActions.typeText("password"));
        onView(withId(R.id.loginScreenPassword)).check(matches(withText("password")));

    }

    @Test
    public void testPlayonGuardInitial_Guarded() {
        //assert the PlayOn button is not clickable without no inputs from loginScreenUsername and loginScreenPassword
        onView(withId(R.id.loginScreenUserName)).check(matches(withText("")));
        onView(withId(R.id.loginScreenPassword)).check(matches(withText("")));
        onView(withId(R.id.PlayOn)).check(matches(not(isEnabled())));
    }

    @Test
    public void testPlayonGuardInitial_Guarded_UserNameGoodPasswordBad() {
        final String goodUserName = "Casey";
        onView(withId(R.id.loginScreenUserName)).perform(ViewActions.typeText(goodUserName));
        onView(isRoot()).perform(new CloseKeyboardAction());
        onView(withId(R.id.loginScreenUserName)).check(matches(withText(goodUserName)));
        onView(withId(R.id.loginScreenPassword)).check(matches(withText("")));
        onView(withId(R.id.PlayOn)).check(matches(not(isEnabled())));
    }

    @Test
    public void testPlayonGuardInitial_Guarded_UserNameBadPasswordGood() {
        final String goodPassword = "mypass";
        onView(withId(R.id.loginScreenPassword)).perform(ViewActions.typeText(goodPassword));
        onView(isRoot()).perform(new CloseKeyboardAction());
        onView(withId(R.id.loginScreenUserName)).check(matches(withText("")));
        onView(withId(R.id.loginScreenPassword)).check(matches(withText(goodPassword)));
        onView(withId(R.id.PlayOn)).check(matches(not(isEnabled())));
    }

    @Test
    public void testPlayonWithGoodUserNameAndPassword() {

        //tests username Input
        onView(withId(R.id.loginScreenUserName)).perform(ViewActions.typeText("username"));
        onView(isRoot()).perform(new CloseKeyboardAction());
        // test password input
        onView(withId(R.id.loginScreenPassword)).perform(ViewActions.typeText("password"));
        onView(isRoot()).perform(new CloseKeyboardAction());
        // test if play on button enable and become a clickable or not
        onView(withId(R.id.PlayOn)).check(matches(isEnabled()));
    }

}

