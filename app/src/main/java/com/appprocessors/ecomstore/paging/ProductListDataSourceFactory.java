package com.appprocessors.ecomstore.paging;

import com.appprocessors.ecomstore.model.Content;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import java.util.concurrent.Executor;

public class ProductListDataSourceFactory  extends DataSource.Factory<Integer,Content> {

    public MutableLiveData<ProductListItemSource> sourceLiveData = new MutableLiveData<>();
    private final IEStoreAPI movieService;
    private final Executor networkExecutor;
    private final ProductListFilterType sortBy;


    public ProductListDataSourceFactory(IEStoreAPI movieService,
                                  Executor networkExecutor, ProductListFilterType sortBy) {
        this.movieService = movieService;
        this.sortBy = sortBy;
        this.networkExecutor = networkExecutor;
    }

    @Override
    public DataSource<Integer, Content> create() {
        ProductListItemSource movieDataSource =
                new ProductListItemSource(movieService, networkExecutor, sortBy);
        sourceLiveData.postValue(movieDataSource);
        return movieDataSource;
    }

}
