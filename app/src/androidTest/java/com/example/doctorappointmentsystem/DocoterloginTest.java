package com.example.doctorappointmentsystem;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.doctorappointmentsystem.activity.DocLoginActivity;
import com.example.doctorappointmentsystem.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class DocoterloginTest {
    @Rule
    public ActivityTestRule<DocLoginActivity>
            testRule = new ActivityTestRule<>(DocLoginActivity.class);

    @Test
    public void login() {
        onView(withId(R.id.username)).perform(typeText("rajesh23"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("12345"), closeSoftKeyboard());

        onView(withId(R.id.btnLogin))
                .check(matches(isDisplayed()));
    }
}