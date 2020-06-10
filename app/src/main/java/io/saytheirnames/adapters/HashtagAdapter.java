package io.saytheirnames.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.saytheirnames.R;
import io.saytheirnames.models.Hashtag;

import java.util.List;

public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.HashtagViewHolder> {

    private HashtagListener listener;
    private List<Hashtag> hashtagList;

    public HashtagAdapter(HashtagListener listener, List<Hashtag> hashtagList) {
        this.listener = listener;
        this.hashtagList = hashtagList;
    }

    @NonNull
    @Override
    public HashtagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HashtagViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hashtag_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagViewHolder holder, int position) {
        Hashtag item = hashtagList.get(position);

        holder.button.setText(item.getTag());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onHashtagClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hashtagList.size();
    }

    public interface HashtagListener {
        public void onHashtagClick(Hashtag hashtag);
    }

    static class HashtagViewHolder extends RecyclerView.ViewHolder {

        Button button;

        public HashtagViewHolder(@NonNull View itemView) {
            super(itemView);

            button = itemView.findViewById(R.id.button);
        }
    }
}
