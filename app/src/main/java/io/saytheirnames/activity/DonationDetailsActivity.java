package io.saytheirnames.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import io.saytheirnames.R;
import io.saytheirnames.models.Donation;
import io.saytheirnames.models.DonationData;
import io.saytheirnames.network.BackendInterface;
import io.saytheirnames.network.Utils;
import io.saytheirnames.utils.CustomTabUtil;
import io.saytheirnames.utils.ShareUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.jgabrielfreitas.core.BlurImageView;
import com.squareup.picasso.Picasso;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class DonationDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_ID = "identifier";

    private String identifier;
    private String donationLink;

    private BlurImageView blurImageView;
    private TextView donationTitle;
    private TextView donationDesc;
    private TextView socialHashtags;

    private ImageView close;
    private ImageView donationImage;

    private View progress;
    private Toolbar toolbar;

    private Donation donation;

    private Button donationButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_details);

        getIntentExtra();
        setOnClickListeners();
        socialHashtags.setVisibility(View.GONE); // hiding view until endpoint has hastags available
        loadData();
    }

    private void getIntentExtra() {
        initView();

        Intent intent = getIntent();

        identifier = intent.getStringExtra(EXTRA_ID);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        blurImageView = findViewById(R.id.blurImageView);
        donationImage = findViewById(R.id.actual_image);
        donationButton = findViewById(R.id.btnDonate);
        donationTitle = findViewById(R.id.donation_title);
        close = findViewById(R.id.close);
        donationDesc = findViewById(R.id.donation_desc);
        socialHashtags = findViewById(R.id.tv_social_hashtags);
        progress = findViewById(R.id.progress);
    }

    private void setOnClickListeners() {
        donationButton.setOnClickListener(this);
        close.setOnClickListener(this);
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
                return null;
            }
        };
    }

    private void loadData() {
        showProgress(true);
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> getPerson = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                BackendInterface backendInterface = Utils.getBackendService();
                backendInterface.getDonationsById(identifier).enqueue(new Callback<DonationData>() {
                    @Override
                    public void onResponse(@NonNull Call<DonationData> call, @NonNull Response<DonationData> response) {
                        if (response.isSuccessful()) {
                            showProgress(false);
                            if (response.body() != null) {
                                donation = response.body().getData();
                            }


                            donationTitle.setText(donation.getTitle());
                            donationDesc.setText(donation.getDescription());

                            donationLink = donation.getLink();


                            Glide.with(getApplicationContext())
                                    .load(donation.getBanner_img_url())
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.blm2)
                                            .error(R.drawable.blm2))
                                    .into(donationImage);

                            Glide.with(getApplicationContext())
                                    .load(donation.getBanner_img_url())
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.blm2)
                                            .error(R.drawable.blm2))
                                    .apply(bitmapTransform(new BlurTransformation(22, 5)))
                                    .into(blurImageView);

                           /* Glide.with(getApplicationContext())
                                    .load(petition.getImage_url())
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.blm2)
                                            .error(R.drawable.blm2))
                                    .into(imageViewMore);*/
                        }
                    }

                    @Override
                    public void onFailure(Call<DonationData> call, Throwable t) {
                        showProgress(false);
                        onGetPersonFailure(t);
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
            }
        };
        getPerson.execute(null, null, null);
    }

    private void onGetPersonFailure(Throwable throwable) {
        //TODO: Get a better message. This could be full of dev jargon.
        showSnackbar(throwable.getLocalizedMessage());
    }

    private void showSnackbar(String text) {
        Snackbar.make(toolbar, text, Snackbar.LENGTH_SHORT).show();
    }

    private void visitPages(String link) {
        CustomTabUtil.openCustomTabForUrl(DonationDetailsActivity.this, link);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDonate:
                visitPages(donationLink);
                break;
            case R.id.close:
                finish();
                break;

            case R.id.imgShare:
                share(donationLink);
                break;
        }
    }

    private void showProgress(Boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void share(String url) {
        ShareUtil.shareBaseLink(this, url);
    }
}
