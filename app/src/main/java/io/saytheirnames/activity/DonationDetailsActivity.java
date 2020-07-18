package io.saytheirnames.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jgabrielfreitas.core.BlurImageView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.saytheirnames.R;
import io.saytheirnames.models.Donation;
import io.saytheirnames.utils.CustomTabUtil;
import io.saytheirnames.utils.ShareUtil;
import io.saytheirnames.viewmodels.DonationDetailsViewModel;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class DonationDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private String identifier, image, title, desc, donationLink;
    public static final String EXTRA_ID = "identifier";

    private BlurImageView blurImageView;
    private ImageView donationImage, close;
    private TextView donationTitle, subTitle, donationDesc, socialHashtags;
    private Button donationButton;
    private View progress;
    private Toolbar toolbar;

    private DonationDetailsViewModel mDonationDetailsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_details);

        bindViews();
        showProgress(true);
        identifier = getIntent().getStringExtra(EXTRA_ID);

        mDonationDetailsViewModel = new ViewModelProvider(this).get(DonationDetailsViewModel.class);
        mDonationDetailsViewModel.init();
        mDonationDetailsViewModel.searchDonationWithId(identifier);
        mDonationDetailsViewModel.getDonationDetails().observe(this, donation -> {
            if (donation != null) {
                showProgress(false);
                handleDonationData(donation);
            }
        });
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        blurImageView = findViewById(R.id.blurImageView);
        donationImage = findViewById(R.id.actual_image);
        donationButton = findViewById(R.id.btnDonate);
        donationTitle = findViewById(R.id.donation_title);
        subTitle = findViewById(R.id.sub_title);
        close = findViewById(R.id.close);
        donationDesc = findViewById(R.id.donation_desc);
        socialHashtags = findViewById(R.id.tv_social_hashtags);
        socialHashtags.setVisibility(View.GONE); // hiding view until endpoint has hashtags available
        progress = findViewById(R.id.progress);

        donationButton.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    private void handleDonationData(Donation donation) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Clear Glide's disk cache whenever an activity is destroyed
        Completable.fromAction(() -> {
            Glide.get(getApplicationContext()).clearDiskCache();
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
