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

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class DonationDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    String identifier,image, title, desc, donationLink;

    private BlurImageView blurImageView;
    private ImageView donationImage,close;
    private TextView donationTitle, subTitle, donationDesc, socialHashtags;
    private Button donationButton;
    private View progress;
    private Toolbar toolbar;

    Donation donation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_details);


        initView();

        Intent intent = getIntent();

        identifier = intent.getStringExtra("identifier");

       loadData();
    }

    void initView() {
        toolbar = findViewById(R.id.toolbar);
        blurImageView = findViewById(R.id.blurImageView);
        donationImage = findViewById(R.id.actual_image);
        donationButton = findViewById(R.id.btnDonate);
        donationTitle = findViewById(R.id.donation_title);
        subTitle = findViewById(R.id.sub_title);
        close = findViewById(R.id.close);
        donationDesc = findViewById(R.id.donation_desc);
        socialHashtags = findViewById(R.id.tv_social_hashtags);
        socialHashtags.setVisibility(View.GONE); // hiding view until endpoint has hastags available
        progress = findViewById(R.id.progress);

        donationButton.setOnClickListener(this);
        close.setOnClickListener(this);
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
