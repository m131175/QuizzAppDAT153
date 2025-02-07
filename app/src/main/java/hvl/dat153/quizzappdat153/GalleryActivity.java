package hvl.dat153.quizzappdat153;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hvl.dat153.quizzappdat153.data.AppData;

public class GalleryActivity extends AppCompatActivity {

    private GalleryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Enable system back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Gallery");
        }

        // Initialize UI components
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Button buttonAddEntry = findViewById(R.id.button_add_entry);
        Button buttonSortAsc = findViewById(R.id.button_sort_asc);
        Button buttonSortDesc = findViewById(R.id.button_sort_desc);
        Button buttonBack = findViewById(R.id.button_back);

        // Set up RecyclerView and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GalleryAdapter(AppData.getInstance().getNamePhotoPairs());
        recyclerView.setAdapter(adapter);

        // Add Entry Button
        buttonAddEntry.setOnClickListener(v -> {
            Intent intent = new Intent(GalleryActivity.this, AddEntryActivity.class);
            startActivity(intent);
        });

        // Sorting Buttons
        buttonSortAsc.setOnClickListener(v -> {
            AppData.getInstance().sortEntries(true);
            adapter.notifyDataSetChanged();
        });

        buttonSortDesc.setOnClickListener(v -> {
            AppData.getInstance().sortEntries(false);
            adapter.notifyDataSetChanged();
        });

        // Back Button (Programmatic)
        buttonBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();  // Refresh the list when returning to the screen
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // Handle system back button in the action bar
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
