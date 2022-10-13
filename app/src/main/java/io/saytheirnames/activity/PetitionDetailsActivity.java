package io.saytheirnames.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import io.saytheirnames.models.Petition;
import io.saytheirnames.utils.CustomTabUtil;
import io.saytheirnames.utils.ShareUtil;
import io.saytheirnames.viewmodels.PetitionDetailsViewModel;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class PetitionDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_ID = "identifier";

    private ImageView imgClose, imgShare;
    private TextView txtName, txtDescription, txtShareThisPetition;
    private Button btnSignThisPetition;
    private ImageView actualImage;
    private BlurImageView blurImageView;
    private Toolbar toolbar;

    private String petitionID;
    private String petitionLink;

    private PetitionDetailsViewModel mPetitionDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petition_details);
        
        petitionID = getIntent().getStringExtra(EXTRA_ID);
        bindViews();

        mPetitionDetailsViewModel = new ViewModelProvider(this).get(PetitionDetailsViewModel.class);
        mPetitionDetailsViewModel.init();
        mPetitionDetailsViewModel.searchPetitionWithId(petitionID);

        mPetitionDetailsViewModel.getPerson().observe(this, petition -> {
            if (petition != null)
                handlePetitionData(petition);
        });

        toolbar.setTitle("");
        imgClose.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        txtShareThisPetition.setOnClickListener(this);
        btnSignThisPetition.setOnClickListener(this);
    }

    private void bindViews() {
        txtName = findViewById(R.id.txtName);
        txtDescription = findViewById(R.id.txtDescription);

        toolbar = findViewById(R.id.toolbar);
        imgClose = toolbar.findViewById(R.id.imgClose);
        imgShare = toolbar.findViewById(R.id.imgShare);

        actualImage = findViewById(R.id.actual_image);
        blurImageView = findViewById(R.id.blurImageView);
        txtShareThisPetition = findViewById(R.id.btnShareThisPetition);
        btnSignThisPetition = findViewById(R.id.btnSignThisPetition);

    }

    private void handlePetitionData(Petition petition) {
        txtName.setText(petition.getTitle());
        txtDescription.setText(petition.getDescription());
        petitionLink = petition.getLink();

        Glide.with(getApplicationContext())
                .load(petition.getBanner_img_url())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm2)
                        .error(R.drawable.blm2))
                .into(actualImage);

        Glide.with(getApplicationContext())
                .load(petition.getBanner_img_url())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm2)
                        .error(R.drawable.blm2))
                .apply(bitmapTransform(new BlurTransformation(22, 5)))
                .into(blurImageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignThisPetition:
                visitPage(petitionLink);
                break;
            case R.id.btnShareThisPetition:
            case R.id.imgShare:
                share(petitionLink);
                break;
            case R.id.imgClose:
                finish();
                break;
        }
    }

    private void visitPage(String url) {
        CustomTabUtil.openCustomTabForUrl(this, url);
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