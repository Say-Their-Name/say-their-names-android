package com.blm.saytheirnames.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.models.Person;

import java.util.List;

public class PersonsAdapter extends BaseAdapter {

    private List<Person> personList;
    private Context context;


    public PersonsAdapter(List<Person> personList, Context context) {
        super();
        this.personList = personList;
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item,null);

        TextView personName = convertView.findViewById(R.id.txtPersonName);
        TextView personAge = convertView.findViewById(R.id.txtPersonAge);
        ImageView personImage = convertView.findViewById(R.id.personImage);

        Person person = personList.get(position);

        personName.setText(person.getFullName());
        personAge.setText(String.valueOf(person.getAge()));


        return convertView;
    }
}
