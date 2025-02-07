package hvl.dat153.quizzappdat153;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

import hvl.dat153.quizzappdat153.data.AppData;

public class AddEntryActivity extends AppCompatActivity {

    private ImageView imagePreview;
    private EditText editTextName;
    private Bitmap selectedImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        Button buttonBack = findViewById(R.id.button_back);
        Button buttonChooseImage = findViewById(R.id.button_choose_image);
        Button buttonAddEntry = findViewById(R.id.button_add_entry);
        editTextName = findViewById(R.id.edit_text_name);
        imagePreview = findViewById(R.id.image_preview);

        buttonBack.setOnClickListener(v -> finish());

        buttonChooseImage.setOnClickListener(v -> chooseImage());

        buttonAddEntry.setOnClickListener(v -> addEntry());
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageChooserLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> imageChooserLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(result.getData().getData());
                        selectedImageBitmap = BitmapFactory.decodeStream(inputStream);
                        imagePreview.setImageBitmap(selectedImageBitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void addEntry() {
        String name = editTextName.getText().toString().trim();

        if (name.isEmpty() || selectedImageBitmap == null) {
            Toast.makeText(this, "Please provide both a name and an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the entry to AppData
        AppData.getInstance().addEntry(name, selectedImageBitmap);

        Toast.makeText(this, "Entry added successfully!", Toast.LENGTH_SHORT).show();
        finish();  // Go back to the previous screen
    }
}
