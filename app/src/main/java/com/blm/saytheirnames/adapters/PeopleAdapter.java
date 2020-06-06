package com.blm.saytheirnames.adapters;

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
import androidx.recyclerview.widget.RecyclerView;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.activity.DetailsActivity;
import com.blm.saytheirnames.models.People;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.FilterItemHolder> {
    private List<People> peopleList;
    private List<People> filteredPeopleList;
    private SimpleDateFormat dateOut;
    private SimpleDateFormat dateIn;

    private Context context;
    private int selected_item = 0;

    public PeopleAdapter(List<People> peopleList, Context context) {
        super();
        this.peopleList = peopleList;
        this.filteredPeopleList = peopleList;
        this.context = context;
        dateOut = new SimpleDateFormat("MM.dd.yyyy");
        dateIn = new SimpleDateFormat("yyyy-MM-dd");
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PeopleAdapter.FilterItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
        return new FilterItemHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleAdapter.FilterItemHolder holder, final int position) {
        People people = peopleList.get(position);

        holder.personName.setText(people.getFullName());


        try {
            Date parsedDate = dateIn.parse(people.getDateOfIncident());
            holder.dateOfIncident.setText(dateOut.format(parsedDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.personName.setText(people.getFullName());


        Glide.with(context)
                .load(people.getImages().get(0).getImage_url())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm2)
                        .error(R.drawable.blm2))
                .into(holder.personImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_ID, people.getIdentifier());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredPeopleList = peopleList;
                } else {
                    List<People> filteredList = new ArrayList<>();
                    for (People model : peopleList)
                        if (model.getCity().toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(model);

                    filteredPeopleList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredPeopleList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredPeopleList = (ArrayList<People>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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