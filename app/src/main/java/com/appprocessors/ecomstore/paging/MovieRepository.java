package com.appprocessors.ecomstore.paging;



import android.util.Log;

import com.appprocessors.ecomstore.model.Content;
import com.appprocessors.ecomstore.model.ProductDetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
/**
 * Repository implementation that returns a paginated data and loads data directly from network.
 *
 * @author Yassin Ajdi.
 */
public class MovieRepository implements DataSource {

    private static volatile MovieRepository sInstance;


    private final MoviesRemoteDataSource mRemoteDataSource;

    private final AppExecutors mExecutors;

    private MovieRepository(
                            MoviesRemoteDataSource remoteDataSource,
                            AppExecutors executors) {
        mRemoteDataSource = remoteDataSource;
        mExecutors = executors;
    }

    public static MovieRepository getInstance(
                                              MoviesRemoteDataSource remoteDataSource,
                                              AppExecutors executors) {
        if (sInstance == null) {
            synchronized (MovieRepository.class) {
                if (sInstance == null) {
                    sInstance = new MovieRepository( remoteDataSource, executors);
                }
            }
        }
        return sInstance;
    }

    @Override
    public LiveData<Resource<ProductDetails>> loadMovie(final long movieId) {
        return new NetworkBoundResource<ProductDetails, Content>(mExecutors) {

            @Override
            protected void saveCallResult(@NonNull Content item) {
                Log.d("TAG","Movie added to database");
            }

            @Override
            protected boolean shouldFetch(@Nullable ProductDetails data) {
                return data == null; // only fetch fresh data if it doesn't exist in database
            }

            @NonNull
            @Override
            protected LiveData<ProductDetails> loadFromDb() {
                return null;
            }


            @NonNull
            @Override
            protected LiveData<ApiResponse<Content>> createCall() {
                Log.d("TAG","Downloading movie from network");
                return mRemoteDataSource.loadMovie(movieId);
            }

            @NonNull
            @Override
            protected void onFetchFailed() {
                // ignored
                Log.d("TAG","Fetch failed!!");
            }
        }.getAsLiveData();
    }

    @Override
    public RepoMoviesResult loadMoviesFilteredBy(ProductListFilterType sortBy) {
        return mRemoteDataSource.loadMoviesFilteredBy(sortBy);
    }



}