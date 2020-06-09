package com.blm.saytheirnames.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.adapters.NewsAdapter;
import com.blm.saytheirnames.adapters.HashtagAdapter;
import com.blm.saytheirnames.models.News;
import com.blm.saytheirnames.models.Person;
import com.blm.saytheirnames.models.PersonData;
import com.blm.saytheirnames.models.Hashtag;
import com.blm.saytheirnames.network.BackendInterface;
import com.blm.saytheirnames.network.Utils;
import com.blm.saytheirnames.utils.CustomTabUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class DetailsActivity extends AppCompatActivity
        implements NewsAdapter.NewsListener, View.OnClickListener, HashtagAdapter.HashtagListener {

    public static final String EXTRA_ID = "identifier";

    private NewsAdapter newsAdapter;
    private HashtagAdapter hashtagAdapter;

    private List<News> newsList;
    private List<Hashtag> hashtagList;

    private String personID;

    Person person;

    BackendInterface backendInterface;

    private Toolbar toolbar;
    private FrameLayout heroContainer;
    private ImageView hero;
    private ImageView blurImageView;
    private TextView name;
    private TextView age;
    private TextView children;
    private TextView location;
    private TextView theirStory;
    private TextView outcome;
    private RecyclerView news;
    private RecyclerView media;
    private RecyclerView hashtags;
    private Button donate;

    private View progress;
    private View share;
    private Group outcomeGroup;
    private Group newsGroup;
    private Group mediaGroup;
    private Group hashtagGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        personID = getIntent().getStringExtra(EXTRA_ID);

        bindViews();
        initializeBackend();
        renderMedia();
        renderSocialMedia();
        renderData();
    }

    private void initializeBackend() {
        backendInterface = Utils.getBackendService();
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        heroContainer = findViewById(R.id.hero_container);
        hero = findViewById(R.id.hero);
        blurImageView = findViewById(R.id.blurImageView);
        name = findViewById(R.id.full_name);
        age = findViewById(R.id.age);
        children = findViewById(R.id.children);
        location = findViewById(R.id.location);
        theirStory = findViewById(R.id.story);
        outcome = findViewById(R.id.outcome);
        news = findViewById(R.id.news);
        media = findViewById(R.id.media);
        hashtags = findViewById(R.id.hashtags);
        donate = findViewById(R.id.donate);
        progress = findViewById(R.id.progress);
        share = findViewById(R.id.share);

        outcomeGroup = findViewById(R.id.outcome_group);
        newsGroup = findViewById(R.id.news_group);
        mediaGroup = findViewById(R.id.media_group);
        hashtagGroup = findViewById(R.id.hashtags_group);

        donate.setOnClickListener(this);
        share.setOnClickListener(this);
    }

    private void renderMedia() {
        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, newsList);
        news.setAdapter(newsAdapter);
    }

    private void renderSocialMedia() {
        hashtagList = new ArrayList<>();
        hashtagAdapter = new HashtagAdapter(this, hashtagList);
        hashtags.setAdapter(hashtagAdapter);
    }

    @Override
    public void onNewsSelected(News news) {
        navigateToUrl(news.getUrl());
    }

    @Override
    public void onHashtagClick(Hashtag hashtag) {
        navigateToUrl(hashtag.getLink());
    }

    private void renderData() {
        showProgress(true);
        backendInterface.getPeopleById(personID).enqueue(new Callback<PersonData>() {
            @Override
            public void onResponse(Call<PersonData> call, Response<PersonData> response) {
                if (response.isSuccessful()) {
                    onGetPersonSuccess(response.body().getData());
                } else {
                    onGetPersonFailure(new Throwable(response.message()));
                }
                showProgress(false);
            }

            @Override
            public void onFailure(Call<PersonData> call, Throwable throwable) {
                onGetPersonFailure(throwable);
                showProgress(false);
            }
        });
    }

    private void onGetPersonSuccess(Person person) {
        this.person = person;

        if (person.getNews() != null && person.getNews().size() > 0) {
            newsList.clear();
            newsList.addAll(person.getNews());
            newsAdapter.notifyDataSetChanged();
            newsGroup.setVisibility(View.VISIBLE);
        } else {
            newsGroup.setVisibility(View.GONE);
        }

        //TODO: Add Media adapter
        mediaGroup.setVisibility(View.GONE);

        if (person.getHashtags() != null && person.getHashtags().size() > 0) {
            hashtagList.clear();
            hashtagList.addAll(person.getHashtags());
            hashtagAdapter.notifyDataSetChanged();
            hashtagGroup.setVisibility(View.VISIBLE);
        } else {
            hashtagGroup.setVisibility(View.GONE);
        }

        name.setText(person.getFullName());
        age.setText(person.getAge().toString());
        children.setText(nullCheck(person.getNumberOfChildren()));
        location.setText(person.getCity());
        theirStory.setText(person.getTheirStory());

        if (person.getOutcome() == null) {
            outcomeGroup.setVisibility(View.GONE);
        } else {
            outcomeGroup.setVisibility(View.VISIBLE);
            outcome.setText(person.getOutcome());
        }

        Glide.with(getApplicationContext())
                .load(person.getImages().get(0).getImage_url())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm2)
                        .error(R.drawable.blm2))
                .into(hero);

        Glide.with(getApplicationContext())
                .load(person.getImages().get(0).getImage_url())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm2)
                        .error(R.drawable.blm2))
                .apply(bitmapTransform(new BlurTransformation(22, 5)))
                .into(blurImageView);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.donate:
                if (person.getDonationLinks().size() > 0) {
                    //TODO: For now, just get the first one. We'll improve on this and present multiple options later.
                    navigateToUrl(person.getDonationLinks().get(0).getLink());
                }
                break;
            case R.id.share:
                //TODO: What are we sharing?
                showSnackbar("TODO: Share.");
        }
    }

    private void onGetPersonFailure(Throwable throwable) {
        //TODO: Get a better message. This could be full of dev jargon.
        showSnackbar(throwable.getLocalizedMessage());
    }

    private void showSnackbar(String text) {
        Snackbar.make(toolbar, text, Snackbar.LENGTH_SHORT).show();
    }

    private String nullCheck(Integer inputString) {
        if (inputString == null) {
            return "N/A";
        } else {
            return inputString.toString();
        }
    }

    private void showProgress(Boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void navigateToUrl(String url) {
        CustomTabUtil.openCustomTabForUrl(this, url);
    }

}