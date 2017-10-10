package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.alexlondon07.arquitecturamvpbase.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by alexlondon07 on 10/9/17.
 */


@RunWith(AndroidJUnit4.class)
public class CustomerCreateEspressoTest {

    @Rule
    public ActivityTestRule<CustomerCreateActivity> mActivityRule =  new ActivityTestRule<>(CustomerCreateActivity.class);


    @Test
    public void ensureTextChangesWork(){
        onView(withId(R.id.activity_create_customer_name))
                .perform(typeText("Name with espresso"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.activity_create_customer_surname))
                .perform(typeText("Surname with espresso"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.activity_create_customer_phone_number))
                .perform(typeText("31234"));


        onView(withId(R.id.activity_create_customer_coordinates_location_x))
                .perform(typeText("-345"));

        onView(withId(R.id.activity_create_customer_coordinates_location_y))
                .perform(typeText("6753"));

        onView(withId(R.id.activity_create_customer_button_create)).perform(click());
    }
}
