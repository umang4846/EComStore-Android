package com.appprocessors.ecomstore.paging;

import android.content.Context;

import com.appprocessors.ecomstore.retrofit.IEStoreAPI;

/**
 * Class that handles object creation.
 *
 * @author Yassin Ajdi.
 */
public class Injection {

    /**
     * Creates an instance of MoviesRemoteDataSource
     */
    public static MoviesRemoteDataSource provideMoviesRemoteDataSource() {
        IEStoreAPI apiService = ApiClient.getInstance();
        AppExecutors executors = AppExecutors.getInstance();
        return MoviesRemoteDataSource.getInstance(apiService, executors);
    }

    /**
     * Creates an instance of MovieRepository
     */
    public static MovieRepository provideMovieRepository(Context context) {
        MoviesRemoteDataSource remoteDataSource = provideMoviesRemoteDataSource();
        AppExecutors executors = AppExecutors.getInstance();
        return MovieRepository.getInstance(
                remoteDataSource,
                executors);
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        MovieRepository repository = provideMovieRepository(context);
        return ViewModelFactory.getInstance(repository);
    }
}
