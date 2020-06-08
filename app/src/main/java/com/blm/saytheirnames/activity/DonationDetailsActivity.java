package com.blm.saytheirnames.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.models.Donation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jgabrielfreitas.core.BlurImageView;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class DonationDetailsActivity extends AppCompatActivity {

    String image, title, desc;

    BlurImageView blurImageView;
    ImageView donationImage, close;
    TextView donationTitle, subTitle, donationDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_details);


        initView();

        Intent intent = getIntent();

        image = intent.getStringExtra("image");
        title = intent.getStringExtra("title");
        desc = intent.getStringExtra("desc");

        Glide.with(this)
                .load(image)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm2)
                        .error(R.drawable.blm2))
                .apply(bitmapTransform(new BlurTransformation(22, 5)))
                .into(blurImageView);

        Glide.with(this)
                .load(image)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blm2)
                        .error(R.drawable.blm2))
                .into(donationImage);

        donationTitle.setText(title);
        donationDesc.setText(desc);

        close.setOnClickListener(view -> finish());
    }

    void initView() {
        blurImageView = findViewById(R.id.blurImageView);
        donationImage = findViewById(R.id.actual_image);
        donationTitle = findViewById(R.id.donation_title);
        subTitle = findViewById(R.id.sub_title);
        donationDesc = findViewById(R.id.donation_desc);
        close = findViewById(R.id.close);
    }
}
