package io.saytheirnames.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.saytheirnames.R;
import io.saytheirnames.activity.DetailsActivity;
import io.saytheirnames.models.People;
import io.saytheirnames.models.PeopleData;
import io.saytheirnames.models.Person;

public class PeopleSearchAdapter extends RecyclerView.Adapter<PeopleSearchAdapter.FilterItemHolder> {

    private List<People>  peopleList;
    private Context context;


    public PeopleSearchAdapter(List<People> peopleList, Context context) {
        super();
        this.peopleList = peopleList;
        this.context = context;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PeopleSearchAdapter.FilterItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_search_item, parent, false);


        return new FilterItemHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleSearchAdapter.FilterItemHolder holder, final int position) {

        People people = peopleList.get(position);

        holder.personName.setText(people.getFullName());


        holder.cardView.setOnClickListener(v -> {
            ((Activity)context).finish();
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_ID, people.getIdentifier());
            holder.itemView.getContext().startActivity(intent);
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

    static class FilterItemHolder extends RecyclerView.ViewHolder {
        TextView personName;
        CardView cardView;

        public FilterItemHolder(@NonNull View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.txtPersonName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
