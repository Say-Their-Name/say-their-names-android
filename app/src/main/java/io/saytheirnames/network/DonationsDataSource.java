package io.saytheirnames.network;

import androidx.paging.PagingSource;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collections;

import io.saytheirnames.models.Donation;
import io.saytheirnames.models.DonationsData;
import kotlin.coroutines.Continuation;

public class DonationsDataSource extends PagingSource<Integer, Donation> {

    private BackendInterface backendInterface = Utils.getBackendService();
    private String type;

    public DonationsDataSource(String type) {
        this.type = type;
    }

    @Nullable
    @Override
    public Object load(@NotNull LoadParams<Integer> loadParams, @NotNull Continuation<? super LoadResult<Integer, Donation>> continuation) {

        DonationsData donationsData;

        try {
            if (type.equals("All")) {
                donationsData = backendInterface.getDonations(loadParams.getKey()).execute().body();
            } else {
                donationsData = backendInterface.getDonationsWithFilter(loadParams.getKey(), type).execute().body();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            //TODO: We could use a better error state.
            return new LoadResult.Page<Integer, Donation>(Collections.emptyList(), null, null, 0, 0);
        }

        int next = donationsData.getPagination().getCurrentPage() + 1;

        return new LoadResult.Page<>(donationsData.getData(), null, next > donationsData.getPagination().getLastPage() ? null : next, 0, 0);
    }

}
