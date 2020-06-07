package com.blm.saytheirnames.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.adapters.FilterAdapter;
import com.blm.saytheirnames.adapters.HeaderCardRecyclerAdapter;
import com.blm.saytheirnames.adapters.PeopleAdapter;
import com.blm.saytheirnames.customTabs.CustomTabActivityHelper;
import com.blm.saytheirnames.customTabs.WebViewActivity;
import com.blm.saytheirnames.models.HeaderCard;
import com.blm.saytheirnames.models.People;
import com.blm.saytheirnames.models.PeopleData;
import com.blm.saytheirnames.network.BackendInterface;
import com.blm.saytheirnames.network.Utils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
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
    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManager1;

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    private PeopleAdapter peopleAdapter;
    private FilterAdapter filterAdapter;
    private HeaderCardRecyclerAdapter headerCardRecyclerAdapter;

    private List<People> peopleArrayList;
    private String[] filterList;
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

        peopleArrayList = new ArrayList<>();

        filterList = resources.getStringArray(R.array.location);

        personRecyclerView = mContent.findViewById(R.id.personRecyclerView);
        recyclerView = mContent.findViewById(R.id.recyclerView);
        progressBar = mContent.findViewById(R.id.progressBar);

        peopleAdapter = new PeopleAdapter(peopleArrayList, getActivity());
        filterAdapter = new FilterAdapter(filterList, getActivity());

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager1 = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager1);
        personRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        personRecyclerView.setAdapter(peopleAdapter);
        recyclerView.setAdapter(filterAdapter);
        setupHeaderCardViews();

        loadData();

        return mContent;
    }

    private void setupHeaderCardViews() {
        tabLayout = mContent.findViewById(R.id.header_tab_layout);
        viewPager = mContent.findViewById(R.id.header_view_pager);
        // TODO: update implementation
        headerCards = Arrays.asList(new HeaderCard(), new HeaderCard(), new HeaderCard(),
                new HeaderCard());
        headerCardRecyclerAdapter = new HeaderCardRecyclerAdapter(headerCards, this);

        viewPager.setAdapter(headerCardRecyclerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tabLayout.selectTab(tabLayout.getTabAt(position));
            viewPager.setCurrentItem(tab.getPosition(), true);
        }).attach();
    }

    private boolean validateUrl(String url) {
        return url != null && url.length() > 0 && (url.startsWith("http://") || url.startsWith("https://"));
    }

    private void openCustomChromeTab(Uri uri) {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = intentBuilder.build();

        intentBuilder.setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));

        CustomTabActivityHelper.openCustomTab(getContext(), customTabsIntent, uri, new CustomTabActivityHelper.CustomTabFallback() {
            @Override
            public void openUri(Context activity, Uri uri) {
                openWebView(uri);
            }
        });
    }

    private void openWebView(Uri uri) {
        Intent webViewIntent = new Intent(getContext(), WebViewActivity.class);
        webViewIntent.putExtra(WebViewActivity.EXTRA_URL, uri.toString());
        webViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(webViewIntent);
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

                        filterAdapter.notifyDataSetChanged();
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
        if (validateUrl("http://google.com")) {
            Uri uri = Uri.parse("http://google.com");
            if (uri != null) {
                openCustomChromeTab(uri);
            }
        } else {
            Toast.makeText(getContext(), "Error with link", Toast.LENGTH_SHORT).show();
        }
    }
}