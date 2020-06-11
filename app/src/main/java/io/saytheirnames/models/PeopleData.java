package io.saytheirnames.models;

import androidx.annotation.NonNull;

import java.util.List;

public class PeopleData {
    private List<People> data;

    private @NonNull Pagination meta;

    public List<People> getData() {
        return data;
    }

    public void setData(List<People> data) {
        this.data = data;
    }

    public @NonNull Pagination getPagination() {
        return meta;
    }
}