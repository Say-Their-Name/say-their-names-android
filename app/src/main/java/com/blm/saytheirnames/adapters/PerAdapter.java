package com.blm.saytheirnames.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.models.Person;

import java.util.ArrayList;

public class PerAdapter extends RecyclerView.Adapter<PerAdapter.FilterItemHolder> {

    private ArrayList<Person> personList;
    private Context context;
    private int selected_item = 0;


    public PerAdapter(ArrayList<Person> personList, Context context) {
        super();
        this.personList = personList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PerAdapter.FilterItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);


        return new FilterItemHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PerAdapter.FilterItemHolder holder, final int position) {

        Person person = personList.get(position);

        holder.personName.setText(person.getFull_name());
        holder.personAge.setText(String.valueOf(person.getAge()));
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
        TextView personAge;
        ImageView personImage;

        public FilterItemHolder(@NonNull View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.txtPersonName);
            personAge = itemView.findViewById(R.id.txtPersonAge);
            personImage = itemView.findViewById(R.id.personImage);


        }
    }
}
