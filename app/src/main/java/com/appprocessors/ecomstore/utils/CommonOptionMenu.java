package com.appprocessors.ecomstore.utils;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.appprocessors.ecomstore.activities.CartActivity;
import com.appprocessors.ecomstore.activities.FavouritesActivity;
import com.appprocessors.ecomstore.R;
import com.nex3z.notificationbadge.NotificationBadge;

public class CommonOptionMenu extends AppCompatActivity {
    NotificationBadge badge;

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.common_option_menu, menu);
        final View view = menu.findItem(R.id.menu_cart).getActionView();
        badge =  view.findViewById(R.id.home_cart_badge);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menu.findItem(R.id.menu_cart));
            }
        });
        updateCartCount();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_search:
                Intent search = new Intent(CommonOptionMenu.this,FavouritesActivity.class);
                startActivity(search);
                return true;

            case R.id.menu_favourites:
                Intent favourites = new Intent(CommonOptionMenu.this,FavouritesActivity.class);
                startActivity(favourites);
                return true;

            case R.id.menu_cart:
                Intent cart = new Intent(CommonOptionMenu.this,CartActivity.class);
                startActivity(cart);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void updateCartCount() {

        if (badge==null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Common.cartRepository.countCartItems()==0){
                    badge.setVisibility(View.GONE);
                }
                else
                {
                    badge.setVisibility(View.VISIBLE);
                    badge.setText(String.valueOf(Math.min(Common.cartRepository.countCartItems(), 99)));
                    //badge.setText(String.valueOf(Common.cartRepository.countCartItems()));
                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        updateCartCount();
    }
}
