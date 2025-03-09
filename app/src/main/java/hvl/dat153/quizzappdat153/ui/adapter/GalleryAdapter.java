package hvl.dat153.quizzappdat153.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hvl.dat153.quizzappdat153.R;
import hvl.dat153.quizzappdat153.data.model.ImageEntity;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageViewHolder> {

    private final Context context;
    private List<ImageEntity> images = new ArrayList<>();

    public GalleryAdapter(Context context) {
        this.context = context;
    }

    public void setImages(List<ImageEntity> newImages) {
        this.images = new ArrayList<>(newImages);
        notifyDataSetChanged();
    }

    public void sortImages(boolean ascending) {
        if (images.isEmpty()) return;
        images.sort((a, b) -> ascending ? a.getName().compareToIgnoreCase(b.getName())
                : b.getName().compareToIgnoreCase(a.getName()));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageEntity image = images.get(position);
        holder.imageName.setText(image.getName());
        holder.imageView.setImageURI(Uri.parse(image.getImageUri()));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView imageName;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            imageName = itemView.findViewById(R.id.image_name);
        }
    }
}
