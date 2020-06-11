package io.saytheirnames.models;

import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("current_page")
    Integer currentPage;

    @SerializedName("last_page")
    Integer lastPage;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }
}
