package hvl.dat153.quizzappdat153.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import hvl.dat153.quizzappdat153.data.db.AppDatabase;
import hvl.dat153.quizzappdat153.data.db.ImageDao;
import hvl.dat153.quizzappdat153.data.model.ImageEntity;

public class ImageViewModel extends AndroidViewModel {
    private final ImageDao imageDao;
    private final LiveData<List<ImageEntity>> allImages;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ImageViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        imageDao = database.imageDao();
        allImages = imageDao.getAllImages();
    }

    public LiveData<List<ImageEntity>> getAllImages() {
        return allImages;
    }

    public void insert(ImageEntity image) {
        executorService.execute(() -> imageDao.insert(image));
    }
}
