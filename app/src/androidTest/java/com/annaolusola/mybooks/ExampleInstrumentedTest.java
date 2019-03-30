package com.annaolusola.mybooks;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)


public class ExampleInstrumentedTest {



    public static final String STRING_TO_BE_TYPED = "The Trial";
    public static final String MESSAGE = InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.author) + " Frank Kafka\n" + InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.publisher) + " Random House\r\n\n" +
            InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.storeUrl) + " https://www.amazon.co.uk/Trial-Schocken-Kafka-Library/dp/0805209999/ref=sr_1_2?ie=UTF8&qid=1553283723&sr=8-2&keywords=the+trial+frank+kafka\n" + InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.price) + "11.99\r\n";


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.annaolusola.mybooks", appContext.getPackageName());
    }

    @Test
    public void changeText_newActivity() {
        // Type text and then press the button.
        onView(withId(R.id.editText)).perform(typeText(STRING_TO_BE_TYPED), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.resultTextView)).check(matches(withText(MESSAGE)));
    }
}
