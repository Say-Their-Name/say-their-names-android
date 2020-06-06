package com.blm.saytheirnames.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.adapters.MediaAdapter;
import com.blm.saytheirnames.models.Donation;
import com.blm.saytheirnames.models.Media;
import com.blm.saytheirnames.models.Person;
import com.blm.saytheirnames.models.PersonData;
import com.blm.saytheirnames.network.BackendInterface;
import com.blm.saytheirnames.network.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.jgabrielfreitas.core.BlurImageView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class DetailsActivity extends AppCompatActivity implements MediaAdapter.MediaListener, View.OnClickListener {

    public static final String EXTRA_ID = "identifier";

    private MediaAdapter mediaAdapter;

    private List<Media> mediaList;

    private String personID;

    Person person;

    BackendInterface backendInterface;

    private Toolbar toolbar;
    private FrameLayout heroContainer;
    private ImageView hero;
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
        renderData();
    }

    private void initializeBackend() {
        backendInterface = Utils.getBackendService();
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        heroContainer = findViewById(R.id.hero_container);
        hero = findViewById(R.id.hero);
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
        mediaList = new ArrayList<>();
        mediaAdapter = new MediaAdapter(this, mediaList);
        media.setAdapter(mediaAdapter);
    }

    @Override
    public void onMediaSelected(Media media) {
        navigateToUrl(media.getUrl());
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

        mediaList.addAll(person.getMediaLinks());
        mediaAdapter.notifyDataSetChanged();

        //TODO: Make adapter for News
        newsGroup.setVisibility(View.GONE);

        //TODO: Make adapter for Hashtags
        hashtagGroup.setVisibility(View.GONE);

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

        //TODO: We may need to do extra things for the hero container if we want to match the mocks more accurately.

        Glide.with(getApplicationContext())
                .load(person.getImages().get(0).getImage_url())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm2)
                        .error(R.drawable.blm2))
                .into(hero);

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
        Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.trim()));
        startActivity(urlIntent);
    }

}