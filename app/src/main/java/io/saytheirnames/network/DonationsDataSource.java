package io.saytheirnames.network;

import androidx.paging.PagingSource;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collections;

import io.saytheirnames.models.Donation;
import io.saytheirnames.models.DonationsData;
import io.saytheirnames.models.Petition;
import io.saytheirnames.models.PetitionsData;
import kotlin.coroutines.Continuation;

public class DonationsDataSource extends PagingSource<Integer, Donation> {

    private BackendInterface backendInterface = Utils.getBackendService();

    @Nullable
    @Override
    public Object load(@NotNull LoadParams<Integer> loadParams, @NotNull Continuation<? super LoadResult<Integer, Donation>> continuation) {

        DonationsData petitionData;

        try {
            petitionData = backendInterface.getDonations(loadParams.getKey()).execute().body();
        } catch (IOException exception) {
            exception.printStackTrace();
            //TODO: We could use a better error state.
            return new LoadResult.Page<Integer, Petition>(Collections.emptyList(), null, null, 0, 0);
        }

        int next = petitionData.getPagination().getCurrentPage() + 1;

        return new LoadResult.Page<Integer, Donation>(petitionData.getData(), null, next > petitionData.getPagination().getLastPage() ? null : next, 0, 0);
    }

}
