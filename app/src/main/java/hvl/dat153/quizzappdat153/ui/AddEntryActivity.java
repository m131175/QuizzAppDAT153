package hvl.dat153.quizzappdat153.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import hvl.dat153.quizzappdat153.R;
import hvl.dat153.quizzappdat153.data.model.ImageEntity;
import hvl.dat153.quizzappdat153.viewmodel.ImageViewModel;

public class AddEntryActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText editTextName;
    private Uri imageUri;
    private ImageViewModel imageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        imageView = findViewById(R.id.image_preview);
        editTextName = findViewById(R.id.edit_text_name);
        imageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);

        Button buttonSelectImage = findViewById(R.id.button_choose_image);
        Button buttonSaveEntry = findViewById(R.id.button_add_entry);

        ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        imageView.setImageURI(imageUri);
                    }
                });

        buttonSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        });

        buttonSaveEntry.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            if (name.isEmpty() || imageUri == null) {
                Toast.makeText(this, "Please enter a name and select an image", Toast.LENGTH_SHORT).show();
                return;
            }

            Uri savedUri = saveImageFile(name, imageUri);
            if (savedUri != null) {
                imageViewModel.insert(new ImageEntity(name, savedUri.toString()));
                Toast.makeText(this, "Entry added", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Uri saveImageFile(String name, Uri sourceUri) {
        try {
            ContentResolver resolver = getContentResolver();
            InputStream inputStream = resolver.openInputStream(sourceUri);
            if (inputStream == null) return null;

            File directory = new File(getFilesDir(), "images");
            if (!directory.exists()) {
                directory.mkdir();
            }

            File imageFile = new File(directory, name + ".jpg");
            OutputStream outputStream = new FileOutputStream(imageFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            return Uri.fromFile(imageFile);
        } catch (Exception e) {
            Log.e("AddEntryActivity", "Error saving image file", e);
            return null;
        }
    }
}
