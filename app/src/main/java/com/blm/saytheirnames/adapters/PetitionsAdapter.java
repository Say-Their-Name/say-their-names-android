package com.blm.saytheirnames.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.activity.DetailsActivity;
import com.blm.saytheirnames.activity.PetitionDetailsActivity;
import com.blm.saytheirnames.models.Petition;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class PetitionsAdapter extends RecyclerView.Adapter<PetitionsAdapter.PetitionItemHolder> {

    private List<Petition> petitionList;
    private Context context;


    public PetitionsAdapter(List<Petition> petitionList, Context context) {
        super();
        this.petitionList = petitionList;
        this.context = context;
        notifyDataSetChanged();
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
        Petition petition = petitionList.get(position);

        holder.txtTitle.setText(petition.getTitle());
        holder.txtDescription.setText(String.valueOf(petition.getDescription()));

        Glide.with(context)
                .load(petition.getImage_url())
               // .load(petition.getPerson().getImages().get(0).getImage_url())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm2)
                        .error(R.drawable.blm2))
                .into(holder.petitionImage);

        holder.btnFindOutMore.setOnClickListener(v -> {
            Intent intent = new Intent(context, PetitionDetailsActivity.class);
            intent.putExtra(PetitionDetailsActivity.EXTRA_ID, petition.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return petitionList.size();
    }

    class PetitionItemHolder extends RecyclerView.ViewHolder {
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
