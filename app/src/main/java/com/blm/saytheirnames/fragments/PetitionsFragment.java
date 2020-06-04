package com.blm.saytheirnames.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.adapters.FilterAdapter;
import com.blm.saytheirnames.adapters.PersonsAdapter;
import com.blm.saytheirnames.adapters.PetitionsAdapter;
import com.blm.saytheirnames.models.Person;
import com.blm.saytheirnames.models.Petition;
import com.blm.saytheirnames.models.PetitionData;
import com.blm.saytheirnames.network.BackendInterface;
import com.blm.saytheirnames.network.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetitionsFragment extends Fragment {

    private RecyclerView recyclerView;
    private View myFragment;
    private Toolbar toolbar;
    private ImageView imgFilter,imgSearch;

    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;

    private PetitionsAdapter petitionsAdapter;

    private List<Petition> petitionArrayList;
    private String[] filterList;


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
        myFragment =  inflater.inflate(R.layout.fragment_petitions, container, false);

        recyclerView = myFragment.findViewById(R.id.recyclerView);
        toolbar = myFragment.findViewById(R.id.toolbar);
        imgFilter = toolbar.findViewById(R.id.imgFilter);
        imgSearch = toolbar.findViewById(R.id.imgSearch);

        //toolbar.setNavigationIcon(R.drawable.filter);
        //toolbar.setLogo(R.drawable.filter);
        toolbar.setTitle("");
        imgFilter.setOnClickListener(v -> System.out.println("Filter"));
        imgSearch.setOnClickListener(v -> System.out.println("Search"));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        petitionArrayList = new ArrayList<>();


        recyclerView = myFragment.findViewById(R.id.recyclerView);

        petitionsAdapter = new PetitionsAdapter(petitionArrayList,getActivity());

        progressBar = myFragment.findViewById(R.id.progressBar);


        progressBar.getIndeterminateDrawable().setColorFilter(R.color.colorBlack, android.graphics.PorterDuff.Mode.MULTIPLY);

        layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(petitionsAdapter);

        loadData();

        return myFragment;
    }

    /*private void loadData(){
        petitionArrayList.clear();


//        filterList.add(new String[]{"Location ", "gfhfghhg", "hgfgfhfhgfhg"});
        for(int i = 0; i < 10; i++){
            Petition person = new Petition(1,"George Floyd","he death of George Floyd occurred on May 25, 2020, when Derek Chauvin, a white Minneapolis police officer, kneeled on his neck for at least seven minutes","https://google.com","Minnesota",null);

            petitionArrayList.add(person);
        }

        petitionsAdapter.notifyDataSetChanged();
    }*/

    private void loadData() {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> getPetitions    = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                BackendInterface backendInterface = Utils.getBackendService();
                backendInterface.getPetitions().enqueue(new Callback<PetitionData>() {
                    @Override
                    public void onResponse(@NonNull Call<PetitionData> call, @NonNull Response<PetitionData> response) {
                        petitionArrayList.clear();
                        Log.d("API_Response", response.body().toString());
                        List<Petition> body = response.body().getData();

                        petitionArrayList.addAll(body);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);


                        petitionsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<PetitionData> call, Throwable t) {
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