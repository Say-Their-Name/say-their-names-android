package com.blm.saytheirnames.adapters;

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

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.activity.DetailsActivity;
import com.blm.saytheirnames.models.Person;

import java.util.ArrayList;

public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.PersonItemHolder> {

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
    public PersonsAdapter.PersonItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);


        return new PersonItemHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonsAdapter.PersonItemHolder holder, final int position) {

        Person person = personList.get(position);

        holder.personName.setText(person.getFull_name());
        holder.personAge.setText(String.valueOf(person.getAge()));

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            context.startActivity(intent);
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

    class PersonItemHolder extends RecyclerView.ViewHolder {
        TextView personName;
        TextView personAge;
        ImageView personImage;
        CardView cardView;

        public PersonItemHolder(@NonNull View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.txtPersonName);
            personAge = itemView.findViewById(R.id.txtPersonAge);
            personImage = itemView.findViewById(R.id.personImage);
            cardView = itemView.findViewById(R.id.cardView);


        }
    }
}
