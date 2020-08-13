package io.saytheirnames.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import io.saytheirnames.R;
import io.saytheirnames.activity.PetitionDetailsActivity;
import io.saytheirnames.models.Petition;
import io.saytheirnames.network.PetitionsPager;
import kotlinx.coroutines.Dispatchers;

public class PetitionsAdapter extends PagingDataAdapter<Petition,PetitionsAdapter.PetitionItemHolder> {


    public PetitionsAdapter() {
        super(PetitionsPager.Companion.getDiffItemCallback(), Dispatchers.getMain(),Dispatchers.getDefault());
    }

    @NonNull
    @Override
    public PetitionsAdapter.PetitionItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.petition_item, parent, false);


        return new PetitionItemHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PetitionsAdapter.PetitionItemHolder holder, final int position) {

        holder.setIsRecyclable(false);
        Petition petition = getItem(position);

        if (petition != null) {
            holder.txtTitle.setText(petition.getTitle());
            holder.txtDescription.setText(String.valueOf(petition.getDescription()));

            Glide.with(holder.itemView.getContext())
                    .load(petition.getBanner_img_url())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.blm2)
                            .error(R.drawable.blm2))
                    .into(holder.petitionImage);

            holder.btnFindOutMore.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), PetitionDetailsActivity.class);
                intent.putExtra(PetitionDetailsActivity.EXTRA_ID, petition.getIdentifier());
                holder.itemView.getContext().startActivity(intent);
            });
        }



    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    static class PetitionItemHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtDescription;
        ImageView petitionImage;
        Button btnFindOutMore;
        CardView cardView;

        public PetitionItemHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            petitionImage = itemView.findViewById(R.id.petitionImage);
            btnFindOutMore = itemView.findViewById(R.id.btnFindOutMore);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
