package com.appprocessors.ecomstore.paging;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.Content;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProductListViewModel extends ViewModel {

    private LiveData<RepoMoviesResult> repoMoviesResult;

    private LiveData<PagedList<Content>> pagedList;

    private LiveData<Resource> networkState;

    private MutableLiveData<Integer> currentTitle = new MutableLiveData<>();

    private MutableLiveData<ProductListFilterType> sortBy = new MutableLiveData<>();

    public ProductListViewModel() {
    }

    public ProductListViewModel(final MovieRepository movieRepository) {

        // By default show popular product
        sortBy.postValue(ProductListFilterType.POPULARITY);
        currentTitle.postValue(R.string.action_popularity);

        repoMoviesResult = Transformations.map(sortBy, new Function<ProductListFilterType, RepoMoviesResult>() {
            @Override
            public RepoMoviesResult apply(ProductListFilterType sort) {
                return movieRepository.loadMoviesFilteredBy(sort);
            }
        });
        pagedList = Transformations.switchMap(repoMoviesResult,
                new Function<RepoMoviesResult, LiveData<PagedList<Content>>>() {
                    @Override
                    public LiveData<PagedList<Content>> apply(RepoMoviesResult input) {
                        return input.data;
                    }
                });

        networkState = Transformations.switchMap(repoMoviesResult, new Function<RepoMoviesResult, LiveData<Resource>>() {
            @Override
            public LiveData<Resource> apply(RepoMoviesResult input) {
                return input.resource;
            }
        });
    }

    public LiveData<PagedList<Content>> getPagedList() {
        return pagedList;
    }

    public LiveData<Resource> getNetworkState() {
        return networkState;
    }

    public ProductListFilterType getCurrentSorting() {
        return sortBy.getValue();
    }

    public LiveData<Integer> getCurrentTitle() {
        return currentTitle;
    }

    public void setSortProductBy(String sortProductBy) {
        ProductListFilterType filterType = null;
        Integer title = null;
        switch (sortProductBy) {
            case "Popularity": {
                // check if already selected. no need to request API
                if (sortBy.getValue() == ProductListFilterType.POPULARITY)
                    return;

                filterType = ProductListFilterType.POPULARITY;
                title = R.string.action_popularity;
                break;
            }
            case "New Arrival": {
                // check if already selected. no need to request API
                if (sortBy.getValue() == ProductListFilterType.NEW_ARRIVAL)
                    return;

                filterType = ProductListFilterType.NEW_ARRIVAL;
                title = R.string.action_new_arrival;
                break;
            }
            case "Price - Low to High" :{
                // check if already selected. no need to request API
                if (sortBy.getValue() == ProductListFilterType.PRICE_LOW_TO_HIGH)
                    return;

                filterType = ProductListFilterType.PRICE_LOW_TO_HIGH;
                title = R.string.action_price_low_to_high;
                break;
            }
            case "Price - High to Low": {
                if (sortBy.getValue() == ProductListFilterType.PRICE_HIGH_TO_LOW)
                    return;

                filterType = ProductListFilterType.PRICE_HIGH_TO_LOW;
                title = R.string.action_price_high_to_low;
                break;
            }
            default:
                throw new IllegalArgumentException("unknown sorting id");
        }
        sortBy.postValue(filterType);
        currentTitle.postValue(title);
    }

    // retry any failed requests.
    public void retry() {
        repoMoviesResult.getValue().sourceLiveData.getValue().retryCallback.invoke();
    }


}
