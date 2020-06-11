package io.saytheirnames.network;

import androidx.paging.PagingSource;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import io.saytheirnames.models.People;
import io.saytheirnames.models.PeopleData;
import kotlin.coroutines.Continuation;

public class PeopleDataSource extends PagingSource<Integer, People> {

    private BackendInterface backendInterface = Utils.getBackendService();

    @Nullable
    @Override
    public Object load(@NotNull LoadParams<Integer> loadParams, @NotNull Continuation<? super LoadResult<Integer, People>> continuation) {

        PeopleData peopleData;

        try {
            peopleData = backendInterface.getPeople(loadParams.getKey()).execute().body();
        } catch (IOException exception) {
            exception.printStackTrace();
            //TODO: We could use a better error state.
            return new LoadResult.Page<Integer, People>(Collections.emptyList(), null, null, 0, 0);
        }

        int next = peopleData.getPagination().getCurrentPage() + 1;

        return new LoadResult.Page<Integer, People>(peopleData.getData(), null, next > peopleData.getPagination().getLastPage() ? null : next, 0, 0);
    }

}
