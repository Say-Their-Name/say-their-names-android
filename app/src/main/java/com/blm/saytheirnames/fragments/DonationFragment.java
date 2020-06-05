package com.blm.saytheirnames.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.activity.MainActivity;
import com.blm.saytheirnames.adapters.FilterAdapter;
import com.blm.saytheirnames.adapters.PersonsAdapter;
import com.blm.saytheirnames.models.Person;

import java.util.ArrayList;

public class DonationFragment extends Fragment {


    private RecyclerView personGridView;
    private RecyclerView recyclerView;
    private PersonsAdapter personsAdapter;
    private FilterAdapter filterAdapter;

    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManager1;

    private ArrayList<Person> personArrayList;
    private String[] filterList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donation, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {

        personArrayList = new ArrayList<>();

        personGridView = view.findViewById(R.id.personGridView);
        recyclerView = view.findViewById(R.id.recyclerView);

        loadData();
        filterList = view.getResources().getStringArray(R.array.tag);
        //setup adapter
        personsAdapter = new PersonsAdapter(personArrayList, getActivity());
        filterAdapter = new FilterAdapter(filterList, getActivity());

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager1 = new LinearLayoutManager(getActivity());
        //set orientation
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager1);
        personGridView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        personGridView.setAdapter(personsAdapter);
        recyclerView.setAdapter(filterAdapter);

        filterAdapter.notifyDataSetChanged();
        personsAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        personArrayList.clear();

        for (int i = 0; i < 10; i++) {
            Person person = new Person("1", "George Floyd", "2020-05-26", "he death of George Floyd occurred on May 25, 2020, when Derek Chauvin, a white Minneapolis police officer, kneeled on his neck for at least seven minutes", "03-23-1967", "Minnesota", "46", "Minnesota", "United States", "-", null);

            personArrayList.add(person);
        }
    }
}
