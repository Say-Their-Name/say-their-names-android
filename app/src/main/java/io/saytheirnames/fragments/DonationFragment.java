package io.saytheirnames.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.saytheirnames.R;
import io.saytheirnames.adapters.DonationAdapter;
import io.saytheirnames.adapters.DonationFilterAdapter;
import io.saytheirnames.models.Donation;
import io.saytheirnames.models.DonationType;
import io.saytheirnames.models.DonationTypesData;
import io.saytheirnames.models.DonationsData;
import io.saytheirnames.network.BackendInterface;
import io.saytheirnames.network.DonationsPager;
import io.saytheirnames.network.Utils;
import io.saytheirnames.utils.CustomTabUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationFragment extends Fragment {
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
    private NestedScrollView nestedScrollView;

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

        nestedScrollView = view.findViewById(R.id.nestedScroll);

        donationAdapter = new DonationAdapter();

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager1 = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

        /*donationRecyclerView.setLayoutManager(layoutManager);


        donationRecyclerView.setAdapter(donationAdapter);*/

        progressBar = view.findViewById(R.id.progressBar);

        initializeRecyclerView();
        loadData();

        return view;
    }

    // donation filter will not be shown in MVP so this method is unused for now
    public void setupDonationFilters() {
        /*recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager1);
        donationFilterAdapter = new DonationFilterAdapter(donationTypeList, this, nestedScrollView);
        recyclerView.setAdapter(donationFilterAdapter);
        getDonationFilterItems();*/
    }

    private void visitPage(String url) {
        CustomTabUtil.openCustomTabForUrl(getActivity(), url);
    }

    private void initializeRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        /*
            This triggers the showing/hiding of the progress bar. This looks really hacky
            this way, though.
         */

        donationAdapter.withLoadStateFooter(new LoadStateAdapter<RecyclerView.ViewHolder>() {
            @NotNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, @NotNull LoadState loadState) {
                return new RecyclerView.ViewHolder(progressBar){};
            }

            @Override
            public void onBindViewHolder(@NotNull RecyclerView.ViewHolder viewHolder, @NotNull LoadState loadState) {
                if (loadState.equals(LoadState.Loading.INSTANCE)) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        donationRecyclerView.setHasFixedSize(true);
        donationRecyclerView.setLayoutManager(layoutManager);
        donationRecyclerView.setAdapter(donationAdapter);
    }

    private void loadData() {
        DonationsPager petitionsPager = new DonationsPager(donationAdapter);
        petitionsPager.loadDonationsFromPagination();
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

        // initialize views TODO this is never used. Use it or removit , please :)
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
        //TODO nothing happens here
        if (bundle != null) {
            //mTextView.setText(" "+bundle.getString("arg_text"));
        }

        // set text and background color
        // mTextView.setText(text);
        //view.setBackgroundColor(mColor);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
