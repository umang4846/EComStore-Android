package com.appprocessors.ecomstore.database.local;

import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import com.appprocessors.ecomstore.database.modeldb.Cart;

@Database(entities = {Cart.class},version = 1)
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
