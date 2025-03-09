package hvl.dat153.quizzappdat153.data.db;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import hvl.dat153.quizzappdat153.R;
import hvl.dat153.quizzappdat153.data.model.ImageEntity;

@Database(entities = {ImageEntity.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ImageDao imageDao();
    private static volatile AppDatabase INSTANCE;
    private static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "image_database")
                            .fallbackToDestructiveMigration()
                            .setJournalMode(JournalMode.TRUNCATE)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    databaseExecutor.execute(() -> preloadData(context));
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static void preloadData(final Context context) {
        ImageDao dao = INSTANCE.imageDao();
        if (dao.getImageCount() == 0) {
            databaseExecutor.execute(() -> {
                dao.insert(new ImageEntity("Cat", getResourceUri(context, R.drawable.cat)));
                dao.insert(new ImageEntity("Dog", getResourceUri(context, R.drawable.dog)));
                dao.insert(new ImageEntity("Rabbit", getResourceUri(context, R.drawable.rabbit)));
            });
        }
    }

    private static String getResourceUri(Context context, int drawableId) {
        return "android.resource://" + context.getPackageName() + "/" + drawableId;
    }
}
