package hvl.dat153.quizzappdat153.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.idling.CountingIdlingResource;

import hvl.dat153.quizzappdat153.R;

public class QuizActivity extends AppCompatActivity {
    private CountingIdlingResource idlingResource = new CountingIdlingResource("QUIZ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.quiz_fragment_container, new QuizFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public IdlingResource getIdlingResource() {
        return idlingResource;
    }

    @Nullable
    public String getCurrentCorrectAnswer() {
        QuizFragment fragment = (QuizFragment) getSupportFragmentManager()
                .findFragmentById(R.id.quiz_fragment_container);
        return fragment != null ? fragment.getCorrectAnswer() : null;
    }

    public void incrementIdlingResource() {
        idlingResource.increment();
    }

    public void decrementIdlingResource() {
        if (!idlingResource.isIdleNow()) {
            idlingResource.decrement();
        }
    }
}
