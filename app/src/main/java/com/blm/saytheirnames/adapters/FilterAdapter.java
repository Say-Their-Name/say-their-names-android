package com.blm.saytheirnames.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.models.Person;

import java.util.ArrayList;
import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterItemHolder> {

    private String[] filterList;
    private Context context;
    private int selected_item = 0;


    public FilterAdapter(String[] filterList, Context context) {
        super();
        this.filterList = filterList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilterAdapter.FilterItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item, parent, false);


        return new FilterItemHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterAdapter.FilterItemHolder holder, final int position) {

        if (position == selected_item) {
            holder.filterLocation.setBackgroundResource(R.drawable.selected_button_background);
            holder.filterLocation.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.filterLocation.setBackgroundResource(R.drawable.button_background);
            holder.filterLocation.setTextColor(Color.parseColor("#101010"));
        }

        holder.filterLocation.setText(filterList[position]);

        holder.filterLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_item = position;
                notifyDataSetChanged();
            }
        });
        /*holder.personName.setText(person.getFullName());
        holder.personName.setText(person.getFullName());
        holder.personAge.setText(String.valueOf(person.getAge()));*/
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return filterList.length;
    }

    class FilterItemHolder extends RecyclerView.ViewHolder {
        Button filterLocation;

        public FilterItemHolder(@NonNull View itemView) {
            super(itemView);
            filterLocation = itemView.findViewById(R.id.button);


        }
    }
}
