package com.blm.saytheirnames.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.adapters.MediaAdapter;
import com.blm.saytheirnames.models.Media;
import com.blm.saytheirnames.models.Person;
import com.blm.saytheirnames.models.PersonData;
import com.blm.saytheirnames.network.BackendInterface;
import com.blm.saytheirnames.network.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jgabrielfreitas.core.BlurImageView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_ID = "identifier";
    private ImageView actualImage;
    private BlurImageView blurImageView;
    private TextView txtName, txtDescription, txtMoreName,
            txtFullNameMore, txtAge, txtChildren,
            txtLocation, txtContextMore;
    private ImageButton btnReadMore;
    private ImageView closeDetails, imageViewMore, iv;

    private LinearLayout linearLayoutDetails;
    private LinearLayout linearLayoutReadMore;
    private LinearLayout linearLayout3;

    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private MediaAdapter mediaAdapter;

    private List<Media> mediaList;

    private String personID;
    boolean readMoreState = false;

    Person person;

   // ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        personID = getIntent().getStringExtra(EXTRA_ID);
        actualImage = findViewById(R.id.actual_image);
        blurImageView = findViewById(R.id.blurImageView);
        txtName = findViewById(R.id.txtName);
        txtMoreName = findViewById(R.id.txtMoreName);
        txtFullNameMore = findViewById(R.id.txtFullNameMore);
        txtAge = findViewById(R.id.txtAge);
        txtChildren = findViewById(R.id.txtChildren);
        txtLocation = findViewById(R.id.txtLocation);
        txtContextMore = findViewById(R.id.txtContextMore);
        txtDescription = findViewById(R.id.txtDescription);
        imageViewMore = findViewById(R.id.imageViewMore);
        btnReadMore = findViewById(R.id.btnReadmore);
        linearLayoutDetails = findViewById(R.id.linearLayoutDetails);
        linearLayoutReadMore = findViewById(R.id.linearLayoutReadmore);
        recyclerView = findViewById(R.id.recyclerView);

        mediaList = new ArrayList<>();

        btnReadMore.setOnClickListener(this);

        mediaAdapter = new MediaAdapter(mediaList,this);

        closeDetails = findViewById(R.id.closeDetails);
        closeDetails.setOnClickListener(view -> onBackPressed());

        Shader myShader = new LinearGradient(
                0, txtDescription.getLineHeight() * 5.10f, 0, 0,
                Color.WHITE, Color.parseColor("#101010"),
                Shader.TileMode.CLAMP);
        txtDescription.getPaint().setShader(myShader);

        layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mediaAdapter);

        loadData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReadmore:
                switchView(true);
                break;
        }
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
                backendInterface.getPeopleById(personID).enqueue(new Callback<PersonData>() {
                    @Override
                    public void onResponse(@NonNull Call<PersonData> call, @NonNull Response<PersonData> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                person = response.body().getData();
                            }
                            mediaList.addAll(person.getMedia_links());

                            Log.d("DATA", String.valueOf(response.body()));
                            Log.d("DATA1", String.valueOf(person.getMedia_links()));

                            txtName.setText(person.getFull_name());
                            txtDescription.setText(person.getContext());
                            txtMoreName.setText(person.getFull_name());
                            txtFullNameMore.setText(person.getFull_name());
                            txtAge.setText(person.getAge());
                            txtChildren.setText(nullCheck(person.getNumber_of_children()));
                            txtLocation.setText(person.getCity());
                            txtContextMore.setText(person.getContext());


                            Glide.with(getApplicationContext())
                                    .load(person.getImages().get(0).getImage_url())
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.blm2)
                                            .error(R.drawable.blm2))
                                    .into(actualImage);

                            Glide.with(getApplicationContext())
                                    .load(person.getImages().get(0).getImage_url())
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.blm2)
                                            .error(R.drawable.blm2))
                                    .apply(bitmapTransform(new BlurTransformation(22, 5)))
                                    .into(blurImageView);

                            Glide.with(getApplicationContext())
                                    .load(person.getImages().get(0).getImage_url())
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.blm2)
                                            .error(R.drawable.blm2))
                                    .into(imageViewMore);
                        }
                    }

                    @Override
                    public void onFailure(Call<PersonData> call, Throwable t) {
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

    private String nullCheck(String inputString) {
        if (inputString == null)
            inputString = "N/A";
        return inputString;
    }

    public void switchView(boolean state) {
        readMoreState = state;
        if (!state) {
            linearLayoutDetails.setVisibility(View.VISIBLE);
            linearLayoutReadMore.setVisibility(View.GONE);
        } else {
            linearLayoutDetails.setVisibility(View.GONE);
            linearLayoutReadMore.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (readMoreState) {
            switchView(false);
        } else {
            super.onBackPressed();
        }
    }
}