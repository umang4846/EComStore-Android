package com.appprocessors.ecomstore.database.local;

import android.content.Context;

import com.appprocessors.ecomstore.database.modeldb.Cart;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Cart.class},version = 1,exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {

    public abstract CartDAO cartDAO();

    private static CartDatabase instance;

    public  static CartDatabase getInstance(Context context){

        if (instance==null)
            instance = Room.databaseBuilder(context,CartDatabase.class,"ESTORE")
                    .allowMainThreadQueries().build();

            return instance;


    }


}
