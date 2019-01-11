package com.appprocessors.ecomstore.database.datasource;

import com.appprocessors.ecomstore.database.modeldb.Cart;

import java.util.List;

import io.reactivex.Flowable;

public class CartRepository implements ICartDataSource {

    private ICartDataSource iCartDataSource;

    public CartRepository(ICartDataSource iCartDataSource) {
        this.iCartDataSource = iCartDataSource;
    }

    private static CartRepository instance;

    public static CartRepository getInstance(ICartDataSource iCartDataSource){

        if (instance==null){
            instance = new CartRepository(iCartDataSource);
        }
        return instance;
    }


    @Override
    public Flowable<List<Cart>> getCartItems() {
        return iCartDataSource.getCartItems();
    }

    @Override
    public Flowable<List<Cart>> getCartItemsById(int cartItemId) {
        return iCartDataSource.getCartItemsById(cartItemId);
    }

    @Override
    public int countCartItems() {
        return iCartDataSource.countCartItems();
    }

    @Override
    public void emptyCart() {
        iCartDataSource.emptyCart();
    }

    @Override
    public void insertToCart(Cart cart) {
        iCartDataSource.insertToCart(cart);
    }

    @Override
    public void updateToCart(Cart cart) {
      iCartDataSource.updateToCart(cart);
    }

    @Override
    public void deleteCartItem(Cart cart) {
      iCartDataSource.deleteCartItem(cart);
    }

    @Override
    public int isAlreadyInCart(String productCode) {
        return iCartDataSource.isAlreadyInCart(productCode);
    }
}
