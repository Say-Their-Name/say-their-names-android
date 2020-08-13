package io.saytheirnames.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.saytheirnames.R;
import io.saytheirnames.models.HeaderCard;

import java.util.List;

// TODO: update implementation when api endpoint is ready to consume
public class HeaderCardRecyclerAdapter extends RecyclerView.Adapter<HeaderCardRecyclerAdapter.HeaderCardItemHolder> {

    HeaderCardClickListener clickListener;
    List<HeaderCard> headers;

    public HeaderCardRecyclerAdapter(List<HeaderCard> headers, HeaderCardClickListener clickListener) {
        this.headers = headers;
        this.clickListener = clickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HeaderCardItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_card_item, parent, false);
        return new HeaderCardItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderCardItemHolder holder, int position) {
        HeaderCard headerCard = headers.get(position);
        holder.bind(headerCard);
    }

    @Override
    public int getItemCount() {
        return headers.size();
    }

    class HeaderCardItemHolder extends RecyclerView.ViewHolder {
        TextView title, description, donate;

        public HeaderCardItemHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            donate = itemView.findViewById(R.id.tv_donate);
        }

        public void bind(HeaderCard headerCard) {
            //TODO: left commented since placeholder text is set in xml file, bind data once available
            /*title.setText(headerCard.getTitle());
            description.setText(headerCard.getDescription());
            donate.setText(headerCard.getHowToDonate());*/
            donate.setOnClickListener(view -> {
                        clickListener.onHeaderClick();
                        System.out.println("Donate now clicked!");
            });
        }
    }

    public interface HeaderCardClickListener {
        void onHeaderClick();
    }
}
