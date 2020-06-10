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
import com.blm.saytheirnames.models.News;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private NewsListener listener;
    private List<News> newsList;

    public NewsAdapter(NewsListener listener, List<News> newsList) {
        super();
        this.listener = listener;
        this.newsList = newsList;
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

        News news = newsList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(news.getUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm2)
                        .error(R.drawable.blm2))
                .into(holder.newsUrl);

        holder.itemView.setOnClickListener(v -> listener.onNewsSelected(news));


        Log.d("IASD:::", String.valueOf(newsList.size()));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public interface NewsListener {
        void onNewsSelected(News news);
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