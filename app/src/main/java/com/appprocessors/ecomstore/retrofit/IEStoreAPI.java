package com.appprocessors.ecomstore.retrofit;

import com.appprocessors.ecomstore.model.Address;
import com.appprocessors.ecomstore.model.Banner;
import com.appprocessors.ecomstore.model.CategoryBanner;
import com.appprocessors.ecomstore.model.CategoryProducts;
import com.appprocessors.ecomstore.model.ProductDetails;
import com.appprocessors.ecomstore.model.SubCategoryProducts;
import com.appprocessors.ecomstore.model.SubDistrict;
import com.appprocessors.ecomstore.model.TrendingSubCategory;
import com.appprocessors.ecomstore.model.User;
import com.appprocessors.ecomstore.model.categoryhome.CategoryHome;
import com.appprocessors.ecomstore.model.customer.Addresses;
import com.appprocessors.ecomstore.model.customer.Customer;
import com.appprocessors.ecomstore.model.order.Order;
import com.appprocessors.ecomstore.model.pictureslider.PictureSlider;
import com.appprocessors.ecomstore.model.product.Product;
import com.appprocessors.ecomstore.model.product.ProductList;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IEStoreAPI {

    @GET("customer/{phone}")
    Call<Customer> checkUserExists(@Path("phone") String phone);

    @GET("subCategoryProducts/all")
    Observable<List<CategoryProducts>> getAllSubCategory();

    @GET("category/subCategory/{parentCategoryId}")
    Observable<List<CategoryHome>> getCategoryProductsByParentCategoryId(@Path("parentCategoryId") String parentCategoryId);

    @GET("subCategoryTrending/{menuid}")
    Observable<List<TrendingSubCategory>> getSubCategoryTrendingByMenuId(@Path("menuid") String menuID);

    @GET("subSubCategoryProducts/{menuid}")
    Observable<List<SubCategoryProducts>> getSubCategoryProductsByMenuId(@Path("menuid") String menuID);

    @GET(value = "pictureSlider/pictureSlider/{parentCategoryId}")
    Observable<List<PictureSlider>> getCategoryPictureSlider(@Path("parentCategoryId") String parentCategoryId);

    @FormUrlEncoded
    @POST("user/{phone}")
    Call<User> getUserInformation(@Field("phone") String phone);

    @GET("/pictureSlider/pictureSliderHome")
    Observable<List<PictureSlider>> getBanners();

    @GET("/product/{id}")
    Observable<Product> getProductById(@Path("id") String id);

    @GET("productList/trendingProducts")
    Observable<List<com.appprocessors.ecomstore.model.product.ProductList>> getTrending(@Query("size") int size);

    @GET("/category")
    Observable<List<CategoryHome>> getCategory();

    //Spring User Insert(Create new User)
    @POST("/customer/addCustomer/{phone}")
    Call<Customer> createNewCustomer(@Path("phone") String phone, @Body Customer customer);

    //Insert Address
    @POST("/customer/{phone}/addAddress")
    Call<Addresses> addNewAddress(@Path("phone") String phone, @Body Addresses addresses);


    //Get User's  List of saved Address
    @GET("/customer/{phone}/allAddresses")
    Observable<List<Addresses>> findAllAddressesByPhone(@Path("phone") String phone);

    @Headers("Content-Type: application/json")
    @POST("/order/addOrder")
    Call<Order> addNewOrder(@Body Order order);

    @GET("/order/{CustomerId}")
    Observable<List<Order>> getAllOrdersByCustomerId(@Path("CustomerId") String CustomerId);

    //Get Product list  from  CategoryId
    @GET(value = "productList/{CategoryId}")
    Call<ProductList> getProductsListByCategoryId(
            @Path("CategoryId") String CategoryId,
            @Query("order") String order,
            @Query("page") int page,
            @Query("size") int size,
            @Query("sortby") String sortby
    );

    //Get List of all Sub Districts
    @GET("/subDistricts/all")
    Observable<List<SubDistrict>> getSubDistricts();

}

