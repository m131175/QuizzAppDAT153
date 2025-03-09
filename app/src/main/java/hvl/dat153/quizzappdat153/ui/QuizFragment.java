package hvl.dat153.quizzappdat153.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hvl.dat153.quizzappdat153.R;
import hvl.dat153.quizzappdat153.data.model.ImageEntity;
import hvl.dat153.quizzappdat153.data.viewmodel.ImageViewModel;

public class QuizFragment extends Fragment {
    private ImageView questionImage;
    private Button option1, option2, option3;
    private TextView scoreText;
    private ImageViewModel imageViewModel;
    private List<ImageEntity> imageList;
    private String correctAnswer;
    private int correctAnswers = 0;
    private int totalAttempts = 0;

    public QuizFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                ? R.layout.fragment_quiz_land
                : R.layout.fragment_quiz;
        return inflater.inflate(layoutId, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        questionImage = view.findViewById(R.id.question_image);
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        scoreText = view.findViewById(R.id.score_text);

        option1.setOnClickListener(v -> checkAnswer(option1));
        option2.setOnClickListener(v -> checkAnswer(option2));
        option3.setOnClickListener(v -> checkAnswer(option3));

        Button backButton = view.findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        if (savedInstanceState != null) {
            correctAnswers = savedInstanceState.getInt("correctAnswers");
            totalAttempts = savedInstanceState.getInt("totalAttempts");
            correctAnswer = savedInstanceState.getString("correctAnswer");
            imageList = (ArrayList<ImageEntity>) savedInstanceState.getSerializable("imageList");

            if (imageList != null && !imageList.isEmpty()) {
                loadNextQuestion();
            }
        }

        if (imageList == null) {
            imageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
            imageViewModel.getAllImages().observe(getViewLifecycleOwner(), images -> {
                if (images != null && images.size() >= 3) {
                    imageList = new ArrayList<>(images);
                    loadNextQuestion();
                } else {
                    Toast.makeText(getActivity(), "Not enough images for a quiz!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Log.d("QuizFragment", "Current layout: " + getResources().getConfiguration().orientation);
    }

    private void loadNextQuestion() {
        if (imageList == null || imageList.size() < 3) {
            Toast.makeText(getActivity(), "Not enough images!", Toast.LENGTH_SHORT).show();
            return;
        }

        Collections.shuffle(imageList);
        ImageEntity correctImage = imageList.get(0);
        correctAnswer = correctImage.name;

        if (correctImage.imageUri != null && !correctImage.imageUri.isEmpty()) {
            questionImage.setImageURI(Uri.parse(correctImage.imageUri));
        } else {
            questionImage.setImageResource(R.drawable.ic_launcher_background);
        }

        List<ImageEntity> options = new ArrayList<>(imageList);
        options.remove(correctImage);
        Collections.shuffle(options);

        List<String> answerChoices = new ArrayList<>();
        answerChoices.add(correctAnswer);
        answerChoices.add(options.get(0).name);
        answerChoices.add(options.get(1).name);
        Collections.shuffle(answerChoices);

        option1.setText(answerChoices.get(0));
        option2.setText(answerChoices.get(1));
        option3.setText(answerChoices.get(2));

        updateUI();
    }

    private void checkAnswer(Button selected) {
        totalAttempts++;
        if (selected.getText().toString().equals(correctAnswer)) {
            correctAnswers++;
            Toast.makeText(getActivity(), "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Wrong!", Toast.LENGTH_SHORT).show();
        }
        scoreText.setText("Score: " + correctAnswers + "/" + totalAttempts);
        loadNextQuestion();
    }

    private void updateUI() {
        scoreText.setText("Score: " + correctAnswers + "/" + totalAttempts);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("correctAnswers", correctAnswers);
        outState.putInt("totalAttempts", totalAttempts);
        outState.putString("correctAnswer", correctAnswer);

        if (imageList != null) {
            outState.putSerializable("imageList", new ArrayList<>(imageList));
        }
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
