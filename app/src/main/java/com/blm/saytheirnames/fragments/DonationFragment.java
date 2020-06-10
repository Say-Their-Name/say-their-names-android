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
import com.blm.saytheirnames.adapters.DonationFilterAdapter;
import com.blm.saytheirnames.models.Donation;
import com.blm.saytheirnames.models.DonationType;
import com.blm.saytheirnames.models.DonationTypesData;
import com.blm.saytheirnames.models.DonationsData;
import com.blm.saytheirnames.network.BackendInterface;
import com.blm.saytheirnames.network.Utils;
import com.blm.saytheirnames.utils.CustomTabUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationFragment extends Fragment implements DonationFilterAdapter.DonationFilterListener {
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
    private DonationFilterAdapter donationFilterAdapter;

    private List<Donation> donationArrayList;
    private List<DonationType> donationTypeList;
    private ProgressBar progressBar;
    private ImageView imageView;

    BackendInterface backendInterface;

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

        backendInterface = Utils.getBackendService();

        donationArrayList = new ArrayList<>();
        donationTypeList = new ArrayList<>();

        donationRecyclerView = view.findViewById(R.id.donationRecycler);
        recyclerView = view.findViewById(R.id.recyclerView);

        donationAdapter = new DonationAdapter(donationArrayList, getActivity());
        donationFilterAdapter = new DonationFilterAdapter(donationTypeList, this);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager1 = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager1);
        donationRecyclerView.setLayoutManager(layoutManager);

        donationAdapter.setOnItemClickListener(position -> {
            String outcome_image_url, banner_img_url, title, desc;
            banner_img_url = donationArrayList.get(position).getBanner_img_url();
            outcome_image_url = donationArrayList.get(position).getOutcome_img_url();
            title = donationArrayList.get(position).getTitle();
            desc = donationArrayList.get(position).getDescription();

            Intent intent = new Intent(getContext(), DonationDetailsActivity.class);
            intent.putExtra("banner", banner_img_url);
            intent.putExtra("outcome", outcome_image_url);
            intent.putExtra("title", title);
            intent.putExtra("desc", desc);
            startActivity(intent);
        });

        donationRecyclerView.setAdapter(donationAdapter);
        recyclerView.setAdapter(donationFilterAdapter);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        getDonationFilterItems();
        loadData();


        return view;
    }

    @Override
    public void onDonationFilterSelected(DonationType donationType) {
        filterDonation(donationType.getType());
    }

    private void visitPage(String url) {
        CustomTabUtil.openCustomTabForUrl(getActivity(), url);
    }

    private void loadData() {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> getDonation = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {

                backendInterface.getDonations().enqueue(new Callback<DonationsData>() {
                    @Override
                    public void onResponse(@NonNull Call<DonationsData> call, @NonNull Response<DonationsData> response) {
                        donationArrayList.clear();
                        Log.d("API_Response", response.body().toString());
                        List<Donation> body = response.body().getData();

                        donationArrayList.addAll(body);
                        donationRecyclerView.setVisibility(View.VISIBLE);

                        donationAdapter.notifyDataSetChanged();
                        showProgress(false);
                    }

                    @Override
                    public void onFailure(Call<DonationsData> call, Throwable t) {
                        showProgress(false);
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
            }
        };
        getDonation.execute(null, null, null);
    }

    public void getDonationFilterItems(){
        showProgress(true);
        backendInterface.getDonationsTypes().enqueue(new Callback<DonationTypesData>() {
            @Override
            public void onResponse(Call<DonationTypesData> call, Response<DonationTypesData> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        donationTypeList.clear();
                        List<DonationType> body = response.body().getData();
                        //Add ALL item to list
                        DonationType donationType = new DonationType(0,"All");
                        donationTypeList.add(donationType);
                        donationTypeList.addAll(body);
                        donationFilterAdapter.notifyDataSetChanged();
                        showProgress(false);
                    }
                } else {
                    showProgress(false);
                   // onGetPersonFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<DonationTypesData> call, Throwable throwable) {
                showProgress(false);
            }
        });
    }

    public void filterDonation(String type){
        if(type.equals("All")){
            loadData();
        }else{
            doDonationFilter(type);
        }
    }

    private void doDonationFilter(String type) {
        showProgress(true);
        backendInterface.getFilteredDonations(type.toLowerCase()).enqueue(new Callback<DonationsData>() {
            @Override
            public void onResponse(@NonNull Call<DonationsData> call, @NonNull Response<DonationsData> response) {
                donationArrayList.clear();
                Log.d("API_Response", response.body().toString());
                List<Donation> body = response.body().getData();
                donationArrayList.addAll(body);
                donationRecyclerView.setVisibility(View.VISIBLE);

                donationAdapter.notifyDataSetChanged();
                showProgress(false);
            }

            @Override
            public void onFailure(Call<DonationsData> call, Throwable t) {
                showProgress(false);
            }
        });
    }

    private void showProgress(Boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
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
