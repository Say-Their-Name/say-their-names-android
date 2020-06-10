package io.saytheirnames.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import io.saytheirnames.R;
import io.saytheirnames.models.Petition;
import io.saytheirnames.models.PetitionData;
import io.saytheirnames.network.BackendInterface;
import io.saytheirnames.network.Utils;
import io.saytheirnames.utils.CustomTabUtil;
import io.saytheirnames.utils.ShareUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jgabrielfreitas.core.BlurImageView;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private Petition petition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petition_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            petitionID = extras.getString(EXTRA_ID);

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
}