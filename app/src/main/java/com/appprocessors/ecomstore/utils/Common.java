package com.appprocessors.ecomstore.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.appprocessors.ecomstore.database.datasource.CartRepository;
import com.appprocessors.ecomstore.database.local.CartDatabase;
import com.appprocessors.ecomstore.model.Category;
import com.appprocessors.ecomstore.model.CategoryProducts;
import com.appprocessors.ecomstore.model.Content;
import com.appprocessors.ecomstore.model.ProductDetails;
import com.appprocessors.ecomstore.model.SubCategoryProducts;
import com.appprocessors.ecomstore.model.User;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.retrofit.RetrofitClient;

import java.util.regex.Pattern;

public class Common {

    private static String TAG = "Common";
    // For Tempoary Server link  is :  https://estorecom.000webhostapp.com/estore/
    //"http://10.0.3.2:8080/";

   public static final String BASE_URL = "http://192.168.43.51:8080";
   // private static final String BASE_URL = "http://10.0.3.2:8080/";
   // private static final String BASE_URL = "https://estoreapp.herokuapp.com";

    //Current user
    public static User currentUser = null;

    //Current Sub Category Products
    public static SubCategoryProducts currentSubcategoryProducts = null;


    public static IEStoreAPI getAPI(){
        return RetrofitClient.getClient(BASE_URL).create(IEStoreAPI.class);
    }

    //Password Pattern REG EX
    public static final Pattern PASSWORD_PATTERN =  Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-zA-Z])" +      //atleast 1 any letter
            "(?=\\S+$)" +           //no white spaces
            ".{6,}" +               //at least 6 characters
            "$");

    //Cart Room Database
    public static CartDatabase cartDatabase;
    public  static CartRepository cartRepository;

    // To Calculate Discount in Percentages
    public static double DiscountInPercentage(String mrp, String price){

        Double dis = (Double.parseDouble(mrp)-Double.parseDouble(price))/Double.parseDouble(mrp)*100;
        System.out.print(dis.intValue());
        return (dis.intValue());
    }
  //Check newtwork Available or Not
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




}
