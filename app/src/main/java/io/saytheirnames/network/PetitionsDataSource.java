package io.saytheirnames.network;

import androidx.paging.PagingSource;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collections;

import io.saytheirnames.models.People;
import io.saytheirnames.models.PeopleData;
import io.saytheirnames.models.Petition;
import io.saytheirnames.models.PetitionData;
import io.saytheirnames.models.PetitionsData;
import kotlin.coroutines.Continuation;

public class PetitionsDataSource extends PagingSource<Integer, Petition> {

    private BackendInterface backendInterface = Utils.getBackendService();

    @Nullable
    @Override
    public Object load(@NotNull LoadParams<Integer> loadParams, @NotNull Continuation<? super LoadResult<Integer, Petition>> continuation) {

        PetitionsData petitionData;

        try {
            petitionData = backendInterface.getPetitions(loadParams.getKey()).execute().body();
        } catch (IOException exception) {
            exception.printStackTrace();
            //TODO: We could use a better error state.
            return new LoadResult.Page<Integer, Petition>(Collections.emptyList(), null, null, 0, 0);
        }

        int next = petitionData.getPagination().getCurrentPage() + 1;

        return new LoadResult.Page<Integer, Petition>(petitionData.getData(), null, next > petitionData.getPagination().getLastPage() ? null : next, 0, 0);
    }

}
