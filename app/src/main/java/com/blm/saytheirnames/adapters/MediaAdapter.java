package com.blm.saytheirnames.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.activity.DetailsActivity;
import com.blm.saytheirnames.models.Media;
import com.blm.saytheirnames.models.People;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.FilterItemHolder> {
    private List<Media> mediaList;

    private Context context;
    private int selected_item = 0;

    public MediaAdapter(List<Media> mediaList, Context context) {
        super();
        this.mediaList = mediaList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MediaAdapter.FilterItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_item, parent, false);
        return new FilterItemHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaAdapter.FilterItemHolder holder, final int position) {
        Media media = mediaList.get(position);


        Glide.with(context)
                .load(media.getUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm2)
                        .error(R.drawable.blm2))
                .into(holder.mediaUrl);


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


    static class FilterItemHolder extends RecyclerView.ViewHolder {
        ImageView mediaUrl;
        CardView cardView;

        public FilterItemHolder(@NonNull View itemView) {
            super(itemView);
            mediaUrl = itemView.findViewById(R.id.mediaUrl);;
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}