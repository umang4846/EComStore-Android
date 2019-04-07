package com.appprocessors.ecomstore.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import androidx.annotation.NonNull;

import android.util.Log;
import android.widget.Toast;

import com.appprocessors.ecomstore.model.Content;
import com.appprocessors.ecomstore.model.ProductDetails;
import com.appprocessors.ecomstore.model.product.ProductList;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListItemSource extends PageKeyedDataSource<Integer, Content> {

    public static final int SIZE = 10;
    public static final int PAGE = 0;
    public static String SORT_BY = "productAverageRating";
    public static String ORDER_BY = "asc";

    public MutableLiveData<Resource> networkState = new MutableLiveData<>();
    private final IEStoreAPI mService;
    private Executor networkExecutor;
    private final ProductListFilterType sortBy;

    public RetryCallback retryCallback = null;

    public ProductListItemSource(IEStoreAPI mService, Executor networkExecutor, ProductListFilterType sortBy) {
        this.mService = mService;
        this.networkExecutor = networkExecutor;
        this.sortBy = sortBy;
    }


    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, Content> callback) {

        networkState.postValue(Resource.loading(null));

        // load data from API
        Call<ProductList> request;
        if (sortBy == ProductListFilterType.POPULARITY) {
            SORT_BY = "ApprovedTotalReviews";
            ORDER_BY = "desc";
            request = mService.getProductsListByCategoryId(Common.currentSubcategoryProducts.get_id(),ORDER_BY, PAGE, SIZE, SORT_BY);

        } else if (sortBy == ProductListFilterType.NEW_ARRIVAL) {
            SORT_BY = "CreatedOnUtc";
            ORDER_BY = "asc";
            request = mService.getProductsListByCategoryId(Common.currentSubcategoryProducts.get_id(),ORDER_BY, PAGE, SIZE, SORT_BY);
        } else if (sortBy == ProductListFilterType.PRICE_LOW_TO_HIGH) {
            SORT_BY = "Price";
            ORDER_BY = "asc";
            request = mService.getProductsListByCategoryId(Common.currentSubcategoryProducts.get_id(), ORDER_BY,PAGE, SIZE, SORT_BY);
        } else {
            SORT_BY = "Price";
            ORDER_BY = "desc";
            request = mService.getProductsListByCategoryId(Common.currentSubcategoryProducts.get_id(),ORDER_BY, PAGE, SIZE, SORT_BY);
        }

        // we execute sync since this is triggered by refresh
        try {
            Response<ProductList> response = request.execute();
            ProductList data = response.body();
            List<Content> movieList =
                    data != null ? data.getContent() : Collections.<Content>emptyList();

            retryCallback = null;
            networkState.postValue(Resource.success(null));
            callback.onResult(movieList, null, PAGE + 1);
        } catch (IOException e) {
            // publish error
            retryCallback = new RetryCallback() {
                @Override
                public void invoke() {
                    networkExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            loadInitial(params, callback);
                        }
                    });

                }
            };
            networkState.postValue(Resource.error(e.getMessage(), null));
        }

    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Content> callback) {
        // ignored, since we only ever append to our initial load
/*
        if (ORDER_BY.equalsIgnoreCase("DESC")) {
            mService.getProductByMenuidDESC(Common.currentcategoryProducts.get_id(), params.key, SIZE, SORT_BY)
                    .enqueue(new Callback<ProductDetails>() {
                        @Override
                        public void onResponse(Call<ProductDetails> call, Response<ProductDetails> response) {


                            if (response.body() != null ) {

                                Integer key = (params.key > 0) ? (params.key - 1) : null;
                                callback.onResult(response.body().content, key);
                            }

                        }

                        @Override
                        public void onFailure(Call<ProductDetails> call, Throwable t) {

                        }
                    });
        }
        else if (ORDER_BY.equalsIgnoreCase("ASC")){
            mService.getProductByMenuid(Common.currentcategoryProducts.get_id(), params.key, SIZE, SORT_BY)
                    .enqueue(new Callback<ProductDetails>() {
                        @Override
                        public void onResponse(Call<ProductDetails> call, Response<ProductDetails> response) {


                            if (response.body() != null) {
                                Integer key = (params.key > 0) ? (params.key - 1) : null;
                                callback.onResult(response.body().content, key);
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductDetails> call, Throwable t) {

                        }
                    });
        }*/
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Content> callback) {

        networkState.postValue(Resource.loading(null));

        // load data from API
        Call<ProductList> request;
        if (sortBy == ProductListFilterType.POPULARITY) {
            SORT_BY = "ApprovedTotalReviews";
            ORDER_BY = "desc";
            request = mService.getProductsListByCategoryId(Common.currentSubcategoryProducts.get_id(),ORDER_BY, PAGE, SIZE, SORT_BY);

        } else if (sortBy == ProductListFilterType.NEW_ARRIVAL) {
            SORT_BY = "CreatedOnUtc";
            ORDER_BY = "asc";
            request = mService.getProductsListByCategoryId(Common.currentSubcategoryProducts.get_id(),ORDER_BY, PAGE, SIZE, SORT_BY);
        } else if (sortBy == ProductListFilterType.PRICE_LOW_TO_HIGH) {
            SORT_BY = "Price";
            ORDER_BY = "asc";
            request = mService.getProductsListByCategoryId(Common.currentSubcategoryProducts.get_id(), ORDER_BY,PAGE, SIZE, SORT_BY);
        } else {
            SORT_BY = "Price";
            ORDER_BY = "desc";
            request = mService.getProductsListByCategoryId(Common.currentSubcategoryProducts.get_id(),ORDER_BY, PAGE, SIZE, SORT_BY);
        }

        request.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                if (response.isSuccessful()) {
                    ProductList data = response.body();
                    List<Content> movieList =
                            data != null ? data.getContent() : Collections.<Content>emptyList();

                    retryCallback = null;
                    callback.onResult(movieList, params.key + 1);
                    networkState.postValue(Resource.success(null));
                } else {
                    retryCallback = new RetryCallback() {
                        @Override
                        public void invoke() {
                            loadAfter(params, callback);
                        }
                    };
                    networkState.postValue(Resource.error("error code: " + response.code(), null));
                }
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                retryCallback = new RetryCallback() {
                    @Override
                    public void invoke() {
                        networkExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                loadAfter(params, callback);
                            }
                        });
                    }
                };
                networkState.postValue(Resource.error(t != null ? t.getMessage() : "unknown error", null));
            }
        });


    }

    public interface RetryCallback {
        void invoke();
    }

}
