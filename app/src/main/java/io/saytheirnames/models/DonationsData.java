package io.saytheirnames.models;

import androidx.annotation.NonNull;

import java.util.List;

public class DonationsData {

    private List<Donation> data;

    private @NonNull
    Pagination meta;

    public List<Donation> getData() {
        return data;
    }

    public void setData(List<Donation> data) {
        this.data = data;
    }

    @NonNull
    public Pagination getPagination() {
        return meta;
    }
}
