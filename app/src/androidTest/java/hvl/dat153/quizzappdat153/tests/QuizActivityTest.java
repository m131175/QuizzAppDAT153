package hvl.dat153.quizzappdat153.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import hvl.dat153.quizzappdat153.R;
import hvl.dat153.quizzappdat153.ui.QuizActivity;
import hvl.dat153.quizzappdat153.ui.QuizFragment;

@RunWith(AndroidJUnit4.class)
public class QuizActivityTest {

    @Rule
    public ActivityTestRule<QuizActivity> activityRule = new ActivityTestRule<>(QuizActivity.class);

    @Test
    public void testScoreUpdatesCorrectly() throws InterruptedException {
        // Wait for the first question to load
        Thread.sleep(1000); // Allow the UI and LiveData to update (replace with IdlingResource for production)

        // Assert initial score is 0/0
        onView(withId(R.id.score_text)).check(matches(withText("Score: 0/0")));

        // Simulate selecting the correct answer for the first question
        int correctAnswerId = getCorrectAnswerButtonId();
        onView(withId(correctAnswerId)).perform(click());

        // Wait for the score to update
        Thread.sleep(1000);

        // Check that the score text reflects one correct answer
        onView(withId(R.id.score_text)).check(matches(withText("Score: 1/1")));

        // Simulate selecting an incorrect answer for the next question
        int incorrectAnswerId = getIncorrectAnswerButtonId(correctAnswerId);
        onView(withId(incorrectAnswerId)).perform(click());

        // Wait for the score to update
        Thread.sleep(1000);

        // Check that the score text reflects one correct and one incorrect answer
        onView(withId(R.id.score_text)).check(matches(withText("Score: 1/2")));
    }

    private int getCorrectAnswerButtonId() {
        QuizActivity activity = activityRule.getActivity();
        QuizFragment fragment = (QuizFragment) activity.getSupportFragmentManager()
                .findFragmentById(R.id.quiz_fragment_container);

        String correctAnswer = fragment.getCorrectAnswer();
        if (correctAnswer.equals(activity.findViewById(R.id.option1).getTag())) {
            return R.id.option1;
        } else if (correctAnswer.equals(activity.findViewById(R.id.option2).getTag())) {
            return R.id.option2;
        } else {
            return R.id.option3;
        }
    }

    private int getIncorrectAnswerButtonId(int correctAnswerId) {
        if (correctAnswerId == R.id.option1) {
            return R.id.option2; // Assume option2 is incorrect for simplicity
        } else if (correctAnswerId == R.id.option2) {
            return R.id.option1;
        } else {
            return R.id.option2; // Default to option2 if correctAnswerId is option3
        }
    }
}
