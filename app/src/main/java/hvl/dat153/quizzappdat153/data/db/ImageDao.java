package hvl.dat153.quizzappdat153.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import hvl.dat153.quizzappdat153.data.model.ImageEntity;

@Dao
public interface ImageDao {
    @Insert
    void insert(ImageEntity image);

    @Query("SELECT * FROM images ORDER BY name ASC")
    LiveData<List<ImageEntity>> getAllImages();

    @Query("DELETE FROM images")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM images")
    int getImageCount();
}
