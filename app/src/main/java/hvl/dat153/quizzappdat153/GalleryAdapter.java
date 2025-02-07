package hvl.dat153.quizzappdat153;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import hvl.dat153.quizzappdat153.data.NamePhotoPair;

class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private final List<NamePhotoPair> namePhotoPairs;

    public GalleryAdapter(List<NamePhotoPair> namePhotoPairs) {
        this.namePhotoPairs = namePhotoPairs;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        NamePhotoPair pair = namePhotoPairs.get(position);

        if (pair != null) {
            holder.textName.setText(pair.getName());
            holder.imagePhoto.setImageResource(pair.getPhotoResId());
        } else {
            // Prevent crash by skipping invalid entries if they somehow exist
            holder.textName.setText(holder.itemView.getContext().getString(R.string.unknown_text));
            holder.imagePhoto.setVisibility(View.GONE);  // Hide the image if the data is invalid
        }
    }

    @Override
    public int getItemCount() {
        return namePhotoPairs.size();
    }

    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        final TextView textName;
        final ImageView imagePhoto;

        GalleryViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            imagePhoto = itemView.findViewById(R.id.image_photo);
        }
    }
}
