package io.saytheirnames.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import io.saytheirnames.R;
import io.saytheirnames.activity.DetailsActivity;
import io.saytheirnames.models.Person;

import java.util.ArrayList;

public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.FilterItemHolder> {

    private ArrayList<Person> personList;
    private Context context;
    private int selected_item = 0;


    public PersonsAdapter(ArrayList<Person> personList, Context context) {
        super();
        this.personList = personList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PersonsAdapter.FilterItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);


        return new FilterItemHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonsAdapter.FilterItemHolder holder, final int position) {

        Person person = personList.get(position);

        holder.personName.setText(person.getFullName());
        holder.personDateOfIncident.setText(String.valueOf(person.getDateOfIncident()));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
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
        return personList.size();
    }

    class FilterItemHolder extends RecyclerView.ViewHolder {
        TextView personName;
        TextView personDateOfIncident;
        ImageView personImage;
        CardView cardView;

        public FilterItemHolder(@NonNull View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.txtPersonName);
            personDateOfIncident = itemView.findViewById(R.id.dateOfIncident);
            personImage = itemView.findViewById(R.id.personImage);
            cardView = itemView.findViewById(R.id.cardView);


        }
    }
}
