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
import com.blm.saytheirnames.models.Petition;

import java.util.ArrayList;

public class PetitionsAdapter extends RecyclerView.Adapter<PetitionsAdapter.PetitionItemHolder> {

    private ArrayList<Petition> personList;
    private Context context;
    private int selected_item = 0;


    public PetitionsAdapter(ArrayList<Petition> personList, Context context) {
        super();
        this.personList = personList;
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
        Petition person = personList.get(position);

        holder.txtTitle.setText(person.getTitle());
        holder.txtDescription.setText(String.valueOf(person.getDescription()));

        holder.cardView.setOnClickListener(v -> {
            //IMPLEMENT LINK OPENING
          System.out.println("Do in-browser or push to system browser");
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

    class PetitionItemHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtDescription;
        ImageView personImage;
        CardView cardView;

        public PetitionItemHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            personImage = itemView.findViewById(R.id.personImage);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
