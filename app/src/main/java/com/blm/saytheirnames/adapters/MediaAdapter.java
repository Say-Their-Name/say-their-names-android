package com.blm.saytheirnames.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.models.Media;
import com.blm.saytheirnames.models.News;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.NewsViewHolder> {

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
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        //TODO: This should support URL previews at some point. I believe there are libraries out there that can do that for us.

        Media media = mediaList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(media.getImage_url())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm2)
                        .error(R.drawable.blm2))
                .into(holder.newsUrl);

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

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsUrl;
        CardView cardView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsUrl = itemView.findViewById(R.id.news);;
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}