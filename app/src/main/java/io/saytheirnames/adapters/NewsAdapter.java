package io.saytheirnames.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import io.github.ponnamkarthik.richlinkpreview.RichLinkViewSkype;

import io.github.ponnamkarthik.richlinkpreview.ViewListener;
import io.saytheirnames.R;
import io.saytheirnames.models.News;

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

        //TODO: figure out how to handle cases where the holder's richLinkView could not fetch the preview from the url
        //right now, it just shows a blank shape, but the link still works
        News news = newsList.get(position);
        holder.richLinkView.setLink(news.getUrl(), new ViewListener() {
            @Override
            public void onSuccess(boolean status) {
            }
            @Override
            public void onError(Exception e) {
            }
        });

        //hyperlinking still works even if the preview wasnt fetched successfully
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
        CardView cardView;
        RichLinkViewSkype richLinkView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            richLinkView = itemView.findViewById(R.id.richLinkViewForNews);
        }
    }
}