package com.appprocessors.ecomstore.retrofit;

import com.appprocessors.ecomstore.model.Address;
import com.appprocessors.ecomstore.model.Banner;
import com.appprocessors.ecomstore.model.Category;
import com.appprocessors.ecomstore.model.CategoryBanner;
import com.appprocessors.ecomstore.model.CategoryProducts;
import com.appprocessors.ecomstore.model.OrderModel;
import com.appprocessors.ecomstore.model.ProductDetails;
import com.appprocessors.ecomstore.model.ProductList;
import com.appprocessors.ecomstore.model.SubCategoryProducts;
import com.appprocessors.ecomstore.model.SubDistrict;
import com.appprocessors.ecomstore.model.Trending;
import com.appprocessors.ecomstore.model.TrendingSubCategory;
import com.appprocessors.ecomstore.model.User;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IEStoreAPI {

    @GET("user/{phone}")
    Call<User> checkUserExists(@Path("phone") String phone);

    @FormUrlEncoded
    @POST("register.php")
    Call<User> registerNewUser(@Field("phone") String phone,
                               @Field("name") String name,
                               @Field("email") String email,
                               @Field("password") String password,
                               @Field("gender") String gender);


    @GET("subCategoryProducts/all")
    Observable<List<CategoryProducts>> getAllSubCategory();

    @GET("subCategoryProducts/{menuid}")
    Observable<List<CategoryProducts>> getCategoryProductsByMenuId(@Path("menuid") String menuID);

    @GET("subCategoryTrending/{menuid}")
    Observable<List<TrendingSubCategory>> getSubCategoryTrendingByMenuId(@Path("menuid") String menuID);

    @GET("subSubCategoryProducts/{menuid}")
    Observable<List<SubCategoryProducts>> getSubCategoryProductsByMenuId(@Path("menuid") String menuID);

    @GET("bannerCategoryProducts/{menuid}")
    Observable<List<CategoryBanner>> getCategoryBanner(@Path("menuid") String menuid);

    @FormUrlEncoded
    @POST("user/{phone}")
    Call<User> getUserInformation(@Field("phone") String phone);

    @GET("/bannerHome/topbanners")
    Observable<List<Banner>> getBanners();

    @GET("/fragrances/productDetailsByProductCode/{productCode}")
    Observable<ProductDetails> getProductDetailsByProductCode(@Path("productCode") String productCode);

    @GET("trendingHome")
    Observable<List<Trending>> getTrending();

    @GET("/categoryHome")
    Observable<List<Category>> getCategory();

    //Spring User Insert(Create new User)
    @POST("/user")
    Call<User> registerUser(@Body User user);

    //Insert Address
    @POST("/user/{phone}/addAddress")
    Call<List<Address>> addNewAddress(@Path("phone") String phone,@Body List<Address> addresses);

    @POST("/orders")
    Call<OrderModel> addOrder(@Body OrderModel orderModel);

    @GET("/orders/allorders")
    Observable<List<OrderModel>> getAllOrders();

    //Get Product from  menuid
    @GET(value = "fragrances/{menuid}")
    Call<ProductList> getProductByMenuid(
            @Path("menuid") String menuid,
            @Query("page")int page,
            @Query("size")int size,
            @Query("sortby")String sortby
    );

    //Get Product from  menuid in descending order
    @GET(value = "fragrances/{menuid}/desc")
    Call<ProductList> getProductByMenuidDESC(
            @Path("menuid") String menuid,
            @Query("page")int page,
            @Query("size")int size,
            @Query("sortby")String sortby
    );

    //Get List of all Sub Districts
    @GET("/subDistricts/all")
    Observable<List<SubDistrict>> getSubDistricts();

}

