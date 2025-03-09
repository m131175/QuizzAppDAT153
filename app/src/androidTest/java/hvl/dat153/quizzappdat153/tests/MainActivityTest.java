package hvl.dat153.quizzappdat153.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import hvl.dat153.quizzappdat153.R;
import hvl.dat153.quizzappdat153.ui.MainActivity;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testGalleryButtonOpensGalleryActivity() {
        // Click the Gallery button
        onView(withId(R.id.button_gallery)).perform(click());

        // Check if the image container (LinearLayout inside ScrollView) is displayed
        onView(withId(R.id.image_container)).check(matches(isDisplayed()));
    }
}
