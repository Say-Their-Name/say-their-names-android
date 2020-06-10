package com.blm.saytheirnames.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.activity.DonationDetailsActivity;
import com.blm.saytheirnames.models.Donation;
import com.blm.saytheirnames.models.Petition;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationItemHolder> {

    private List<Donation> donationList;
    private Context context;
    private int selected_item = 0;
    private OnItemClickListener mListener;


    public DonationAdapter(List<Donation> donationList, Context context) {
        super();
        this.donationList = donationList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonationAdapter.DonationItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_item, parent, false);


        return new DonationAdapter.DonationItemHolder(convertView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationAdapter.DonationItemHolder holder, final int position) {

        holder.setIsRecyclable(false);
        Donation donation = donationList.get(position);
        Glide.with(context)
                .load(donation.getOutcome_img_url())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm)
                        .error(R.drawable.blm2))
                .into(holder.donationImage);
        holder.txtTitle.setText(donation.getTitle());
        holder.txtDescription.setText(String.valueOf(donation.getDescription()));
    }


    public void setOnItemClickListener(DonationAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void knowMore(int position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return donationList.size();
    }

    class DonationItemHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtDescription;
        ImageView donationImage;
        Button findOutMore;

        public DonationItemHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.donation_title);
            txtDescription = itemView.findViewById(R.id.donation_desc);
            donationImage = itemView.findViewById(R.id.donation_image);
            findOutMore = itemView.findViewById(R.id.know_more);

            findOutMore.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.knowMore(position);
                    }
                }
            });
        }
    }
}
