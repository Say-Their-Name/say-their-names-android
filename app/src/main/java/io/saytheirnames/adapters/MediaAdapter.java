package io.saytheirnames.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import io.saytheirnames.R;
import io.saytheirnames.models.Media;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {

    private MediaListener listener;
    private List<Media> mediaList;

    public MediaAdapter(MediaListener listener, List<Media> mediaList) {
        super();
        this.listener = listener;
        this.mediaList = mediaList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_item, parent, false);
        return new MediaViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, final int position) {

        Media media = mediaList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(media.getImage_url())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm2)
                        .error(R.drawable.blm2))
                .into(holder.mediaImage);

        holder.itemView.setOnClickListener(v -> listener.onMediaSelected(media));


        Log.d("IASD:::", String.valueOf(mediaList.size()));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public interface MediaListener {
        void onMediaSelected(Media media);
    }

    static class MediaViewHolder extends RecyclerView.ViewHolder {
        ImageView mediaImage;
        CardView cardView;

        public MediaViewHolder(@NonNull View itemView) {
            super(itemView);
            mediaImage = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}