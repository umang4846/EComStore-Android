package com.appprocessors.ecomstore.paging;


import com.appprocessors.ecomstore.model.Content;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * @author Yassin Ajdi.
 */
public class MoviesRemoteDataSource {

    private static final int PAGE_SIZE = 10;

    private final AppExecutors mExecutors;

    private static volatile MoviesRemoteDataSource sInstance;

    private final IEStoreAPI mMovieService;

    private MoviesRemoteDataSource(IEStoreAPI movieService,
                                   AppExecutors executors) {
        mMovieService = movieService;
        mExecutors = executors;
    }

    public static MoviesRemoteDataSource getInstance(IEStoreAPI movieService,
                                                     AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppExecutors.class) {
                if (sInstance == null) {
                    sInstance = new MoviesRemoteDataSource(movieService, executors);
                }
            }
        }
        return sInstance;
    }

    public LiveData<ApiResponse<Content>> loadMovie(final long movieId) {
       // return mMovieService.getCategoryProductsByMenuId(String.valueOf(movieId));
        return null;
    }

    /**
     * Load movies for certain filter.
     */
    public RepoMoviesResult loadMoviesFilteredBy(ProductListFilterType sortBy) {
        ProductListDataSourceFactory sourceFactory =
                new ProductListDataSourceFactory(mMovieService, mExecutors.networkIO(), sortBy);

        // paging configuration
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();

        // Get the paged list
        LiveData<PagedList<Content>> moviesPagedList = new LivePagedListBuilder<>(sourceFactory, config)
                .setFetchExecutor(mExecutors.networkIO())
                .build();

        LiveData<Resource> networkState = Transformations.switchMap(sourceFactory.sourceLiveData, new Function<ProductListItemSource, LiveData<Resource>>() {
            @Override
            public LiveData<Resource> apply(ProductListItemSource input) {
                return input.networkState;
            }
        });

        // Get pagedList and network errors exposed to the viewmodel
        return new RepoMoviesResult(
                moviesPagedList,
                networkState,
                sourceFactory.sourceLiveData
        );
    }
}
