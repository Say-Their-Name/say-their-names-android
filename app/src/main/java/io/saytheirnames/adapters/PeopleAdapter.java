package io.saytheirnames.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import io.saytheirnames.R;
import io.saytheirnames.activity.DetailsActivity;
import io.saytheirnames.models.People;
import io.saytheirnames.network.PeoplePager;
import kotlinx.coroutines.Dispatchers;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class PeopleAdapter extends PagingDataAdapter<People, PeopleAdapter.FilterItemHolder> {

    private SimpleDateFormat dateOut;
    private SimpleDateFormat dateIn;

    public PeopleAdapter() {
        super(PeoplePager.Companion.getDiffItemCallback(), Dispatchers.getMain(),
                Dispatchers.getDefault());

        dateOut = new SimpleDateFormat("MM.dd.yyyy");
        dateIn = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public PeopleAdapter.FilterItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
        return new FilterItemHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleAdapter.FilterItemHolder holder, final int position) {
        People people = getItem(position);

        holder.personName.setText(people.getFullName());


        try {
            Date parsedDate = dateIn.parse(people.getDateOfIncident());
            holder.dateOfIncident.setText(dateOut.format(parsedDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.personName.setText(people.getFullName());

        Glide.with(holder.itemView.getContext())
                .load(people.getImages().get(0).getImage_url())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm2)
                        .error(R.drawable.blm2))
                .into(holder.personImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_ID, people.getIdentifier());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    static class FilterItemHolder extends RecyclerView.ViewHolder {
        TextView personName;
        TextView dateOfIncident;
        ImageView personImage;
        CardView cardView;

        public FilterItemHolder(@NonNull View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.txtPersonName);
            dateOfIncident = itemView.findViewById(R.id.dateOfIncident);
            personImage = itemView.findViewById(R.id.personImage);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

}