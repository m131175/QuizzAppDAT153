package hvl.dat153.quizzappdat153.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import hvl.dat153.quizzappdat153.R;
import hvl.dat153.quizzappdat153.data.db.AppDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase.getInstance(this);

        Button buttonGallery = findViewById(R.id.button_gallery);
        Button buttonQuiz = findViewById(R.id.button_quiz);

        buttonGallery.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GalleryActivity.class)));
        buttonQuiz.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, QuizActivity.class)));
    }
}
