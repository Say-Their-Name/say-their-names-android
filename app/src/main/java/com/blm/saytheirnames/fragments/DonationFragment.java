package com.blm.saytheirnames.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.activity.DonationDetailsActivity;
import com.blm.saytheirnames.adapters.DonationAdapter;
import com.blm.saytheirnames.adapters.FilterAdapter;
import com.blm.saytheirnames.models.Donation;
import com.blm.saytheirnames.models.DonationData;
import com.blm.saytheirnames.network.BackendInterface;
import com.blm.saytheirnames.network.Utils;
import com.blm.saytheirnames.utils.CustomTabUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationFragment extends Fragment {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_COLOR = "arg_color";

    private String mText;
    private int mColor;

    private View view;
    //private TextView mTextView;

    private RecyclerView donationRecyclerView;
    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManager1;

    private DonationAdapter donationAdapter;
    private FilterAdapter filterAdapter;

    private List<Donation> donationArrayList;
    private String[] filterList;
    private ProgressBar progressBar;
    private ImageView imageView;

    Resources resources;

    public static DonationFragment newInstance() {
        return new DonationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_donation, container, false);

        resources = getResources();

        donationArrayList = new ArrayList<>();

        filterList = resources.getStringArray(R.array.tag_donation);

        donationRecyclerView = view.findViewById(R.id.donationRecycler);
        recyclerView = view.findViewById(R.id.recyclerView);

        donationAdapter = new DonationAdapter(donationArrayList, getActivity());
        filterAdapter = new FilterAdapter(filterList, getActivity());

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager1 = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager1);
        donationRecyclerView.setLayoutManager(layoutManager);

        donationAdapter.setOnItemClickListener(position -> {
            String image_url, title, desc;
            image_url = donationArrayList.get(position).getImage();
            title = donationArrayList.get(position).getTitle();
            desc = donationArrayList.get(position).getDescription();

            Intent intent = new Intent(getContext(), DonationDetailsActivity.class);
            intent.putExtra("image", image_url);
            intent.putExtra("title", title);
            intent.putExtra("desc", desc);
            startActivity(intent);
        });

        donationRecyclerView.setAdapter(donationAdapter);
        recyclerView.setAdapter(filterAdapter);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        loadData();


        return view;
    }

    private boolean validateUrl(String url) {
        return url != null && url.length() > 0 && (url.startsWith("http://") || url.startsWith("https://"));
    }

    private void visitPage(String url) {
        CustomTabUtil.openCustomTabForUrl(getActivity(), url);
    }

    private void loadData() {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> getPeople = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                BackendInterface backendInterface = Utils.getBackendService();
                backendInterface.getDonations().enqueue(new Callback<DonationData>() {
                    @Override
                    public void onResponse(@NonNull Call<DonationData> call, @NonNull Response<DonationData> response) {
                        donationArrayList.clear();
                        Log.d("API_Response", response.body().toString());
                        List<Donation> body = response.body().getData();

                        progressBar.setVisibility(View.GONE);
                        donationArrayList.addAll(body);
                        donationRecyclerView.setVisibility(View.VISIBLE);

                        filterAdapter.notifyDataSetChanged();
                        donationAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<DonationData> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
            }
        };
        getPeople.execute(null, null, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize views
        view = view.findViewById(R.id.navigation_donation);

        // retrieve text and color from bundle or savedInstanceState
        /*if (savedInstanceState == null) {
            Bundle args = getActivity().getIntent().getExtras();
            assert args != null;
            mText = args.getString(ARG_TEXT);
            //mColor = args.getInt(ARG_COLOR);
        } else {
            mText = savedInstanceState.getString(ARG_TEXT);
            // mColor = savedInstanceState.getInt(ARG_COLOR);
        }*/

        Bundle bundle = requireActivity().getIntent().getExtras();
        if (bundle != null) {
            //mTextView.setText(" "+bundle.getString("arg_text"));
        }

        // set text and background color
        // mTextView.setText(text);
        //view.setBackgroundColor(mColor);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TEXT, mText);
        // outState.putInt(ARG_COLOR, mColor);
        super.onSaveInstanceState(outState);
    }

}
