package com.appprocessors.ecomstore.database.datasource;

import com.appprocessors.ecomstore.database.modeldb.Cart;

import java.util.List;

import io.reactivex.Flowable;

public interface ICartDataSource {

    Flowable<List<Cart>> getCartItems();
    Flowable<List<Cart>> getCartItemsById(int cartItemId);
    int countCartItems();
    void emptyCart();
    void insertToCart(Cart cart);
    void updateToCart(Cart cart);
    void deleteCartItem(Cart cart);
    int isAlreadyInCart(String productCode);
}
