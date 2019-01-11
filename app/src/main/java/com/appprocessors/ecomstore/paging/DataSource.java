package com.appprocessors.ecomstore.paging;

import com.appprocessors.ecomstore.model.ProductDetails;
import androidx.lifecycle.LiveData;

/**
 * @author Yassin Ajdi.
 */
public interface DataSource {

    LiveData<Resource<ProductDetails>> loadMovie(long movieId);

    RepoMoviesResult loadMoviesFilteredBy(ProductListFilterType sortBy);

}
