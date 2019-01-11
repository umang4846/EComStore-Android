package com.appprocessors.ecomstore.paging;

import com.appprocessors.ecomstore.model.Content;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

/**
 * @author Yassin Ajdi.
 */
public class RepoMoviesResult {
    public LiveData<PagedList<Content>> data;
    public LiveData<Resource> resource;
    public MutableLiveData<ProductListItemSource> sourceLiveData;

    public RepoMoviesResult(LiveData<PagedList<Content>> data,
                            LiveData<Resource> resource,
                            MutableLiveData<ProductListItemSource> sourceLiveData) {
        this.data = data;
        this.resource = resource;
        this.sourceLiveData = sourceLiveData;
    }
}
