package com.example.doctorappointmentsystem;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.doctorappointmentsystem.activity.MainActivity;
import com.example.doctorappointmentsystem.activity.registerActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignupTest {
    @Rule
    public ActivityTestRule<registerActivity>
            testRule = new ActivityTestRule<>(registerActivity.class);

    @Test
    public void signup() {
        onView(withId(R.id.username)).perform(typeText("sudeep"), closeSoftKeyboard());
        onView(withId(R.id.email)).perform(typeText("sudeep@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.firstName)).perform(typeText("sudeep"), closeSoftKeyboard());
        onView(withId(R.id.lastName)).perform(typeText("bhattarai"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("12345"), closeSoftKeyboard());
        onView(withId(R.id.address)).perform(typeText("kathmandu"), closeSoftKeyboard());


        onView(withId(R.id.btnRegister))
                .check(matches(isDisplayed()));
    }
}