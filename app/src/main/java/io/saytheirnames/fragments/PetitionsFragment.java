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
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import io.saytheirnames.R;
import io.saytheirnames.adapters.PetitionsAdapter;
import io.saytheirnames.models.Petition;
import io.saytheirnames.models.PetitionsData;
import io.saytheirnames.network.BackendInterface;
import io.saytheirnames.network.PetitionsPager;
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

    //private LinearLayoutManager layoutManager;
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

        petitionsAdapter = new PetitionsAdapter();

        progressBar = myFragment.findViewById(R.id.progressBar);


        initializeRecyclerView();
        loadData();

        return myFragment;
    }

    private void initializeRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        /*
            This triggers the showing/hiding of the progress bar. This looks really hacky
            this way, though.
         */

        petitionsAdapter.withLoadStateFooter(new LoadStateAdapter<RecyclerView.ViewHolder>() {
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

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(petitionsAdapter);
    }

    private void loadData() {
        PetitionsPager petitionsPager = new PetitionsPager(petitionsAdapter);
        petitionsPager.loadPetitionsFromPagination();
    }
}