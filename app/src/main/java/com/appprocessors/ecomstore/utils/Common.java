package com.appprocessors.ecomstore.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.appprocessors.ecomstore.model.categoryhome.CategoryHome;
import com.appprocessors.ecomstore.model.customer.Customer;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.retrofit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Common {

    private static String TAG = "Common";
    // For Tempoary Server link  is :  https://estorecom.000webhostapp.com/estore/
    //"http://10.0.3.2:8080/";

    public static final String BASE_URL = "http://192.168.20.46:1999";
    // private static final String BASE_URL = "http://10.0.3.2:8080/";
    //public static final String BASE_URL = "https://estoreapp.herokuapp.com";

    //Current Sub Category Products
    public static CategoryHome currentSubcategoryProducts = null;

    public static final int tagAddAddress = 20;
    public static final int tagUpdateAddress = 30;
    public static final String addUpdatedAddress = "addUpdatedAddress";
    public static final String addNewAddress = "addNewAddress";

    public static IEStoreAPI getAPI() {
        return RetrofitClient.getClient(BASE_URL).create(IEStoreAPI.class);
    }

    //Password Pattern REG EX
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-zA-Z])" +      //atleast 1 any letter
            "(?=\\S+$)" +           //no white spaces
            ".{6,}" +               //at least 6 characters
            "$");

    //Cart Room Database
  //  public static CartDatabase cartDatabase;
 //   public static CartRepository cartRepository;

    // To Calculate Discount in Percentages
    public static double DiscountInPercentage(double mrp, double price) {

        Double dis = (mrp - price) / mrp * 100;
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
    public static String getFormattedDate(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        //2nd of march 2015
        int day=cal.get(Calendar.DATE);

        if(!((day>10) && (day<19)))
            switch (day % 10) {
                case 1:
                    return new SimpleDateFormat("EEE, d'st' MMM yyyy").format(date);
                case 2:
                    return new SimpleDateFormat("EEE, d'nd' MMM yyyy").format(date);
                case 3:
                    return new SimpleDateFormat("EEE, d'rd' MMM yyyy").format(date);
                default:
                    return new SimpleDateFormat("EEE, d'th' MMM yyyy").format(date);
            }
        return new SimpleDateFormat("EEE, d'th' MMM yyyy").format(date);
    }



}
