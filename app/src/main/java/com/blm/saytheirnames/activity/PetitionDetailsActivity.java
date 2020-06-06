package com.blm.saytheirnames.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.customTabs.CustomTabActivityHelper;
import com.blm.saytheirnames.customTabs.WebViewActivity;
import com.blm.saytheirnames.models.PersonData;
import com.blm.saytheirnames.models.Petition;
import com.blm.saytheirnames.models.PetitionData;
import com.blm.saytheirnames.network.BackendInterface;
import com.blm.saytheirnames.network.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jgabrielfreitas.core.BlurImageView;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class PetitionDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_ID = "id";

    private ImageView imgClose, imgShare;

    private TextView txtName, txtDescription, txtShareThisPetition;

    private Button btnSignThisPetition;

    private ImageView actualImage;
    private BlurImageView blurImageView;

    private Toolbar toolbar;

    private int petitionID;
    private String petitionLink;

    private Petition petition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petition_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            petitionID = extras.getInt(EXTRA_ID);

        }

        txtName = findViewById(R.id.txtName);
        txtDescription = findViewById(R.id.txtDescription);

        toolbar = findViewById(R.id.toolbar);

        imgClose = toolbar.findViewById(R.id.imgClose);
        imgShare = toolbar.findViewById(R.id.imgShare);

        actualImage = findViewById(R.id.actual_image);
        blurImageView = findViewById(R.id.blurImageView);
        txtShareThisPetition = findViewById(R.id.btnShareThisPetition);
        btnSignThisPetition = findViewById(R.id.btnSignThisPetition);


        toolbar.setTitle("");
        imgClose.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        txtShareThisPetition.setOnClickListener(this);
        btnSignThisPetition.setOnClickListener(this);

        loadData();
    }

    private void loadData() {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> getPerson = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                BackendInterface backendInterface = Utils.getBackendService();
                backendInterface.getPetitionsById(petitionID).enqueue(new Callback<PetitionData>() {
                    @Override
                    public void onResponse(@NonNull Call<PetitionData> call, @NonNull Response<PetitionData> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                petition = response.body().getData();
                            }


                            txtName.setText(petition.getTitle());
                            txtDescription.setText(petition.getDescription());

                            petitionLink = petition.getLink();


                            Glide.with(getApplicationContext())
                                    .load(petition.getPerson().getImages().get(0).getImage_url())
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.blm2)
                                            .error(R.drawable.blm2))
                                    .into(actualImage);

                            Glide.with(getApplicationContext())
                                    .load(petition.getPerson().getImages().get(0).getImage_url())
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
                    public void onFailure(Call<PetitionData> call, Throwable t) {
                        Log.d("DATA", String.valueOf(t.getMessage()));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignThisPetition:
                if (validateUrl(petitionLink)) {
                    Uri uri = Uri.parse(petitionLink);
                    if (uri != null) {
                        openCustomChromeTab(uri);
                    }
                } else {
                    Toast.makeText(PetitionDetailsActivity.this, "Error with link", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnShareThisPetition:

                break;
            case R.id.imgClose:
                finish();
                break;

            case R.id.imgShare:
                
                break;
        }
    }

    private boolean validateUrl(String url) {
        return url != null && url.length() > 0 && (url.startsWith("http://") || url.startsWith("https://"));
    }

    private void openCustomChromeTab(Uri uri) {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = intentBuilder.build();

        intentBuilder.setToolbarColor(ContextCompat.getColor(PetitionDetailsActivity.this, R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(PetitionDetailsActivity.this, R.color.colorPrimaryDark));

        CustomTabActivityHelper.openCustomTab(PetitionDetailsActivity.this, customTabsIntent, uri, (activity, uri1) -> openWebView(uri1));
    }

    private void openWebView(Uri uri) {
        Intent webViewIntent = new Intent(PetitionDetailsActivity.this, WebViewActivity.class);
        webViewIntent.putExtra(WebViewActivity.EXTRA_URL, uri.toString());
        webViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(webViewIntent);
    }
}