package hvl.dat153.quizzappdat153.data;

import android.graphics.Bitmap;

public abstract class NamePhotoPair {
    private final String name;


    public abstract boolean hasBitmap();
    public NamePhotoPair(String name) {
        this.name = name;
    }

    public static class ResourcePhoto extends NamePhotoPair {
        private int photoResId;  // -1 means no resource ID
        public ResourcePhoto(String name, int photoResId) {
            super(name);
            this.photoResId = photoResId;
        }
        public int getPhotoResId() {
            return photoResId;
        }

        public boolean hasBitmap() {
            return false;
        }
    }

    public static class BitmapPhoto extends NamePhotoPair {

        private Bitmap photoBitmap;

        public BitmapPhoto(String name, Bitmap photoBitmap) {
            super(name);
            this.photoBitmap = photoBitmap;
        }
        public boolean hasBitmap() {
            return true;
        }
        public Bitmap getPhotoBitmap() {
            return photoBitmap;
        }
    }


    public String getName() {
        return name;
    }

}
