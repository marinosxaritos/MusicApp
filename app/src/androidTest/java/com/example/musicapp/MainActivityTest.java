package com.appstudio.onlinemusicplayer;

import Server.ServerRequest;
import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class MainActivityTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ServerRequest.class.getName(),null,false);

    private String singerName = "Prince";
    private String errorName = "error";

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = mActivity.findViewById(R.id.txt);
        View view1 = mActivity.findViewById(R.id.txt1);
        View view2 = mActivity.findViewById(R.id.txt2);
        View view3 = mActivity.findViewById(R.id.txt3);

        assertNotNull(view);
        assertNotNull(view1);
        assertNotNull(view2);
        assertNotNull(view3);
    }

    @Test
    public void testLaunchServerRequestOnButtonClick()
    {
        assertNotNull(mActivity.findViewById(R.id.bn));

        onView(withId(R.id.bn)).perform(click());

        Activity  serverRequest = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

        //assertNotNull(serverRequest);

        assertNotNull(R.id.txt);
        onView(withId(R.id.txt)).check(matches(withText(errorName)));
        onView(withId(R.id.txt1)).check(matches(withText("Bio")));

        //serverRequest.finish();
    }

    @Test
    public void testUserInputScenarioExistingArtist()
    {
        //input SingerName in the edit text
        onView(withId(R.id.edit_txt)).perform(typeText(singerName));
        //close soft keyboard
        Espresso.closeSoftKeyboard();
        //perform button click
        onView(withId(R.id.bn)).perform(click());
        Activity  serverRequest = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        //checking the text in the textview
        onView(withId(R.id.txt)).check(matches(withText(singerName)));

    }

    @Test
    public void testUserInputScenarioNotExistingArtist()
    {
        onView(withId(R.id.edit_txt)).perform(typeText("---"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.bn)).perform(click());
        Activity  serverRequest = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        onView(withId(R.id.txt)).check(matches(withText(errorName)));
    }

    @Test
    public void testUserInputScenarioNull()
    {
        onView(withId(R.id.edit_txt)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.bn)).perform(click());
        Activity  serverRequest = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        onView(withId(R.id.txt)).check(matches(withText(errorName)));

    }

    @After
    public void tearDown() {
        mActivity = null;
    }

    @Test
    public void onCreate() {
    }
}