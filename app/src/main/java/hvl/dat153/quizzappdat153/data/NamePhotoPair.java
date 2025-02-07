package hvl.dat153.quizzappdat153.data;

import android.graphics.Bitmap;

public class NamePhotoPair {
    private final String name;
    private final int photoResId;  // -1 means no resource ID
    private final Bitmap photoBitmap;

    public NamePhotoPair(String name, int photoResId) {
        this.name = name;
        this.photoResId = photoResId;
        this.photoBitmap = null;
    }

    public NamePhotoPair(String name, Bitmap photoBitmap) {
        this.name = name;
        this.photoResId = -1;
        this.photoBitmap = photoBitmap;
    }

    public String getName() {
        return name;
    }

    public int getPhotoResId() {
        return photoResId;
    }

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }

    public boolean hasBitmap() {
        return photoBitmap != null;
    }
}
