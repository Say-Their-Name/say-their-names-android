package io.saytheirnames.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.saytheirnames.R;
import io.saytheirnames.adapters.PetitionsAdapter;
import io.saytheirnames.models.Petition;
import io.saytheirnames.models.PetitionsData;
import io.saytheirnames.network.BackendInterface;
import io.saytheirnames.network.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetitionsFragment extends Fragment {

    private RecyclerView recyclerView;
    private View myFragment;
    private Toolbar toolbar;
    // private ImageView imgFilter,imgSearch;

    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;

    private PetitionsAdapter petitionsAdapter;

    private List<Petition> petitionArrayList;


    public static PetitionsFragment newInstance() {
        return new PetitionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_petitions, container, false);

        recyclerView = myFragment.findViewById(R.id.recyclerView);
        toolbar = myFragment.findViewById(R.id.toolbar);
       /* imgFilter = toolbar.findViewById(R.id.imgFilter);
        imgSearch = toolbar.findViewById(R.id.imgSearch);*/

        //toolbar.setNavigationIcon(R.drawable.filter);
        //toolbar.setLogo(R.drawable.filter);
        toolbar.setTitle("");
     /*   imgFilter.setOnClickListener(v -> System.out.println("Filter"));
        imgSearch.setOnClickListener(v -> System.out.println("Search"));*/
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);


        petitionArrayList = new ArrayList<>();


        recyclerView = myFragment.findViewById(R.id.recyclerView);

        petitionsAdapter = new PetitionsAdapter(petitionArrayList, getActivity());

        progressBar = myFragment.findViewById(R.id.progressBar);

        layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(petitionsAdapter);

        loadData();

        return myFragment;
    }

    private void loadData() {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> getPetitions = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                BackendInterface backendInterface = Utils.getBackendService();
                backendInterface.getPetitions().enqueue(new Callback<PetitionsData>() {
                    @Override
                    public void onResponse(@NonNull Call<PetitionsData> call, @NonNull Response<PetitionsData> response) {
                        petitionArrayList.clear();
                        Log.d("API_Response", response.body().toString());
                        List<Petition> body = response.body().getData();

                        petitionArrayList.addAll(body);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);


                        petitionsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<PetitionsData> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("API_Response", t.getMessage().toString());
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
            }
        };
        getPetitions.execute(null, null, null);
    }
}