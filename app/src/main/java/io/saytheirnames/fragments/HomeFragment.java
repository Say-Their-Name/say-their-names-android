package io.saytheirnames.fragments;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import io.saytheirnames.R;
import io.saytheirnames.activity.MainActivity;
import io.saytheirnames.adapters.FilterHomeAdapter;
import io.saytheirnames.adapters.HeaderCardRecyclerAdapter;
import io.saytheirnames.adapters.PeopleAdapter;
import io.saytheirnames.models.HomeFilter;
import io.saytheirnames.models.HeaderCard;
import io.saytheirnames.models.People;
import io.saytheirnames.models.PeopleData;
import io.saytheirnames.network.BackendInterface;
import io.saytheirnames.network.Utils;
import io.saytheirnames.utils.CustomTabUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements HeaderCardRecyclerAdapter.HeaderCardClickListener {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_COLOR = "arg_color";

    private String mText;
    private int mColor;

    private View mContent;
    //private TextView mTextView;

    private RecyclerView personRecyclerView;

    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManager1;

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    private PeopleAdapter peopleAdapter;
    private FilterHomeAdapter filterHomeAdapter;
    private HeaderCardRecyclerAdapter headerCardRecyclerAdapter;

    private List<People> peopleArrayList;
    private String[] filterList;
    private ArrayList<HomeFilter> filterArrayList;
    private List<HeaderCard> headerCards;
    private ProgressBar progressBar;

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
        peopleArrayList = new ArrayList<>();

        filterList = resources.getStringArray(R.array.filters);

        for (String filter : filterList) {
            filterArrayList.add(new HomeFilter(filter, false));
        }

        personRecyclerView = mContent.findViewById(R.id.personRecyclerView);
        progressBar = mContent.findViewById(R.id.progressBar);

        peopleAdapter = new PeopleAdapter(peopleArrayList, getActivity());
        filterHomeAdapter = new FilterHomeAdapter(filterArrayList, getActivity());

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager1 = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

        personRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        personRecyclerView.setAdapter(peopleAdapter);
        setupHeaderCardViews();

        loadData();

        return mContent;
    }

    private void setupHeaderCardViews() {
        tabLayout = mContent.findViewById(R.id.header_tab_layout);
        viewPager = mContent.findViewById(R.id.header_view_pager);
        // TODO: update implementation
        headerCards = Collections.singletonList(new HeaderCard());
        headerCardRecyclerAdapter = new HeaderCardRecyclerAdapter(headerCards, this);

        viewPager.setAdapter(headerCardRecyclerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tabLayout.selectTab(tabLayout.getTabAt(position));
            viewPager.setCurrentItem(tab.getPosition(), true);
        }).attach();
    }

    private void visitPage(String url) {
        CustomTabUtil.openCustomTabForUrl(getActivity(), url);
    }

    private void loadData() {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> getPeople = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                BackendInterface backendInterface = Utils.getBackendService();
                backendInterface.getPeople().enqueue(new Callback<PeopleData>() {
                    @Override
                    public void onResponse(@NonNull Call<PeopleData> call, @NonNull Response<PeopleData> response) {
                        peopleArrayList.clear();
                        Log.d("API_Response", response.body().toString());
                        List<People> body = response.body().getData();

                        peopleArrayList.addAll(body);
                        progressBar.setVisibility(View.GONE);
                        personRecyclerView.setVisibility(View.VISIBLE);

                        filterHomeAdapter.notifyDataSetChanged();
                        peopleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<PeopleData> call, Throwable t) {

                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
            }
        };
        getPeople.execute(null, null, null);
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
        if (bundle != null) {
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