package io.saytheirnames.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.saytheirnames.R;
import io.saytheirnames.adapters.HashtagAdapter;
import io.saytheirnames.adapters.HeaderCardRecyclerAdapter;
import io.saytheirnames.adapters.HomeHeaderAdapter;
import io.saytheirnames.adapters.MediaAdapter;
import io.saytheirnames.adapters.NewsAdapter;
import io.saytheirnames.adapters.PeopleAdapter;
import io.saytheirnames.adapters.PeopleSearchAdapter;
import io.saytheirnames.fragments.DonationFragment;
import io.saytheirnames.models.Hashtag;
import io.saytheirnames.models.Media;
import io.saytheirnames.models.News;
import io.saytheirnames.models.People;
import io.saytheirnames.models.PeopleData;
import io.saytheirnames.network.BackendInterface;
import io.saytheirnames.network.Utils;
import io.saytheirnames.utils.CustomTabUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeSearchActivity extends AppCompatActivity implements HeaderCardRecyclerAdapter.HeaderCardClickListener {

    public static final String EXTRA_ID = "identifier";

    private AppBarLayout appBarLayout;
    private LinearLayout linearLayout;
    private LinearLayout search_message;
    private LinearLayout search_no_record;
    private EditText search_text;
    private TextView cancelText;

    private MediaAdapter mediaAdapter;
    private NewsAdapter newsAdapter;
    private HashtagAdapter hashtagAdapter;

    private List<News> newsList;
    private List<Media> mediaList;
    private List<Hashtag> hashtagList;

    private ArrayList<People> peopleList;

    private String personID, baseUrl;

    PeopleData peopleData;

    BackendInterface backendInterface;
    ;

    private RecyclerView personRecyclerView;
    private ConcatAdapter mergeAdapter;

    private PeopleAdapter peopleAdapter;
    private PeopleSearchAdapter peopleSearchAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);

        personID = getIntent().getStringExtra(EXTRA_ID);
        peopleData = new PeopleData();

        bindViews();
        initializeRecyclerView();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onDestroy() {
        super.onDestroy();

        //clear Glide's disk cache whenever an activity is destroyed. Mechanism for helping against memory leaks/ Out of memory errors
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                // This method must be called on a background thread.
                Glide.get(getApplicationContext()).clearDiskCache();

                // DetailsActivity's richLinkViewer  (in the NewsAdapter) internally uses Picasso
                Picasso.get().shutdown();
                return null;
            }
        };
    }

    private void initializeBackend() {
        backendInterface = Utils.getBackendService();
    }

    private void bindViews() {
        personRecyclerView = findViewById(R.id.personRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        appBarLayout = findViewById(R.id.app_bar_home);
        search_message = findViewById(R.id.search_message);
        search_no_record = findViewById(R.id.search_no_record);
        linearLayout = appBarLayout.findViewById(R.id.linear);
        search_text = linearLayout.findViewById(R.id.search_text);
        cancelText = linearLayout.findViewById(R.id.cancel);

        peopleAdapter = new PeopleAdapter();
        peopleList = new ArrayList<>();
        peopleSearchAdapter = new PeopleSearchAdapter(peopleList, HomeSearchActivity.this);


        mergeAdapter = new ConcatAdapter(new HomeHeaderAdapter(this), peopleAdapter);


        cancelText.setOnClickListener(v -> {
            finish();
        });

        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initializeRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);

        /*
            This makes the first item span two columns (the black cards)
         */
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
                //return position == 0 ? 2 : 1;
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
        personRecyclerView.setAdapter(peopleSearchAdapter);
    }

    private void loadData(String s) {
        showProgress(true);
        showNoRecord(false);
        showSearchMessage(false);
        peopleList.clear();
        if (s.length() == 0) {
            showProgress(false);
            showNoRecord(false);
            showSearchMessage(true);
            peopleSearchAdapter.notifyDataSetChanged();
        } else {
            BackendInterface backendInterface = Utils.getBackendService();
            backendInterface.searchPeople(s).enqueue(new Callback<PeopleData>() {
                @Override
                public void onResponse(@NonNull Call<PeopleData> call, @NonNull Response<PeopleData> response) {
                    if (response.isSuccessful()) {
                        showProgress(false);
                        if (response.body() != null) {
                            if (response.body().getData().size() == 0) {
                                showNoRecord(true);
                                showSearchMessage(false);
                            } else {
                                showNoRecord(false);
                                showSearchMessage(false);
                                peopleList.addAll(response.body().getData());
                            }
                        }
                        peopleSearchAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<PeopleData> call, Throwable t) {
                    showProgress(false);
                    showNoRecord(false);
                    showSearchMessage(true);
                    onGetPersonFailure(t);
                }
            });
        }


    }

    private void onGetPersonFailure(Throwable throwable) {
        //TODO: Get a better message. This could be full of dev jargon.
        showSnackbar(throwable.getLocalizedMessage());
    }

    private void showSnackbar(String text) {
        //  Snackbar.make(toolbar, text, Snackbar.LENGTH_SHORT).show();
    }


    private void showProgress(Boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showSearchMessage(Boolean show) {
        search_message.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showNoRecord(Boolean show) {
        search_no_record.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onHeaderClick() {

        Fragment donationFragment = DonationFragment.newInstance();
        String tag = "DonationFragment";
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, donationFragment, tag);
        ft.commit();
        ((MainActivity) getBaseContext()).updateBottomNavBasedOnTag(tag);
    }


    private String nullCheck(Integer inputString) {
        if (inputString == null) {
            return "N/A";
        } else {
            return inputString.toString();
        }
    }

    private void navigateToUrl(String url) {
        CustomTabUtil.openCustomTabForUrl(this, url);
    }

}