package com.appprocessors.ecomstore.paging;



import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor used to add TheMovieDB API Key to the http request
 * <p>
 * Created by Yassin Ajdi.
 */
public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        HttpUrl url = request.url().newBuilder().build();

        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
