package com.example.doctorappointmentsystem;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.doctorappointmentsystem.activity.DocRegisterActivity;
import com.example.doctorappointmentsystem.activity.registerActivity;

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
public class DocRegisterTest {
    @Rule
    public ActivityTestRule<DocRegisterActivity>
            testRule = new ActivityTestRule<>(DocRegisterActivity.class);

    @Test
    public void signup() {
        onView(withId(R.id.username)).perform(typeText("binita2"), closeSoftKeyboard());
        onView(withId(R.id.email)).perform(typeText("binita2@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.firstName)).perform(typeText("binita"), closeSoftKeyboard());
        onView(withId(R.id.lastName)).perform(typeText("rai"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("asdfgh"), closeSoftKeyboard());
        onView(withId(R.id.address)).perform(typeText("dhankuta"), closeSoftKeyboard());
        onView(withId(R.id.qualification)).perform(typeText("MBBS"), closeSoftKeyboard());

        onView(withId(R.id.btnRegister))
                .check(matches(isDisplayed()));
    }
}