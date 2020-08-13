package io.saytheirnames.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import io.saytheirnames.R;
import io.saytheirnames.activity.DonationDetailsActivity;
import io.saytheirnames.activity.PetitionDetailsActivity;
import io.saytheirnames.models.Donation;
import io.saytheirnames.network.DonationsPager;
import kotlinx.coroutines.Dispatchers;

public class DonationAdapter extends PagingDataAdapter<Donation,DonationAdapter.DonationItemHolder> {


    public DonationAdapter() {
        super(DonationsPager.Companion.getDiffItemCallback(), Dispatchers.getMain(),Dispatchers.getDefault());
    }

    @NonNull
    @Override
    public DonationAdapter.DonationItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_item, parent, false);


        return new DonationItemHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationAdapter.DonationItemHolder holder, final int position) {

        holder.setIsRecyclable(false);
        Donation donation = getItem(position);
        if (donation != null) {
            Glide.with(holder.itemView.getContext())
                    .load(donation.getBanner_img_url())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.blm)
                            .error(R.drawable.blm2))
                    .into(holder.donationImage);

            holder.txtTitle.setText(donation.getTitle());
            //if null, replace with empty string.
            String donationDescription = donation.getDescription() == null? "" : donation.getDescription();
            holder.txtDescription.setText(donationDescription);

            holder.donation_type.setText(donation.getType().getType());

            holder.findOutMore.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), DonationDetailsActivity.class);
                intent.putExtra(DonationDetailsActivity.EXTRA_ID, donation.getIdentifier());
                holder.itemView.getContext().startActivity(intent);
            });

        }
    }


    static class DonationItemHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtDescription;
        ImageView donationImage;
        Button findOutMore;
        Button donation_type;

        public DonationItemHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.donation_title);
            txtDescription = itemView.findViewById(R.id.donation_desc);
            donationImage = itemView.findViewById(R.id.donation_image);
            findOutMore = itemView.findViewById(R.id.know_more);
            donation_type = itemView.findViewById(R.id.donation_type);
        }
    }
}
