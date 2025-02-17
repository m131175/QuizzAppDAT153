package hvl.dat153.quizzappdat153;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

import hvl.dat153.quizzappdat153.data.AppData;
import hvl.dat153.quizzappdat153.data.NamePhotoPair;

public class QuizActivity extends AppCompatActivity {

    private ImageView questionImage;
    private Button option1, option2, option3, backButton;
    private TextView scoreText;
    private int correctAnswers = 0;
    private int totalAttempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionImage = findViewById(R.id.question_image);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        scoreText = findViewById(R.id.score_text);
        backButton = findViewById(R.id.back_button);

        loadNextQuestion();

        // Back button to return to the main menu
        backButton.setOnClickListener(v -> finish());
    }

    private void loadNextQuestion() {
        List<NamePhotoPair> pairs = AppData.getInstance().getNamePhotoPairs();
        if (pairs.isEmpty()) {
            Toast.makeText(this, "No images available for the quiz!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Shuffle and select a random pair
        Collections.shuffle(pairs);
        NamePhotoPair correctPair = pairs.get(0);

        // Display the image
        if (correctPair.hasBitmap()) {
            questionImage.setImageBitmap(((NamePhotoPair.BitmapPhoto)correctPair).getPhotoBitmap());
        } else {
            questionImage.setImageResource(((NamePhotoPair.ResourcePhoto)correctPair).getPhotoResId());
        }

        // Generate randomized options (one correct, two incorrect)
        List<String> options = AppData.getInstance().getRandomOptions(correctPair.getName());
        Collections.shuffle(options);

        // Set options to buttons
        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));

        // Click listener for answer submission
        View.OnClickListener listener = v -> {
            Button selected = (Button) v;
            totalAttempts++;
            if (selected.getText().equals(correctPair.getName())) {
                correctAnswers++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong! Correct answer: " + correctPair.getName(), Toast.LENGTH_SHORT).show();
            }
            updateScore();
            loadNextQuestion();
        };

        // Attach listeners to buttons
        option1.setOnClickListener(listener);
        option2.setOnClickListener(listener);
        option3.setOnClickListener(listener);
    }

    private void updateScore() {
        String score = "Score: " + correctAnswers + " / " + totalAttempts;
        scoreText.setText(score);
    }
}
