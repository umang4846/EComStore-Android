package com.appprocessors.ecomstore.database.local;

import com.appprocessors.ecomstore.database.datasource.ICartDataSource;
import com.appprocessors.ecomstore.database.modeldb.Cart;

import java.util.List;

import io.reactivex.Flowable;

public class CartDataSource implements ICartDataSource {


    private CartDAO cartDAO;
    private static CartDataSource instance;

    public CartDataSource(CartDAO cartDAO){
        this.cartDAO=cartDAO;
    }

    public static CartDataSource getInstance(CartDAO cartDAO){
        if (instance==null){
            instance = new CartDataSource(cartDAO);
        }
        return instance;
    }

    @Override
    public Flowable<List<Cart>> getCartItems() {
        return cartDAO.getCartItems();
    }

    @Override
    public Flowable<List<Cart>> getCartItemsById(int cartItemId) {
        return cartDAO.getCartItemsById(cartItemId);
    }

    @Override
    public int countCartItems() {
        return cartDAO.countCartItems();
    }

    @Override
    public void emptyCart() {
           cartDAO.emptyCart();
    }

    @Override
    public void insertToCart(Cart cart) {
           cartDAO.insertToCart(cart);
    }

    @Override
    public void updateToCart(Cart cart) {
        cartDAO.updateToCart(cart);

    }

    @Override
    public void deleteCartItem(Cart cart) {
        cartDAO.deleteCartItem(cart);

    }

    @Override
    public int isAlreadyInCart(String productCode) {
        return cartDAO.isAlreadyInCart(productCode);
    }
}
