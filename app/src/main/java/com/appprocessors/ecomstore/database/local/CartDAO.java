package com.appprocessors.ecomstore.database.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.appprocessors.ecomstore.database.modeldb.Cart;

import java.util.List;

import io.reactivex.Flowable;
@Dao
public interface CartDAO {

    @Query("SELECT * FROM cart")
    Flowable<List<Cart>> getCartItems();

    @Query("SELECT * FROM cart WHERE id=:cartItemId")
    Flowable<List<Cart>> getCartItemsById(int cartItemId);

    @Query("SELECT EXISTS(SELECT 1 FROM cart WHERE productCode=:productCode)")
    int isAlreadyInCart(String productCode);

    @Query("SELECT COUNT(*) FROM cart")
    int countCartItems();

    @Query("DELETE FROM cart")
    void emptyCart();

    @Insert
    void insertToCart(Cart cart);

    @Update
    void updateToCart(Cart cart);

    @Delete
    void deleteCartItem(Cart cart);
}
