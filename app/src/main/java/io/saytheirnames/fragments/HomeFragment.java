package io.saytheirnames.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.saytheirnames.R;
import io.saytheirnames.activity.HomeSearchActivity;
import io.saytheirnames.activity.MainActivity;
import io.saytheirnames.adapters.FilterHomeAdapter;
import io.saytheirnames.adapters.HeaderCardRecyclerAdapter;
import io.saytheirnames.adapters.HomeHeaderAdapter;
import io.saytheirnames.adapters.PeopleAdapter;
import io.saytheirnames.models.HomeFilter;
import io.saytheirnames.network.PeoplePager;

public class HomeFragment extends Fragment implements HeaderCardRecyclerAdapter.HeaderCardClickListener {

    private View mContent;

    private RecyclerView personRecyclerView;
    private ConcatAdapter mergeAdapter;

    private PeopleAdapter peopleAdapter;
    private FilterHomeAdapter filterHomeAdapter;

    private String[] filterList;
    private ArrayList<HomeFilter> filterArrayList;
    private ProgressBar progressBar;

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private RelativeLayout relativeLayout;
    private ImageButton searchButton;

    Resources resources;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContent = inflater.inflate(R.layout.fragment_home, container, false);

        resources = getResources();

        filterArrayList = new ArrayList<>();

        filterList = resources.getStringArray(R.array.filters);

        for (String filter : filterList) {
            filterArrayList.add(new HomeFilter(filter, false));
        }

        personRecyclerView = mContent.findViewById(R.id.personRecyclerView);
        progressBar = mContent.findViewById(R.id.progressBar);
        appBarLayout = mContent.findViewById(R.id.app_bar_home);
        toolbar = appBarLayout.findViewById(R.id.toolbar);
        relativeLayout = toolbar.findViewById(R.id.relative_layout);
        searchButton = relativeLayout.findViewById(R.id.searchButton);


        peopleAdapter = new PeopleAdapter();

        mergeAdapter = new ConcatAdapter(new HomeHeaderAdapter(this), peopleAdapter);

        filterHomeAdapter = new FilterHomeAdapter(filterArrayList, getActivity());

        searchButton.setOnClickListener(v -> {
            doSearch();
        });

        initializeRecyclerView();
        loadData();

        return mContent;
    }

    private void doSearch() {
        Intent intent = new Intent(getActivity(), HomeSearchActivity.class);
        startActivity(intent);
    }

    private void initializeRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        /*
            This makes the first item span two columns (the black cards)
         */
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });

        /*
            This triggers the showing/hiding of the progress bar. This looks really hacky
            this way, though.
         */

        peopleAdapter.withLoadStateFooter(new LoadStateAdapter<RecyclerView.ViewHolder>() {
            @NotNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, @NotNull LoadState loadState) {
                return new RecyclerView.ViewHolder(progressBar) {
                };
            }

            @Override
            public void onBindViewHolder(@NotNull RecyclerView.ViewHolder viewHolder, @NotNull LoadState loadState) {
                if (loadState.equals(LoadState.Loading.INSTANCE)) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        personRecyclerView.setLayoutManager(gridLayoutManager);
        personRecyclerView.setAdapter(mergeAdapter);
    }

    private void loadData() {
        PeoplePager peoplePager = new PeoplePager(peopleAdapter);
        peoplePager.loadPeopleFromPagination();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize views
        mContent = view.findViewById(R.id.navigation_donation);
    }

    @Override
    public void onHeaderClick() {
        if (getActivity() != null) {
            Fragment donationFragment = DonationFragment.newInstance();
            String tag = "DonationFragment";
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, donationFragment, tag);
            ft.commit();
            ((MainActivity) getActivity()).updateBottomNavBasedOnTag(tag);
        }
    }
}