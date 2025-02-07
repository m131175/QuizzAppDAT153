package hvl.dat153.quizzappdat153.data;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hvl.dat153.quizzappdat153.R;

public class AppData {
    private static AppData instance;
    private final List<NamePhotoPair> namePhotoPairs;

    private AppData() {
        namePhotoPairs = new ArrayList<>();
        loadInitialData();
    }

    public static AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }

    public List<NamePhotoPair> getNamePhotoPairs() {
        return namePhotoPairs;
    }

    // Add entry using resource ID
    public void addEntry(String name, int photoResId) {
        namePhotoPairs.add(new NamePhotoPair(name, photoResId));
    }

    // Add entry using Bitmap
    public void addEntry(String name, Bitmap photoBitmap) {
        namePhotoPairs.add(new NamePhotoPair(name, photoBitmap));
    }

    public void sortEntries(boolean ascending) {
        if (ascending) {
            namePhotoPairs.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        } else {
            namePhotoPairs.sort((a, b) -> b.getName().compareToIgnoreCase(a.getName()));
        }
    }

    public List<String> getRandomOptions(String correctName) {
        List<String> options = new ArrayList<>();
        for (NamePhotoPair pair : namePhotoPairs) {
            options.add(pair.getName());
        }

        // Shuffle and ensure the correct answer is in the options
        Collections.shuffle(options);
        if (!options.contains(correctName)) {
            options.set(0, correctName);
        }
        return options.subList(0, 3);
    }

    private void loadInitialData() {
        namePhotoPairs.add(new NamePhotoPair("Cat", R.drawable.cat));
        namePhotoPairs.add(new NamePhotoPair("Dog", R.drawable.dog));
        namePhotoPairs.add(new NamePhotoPair("Rabbit", R.drawable.rabbit));
    }

    public void preloadSampleData() {
        namePhotoPairs.clear();
        namePhotoPairs.add(new NamePhotoPair("Cat", R.drawable.cat));
        namePhotoPairs.add(new NamePhotoPair("Dog", R.drawable.dog));
        namePhotoPairs.add(new NamePhotoPair("Rabbit", R.drawable.rabbit));
    }
}
