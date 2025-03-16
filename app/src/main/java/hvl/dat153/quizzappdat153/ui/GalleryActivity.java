package hvl.dat153.quizzappdat153.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.espresso.idling.CountingIdlingResource;

import java.util.ArrayList;
import java.util.List;
import hvl.dat153.quizzappdat153.R;
import hvl.dat153.quizzappdat153.data.model.ImageEntity;
import hvl.dat153.quizzappdat153.viewmodel.ImageViewModel;

public class GalleryActivity extends AppCompatActivity {

    private ImageViewModel imageViewModel;
    private LinearLayout imageContainer;
    private final CountingIdlingResource idlingResource = new CountingIdlingResource("GalleryLoad");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        imageContainer = findViewById(R.id.image_container);

        imageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        imageViewModel.getAllImages().observe(this, images -> {
            if (images != null) {
                updateGallery(images);
            }
        });

        Button buttonAddEntry = findViewById(R.id.button_add_entry);
        Button buttonSortAsc = findViewById(R.id.button_sort_asc);
        Button buttonSortDesc = findViewById(R.id.button_sort_desc);
        Button buttonBack = findViewById(R.id.button_back);

        buttonAddEntry.setOnClickListener(v -> startActivity(new Intent(this, AddEntryActivity.class)));
        buttonSortAsc.setOnClickListener(v -> sortImages(true));
        buttonSortDesc.setOnClickListener(v -> sortImages(false));
        buttonBack.setOnClickListener(v -> finish());
    }

    private void updateGallery(@NonNull List<ImageEntity> images) {
        idlingResource.increment();

        imageContainer.removeAllViews();

        new Handler(Looper.getMainLooper()).post(() -> {
            LinearLayout rowLayout = null;
            for (int i = 0; i < images.size(); i++) {
                if (i % 2 == 0) {
                    rowLayout = new LinearLayout(this);
                    rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                    rowLayout.setGravity(Gravity.CENTER);
                    imageContainer.addView(rowLayout);
                }

                View itemView = getLayoutInflater().inflate(R.layout.item_gallery, rowLayout, false);
                ImageView imageView = itemView.findViewById(R.id.image_view);
                TextView imageName = itemView.findViewById(R.id.image_name);

                ImageEntity image = images.get(i);
                imageName.setText(image.getName());
                imageView.setImageURI(Uri.parse(image.getImageUri()));

                rowLayout.addView(itemView);
            }

            idlingResource.decrement();
        });
    }




    private void sortImages(boolean ascending) {
        imageViewModel.getAllImages().observe(this, images -> {
            if (images != null && !images.isEmpty()) {
                List<ImageEntity> sortedList = new ArrayList<>(images);
                sortedList.sort((a, b) -> ascending ? a.getName().compareToIgnoreCase(b.getName())
                        : b.getName().compareToIgnoreCase(a.getName()));
                updateGallery(sortedList);
            }
        });
    }

    public CountingIdlingResource getIdlingResource() {
        return idlingResource;
    }
}
