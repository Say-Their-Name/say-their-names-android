package io.saytheirnames.models;

import androidx.annotation.NonNull;

import java.util.List;

public class PetitionsData {
    private List<Petition> data;

    private @NonNull
    Pagination meta;

    public List<Petition> getData() {
        return data;
    }

    public void setData(List<Petition> data) {
        this.data = data;
    }

    @NonNull
    public Pagination getPagination() {
        return meta;
    }
}
