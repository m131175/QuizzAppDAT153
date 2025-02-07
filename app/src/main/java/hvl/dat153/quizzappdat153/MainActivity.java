package hvl.dat153.quizzappdat153;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import hvl.dat153.quizzappdat153.data.AppData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Preload sample data
        AppData.getInstance().preloadSampleData();

        Button buttonGallery = findViewById(R.id.button_gallery);
        Button buttonQuiz = findViewById(R.id.button_quiz);

        buttonGallery.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
            startActivity(intent);
        });

        buttonQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });
    }

}
