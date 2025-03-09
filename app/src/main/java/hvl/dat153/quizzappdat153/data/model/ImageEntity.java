package hvl.dat153.quizzappdat153.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "images")
public class ImageEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String imageUri;

    public ImageEntity(String name, String imageUri) {
        this.name = name;
        this.imageUri = imageUri;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUri() {
        return imageUri;
    }
}
