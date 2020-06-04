package com.blm.saytheirnames.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.adapters.FilterAdapter;
import com.blm.saytheirnames.adapters.PersonsAdapter;
import com.blm.saytheirnames.models.Person;

import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_COLOR = "arg_color";

    private String mText;
    private int mColor;

    private View mContent;
    //private TextView mTextView;

    private RecyclerView personGridView;
    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManager1;

    private PersonsAdapter personsAdapter;
    private FilterAdapter filterAdapter;

    private ArrayList<Person> personArrayList;
    private String[] filterList;

    Resources resources;

    Toolbar toolbar;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContent = inflater.inflate(R.layout.fragment_home, container, false);

        resources = getResources();

        personArrayList = new ArrayList<>();
       // filterList = new ArrayList<String[]>();

        toolbar = mContent.findViewById(R.id.toolbar);

        toolbar.setTitle("");
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        filterList = resources.getStringArray(R.array.location);

        personGridView = mContent.findViewById(R.id.personGridView);
        recyclerView = mContent.findViewById(R.id.recyclerView);

        personsAdapter = new PersonsAdapter(personArrayList,getActivity());
        filterAdapter = new FilterAdapter(filterList,getActivity());

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager1 = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager1);
        personGridView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        personGridView.setAdapter(personsAdapter);
        recyclerView.setAdapter(filterAdapter);

        loadData();

        return mContent;
    }


    private void loadData(){
        personArrayList.clear();


//        filterList.add(new String[]{"Location ", "gfhfghhg", "hgfgfhfhgfhg"});
        for(int i = 0; i < 10; i++){
            Person person = new Person("1","George Floyd","2020-05-26","he death of George Floyd occurred on May 25, 2020, when Derek Chauvin, a white Minneapolis police officer, kneeled on his neck for at least seven minutes","03-23-1967","Minnesota","46","Minnesota","United States","-",null);

            personArrayList.add(person);
        }

        filterAdapter.notifyDataSetChanged();
        personsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize views
        mContent = view.findViewById(R.id.navigation_donation);

        // retrieve text and color from bundle or savedInstanceState
        /*if (savedInstanceState == null) {
            Bundle args = getActivity().getIntent().getExtras();
            assert args != null;
            mText = args.getString(ARG_TEXT);
            //mColor = args.getInt(ARG_COLOR);
        } else {
            mText = savedInstanceState.getString(ARG_TEXT);
            // mColor = savedInstanceState.getInt(ARG_COLOR);
        }*/

        Bundle bundle = requireActivity().getIntent().getExtras();
        if (bundle != null)
        {
            //mTextView.setText(" "+bundle.getString("arg_text"));
        }

        // set text and background color
       // mTextView.setText(text);
        //mContent.setBackgroundColor(mColor);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TEXT, mText);
       // outState.putInt(ARG_COLOR, mColor);
        super.onSaveInstanceState(outState);
    }
}