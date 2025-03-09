package hvl.dat153.quizzappdat153.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import hvl.dat153.quizzappdat153.R;
import hvl.dat153.quizzappdat153.ui.GalleryActivity;
import hvl.dat153.quizzappdat153.ui.MainActivity;

@RunWith(AndroidJUnit4.class)
public class GalleryActivityTest {

    private GalleryActivity galleryActivity;

    @Rule
    public ActivityScenarioRule<GalleryActivity> activityScenarioRule =
            new ActivityScenarioRule<>(GalleryActivity.class);

    @Rule
    public GrantPermissionRule permissionRule =
            GrantPermissionRule.grant(android.Manifest.permission.READ_EXTERNAL_STORAGE);

    @Before
    public void setup() {
        Intents.init(); // Initialize Espresso Intents

        activityScenarioRule.getScenario().onActivity(activity -> {
            galleryActivity = activity;
            IdlingRegistry.getInstance().register(galleryActivity.getIdlingResource());
        });

        // Ensure MainActivity is launched before starting test
        ActivityScenario.launch(MainActivity.class);
    }

    @After
    public void tearDown() {
        if (galleryActivity != null) {
            IdlingRegistry.getInstance().unregister(galleryActivity.getIdlingResource());
        }
        Intents.release(); // Clean up Espresso Intents
    }

    @Test
    public void testAddingImageIncreasesListSize() throws InterruptedException {
        // Step 1: Click "Gallery" on the main menu
        onView(withId(R.id.button_gallery)).perform(click());

        // Step 2: Wait for gallery to load
        Espresso.onIdle();

        // Step 3: Check if the gallery activity has opened
        onView(withId(R.id.image_container)).check(matches(isDisplayed()));

        // Step 4: Get initial image count
        int initialCount = getLinearLayoutChildCount();

        // Step 5: Click "Add Entry"
        onView(withId(R.id.button_add_entry)).perform(click());

        // Step 6: Enter a name for the image
        onView(withId(R.id.edit_text_name)).perform(replaceText("Test Image"));

        // Step 7: Stub the image picker intent to return a test image
        Intent resultData = new Intent();
        Uri testImageUri = Uri.parse("android.resource://hvl.dat153.quizzappdat153/drawable/sample_image");
        resultData.setData(testImageUri);

        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        Intents.intending(hasAction(Intent.ACTION_PICK)).respondWith(result);

        // Step 8: Click "Choose Image"
        onView(withId(R.id.button_choose_image)).perform(click());

        // Step 9: Verify the intent was sent
        intended(hasAction(Intent.ACTION_PICK));

        // Step 10: Click "Add Entry"
        onView(withId(R.id.button_add_entry)).perform(click());

        Thread.sleep(3000);

        // Step 12: Ensure gallery is displayed again
        onView(withId(R.id.image_container)).check(matches(isDisplayed()));

        // Step 13: Wait for UI update
        Espresso.onIdle();

        // Step 14: Get new image count
        int newCount = getLinearLayoutChildCount();

        // Step 15: Assert that the new count is exactly 1 more than the initial count
        assert (newCount == initialCount + 1) : "Image count should increase by 1";
    }

    private int getLinearLayoutChildCount() throws InterruptedException {
        final int[] count = new int[1];
        onView(withId(R.id.image_container)).check((view, noViewFoundException) -> {
            if (noViewFoundException != null) throw noViewFoundException;
            android.widget.LinearLayout linearLayout = (android.widget.LinearLayout) view;
            count[0] = linearLayout.getChildCount();
        });

        return count[0];
    }
}
