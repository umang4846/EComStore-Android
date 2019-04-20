package com.appprocessors.ecomstore.utils;

import android.app.AlertDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.appprocessors.ecomstore.activities.CartActivity;
import com.appprocessors.ecomstore.activities.LoginSignUp;
import com.appprocessors.ecomstore.activities.WishlistActivity;
import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.retrofit.NetworkEvent;
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
                Intent search = new Intent(CommonOptionMenu.this, WishlistActivity.class);
                startActivity(search);
                return true;

            case R.id.menu_favourites:
                Intent favourites = new Intent(CommonOptionMenu.this, WishlistActivity.class);
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
     /*   runOnUiThread(new Runnable() {
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
        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();

        updateCartCount();

        NetworkEvent.getInstance().register(this,
                networkState -> {
                    switch (networkState) {
                        case NO_INTERNET:
                            displayErrorDialog(getString(R.string.generic_no_internet_title),
                                    getString(R.string.generic_no_internet_desc));
                            break;

                        case NO_RESPONSE:
                            displayErrorDialog(getString(R.string.generic_http_error_title),
                                    getString(R.string.generic_http_error_desc));
                            break;

                        case UNAUTHORISED:
                            //redirect to login screen - if session expired
                            Toast.makeText(getApplicationContext(), R.string.error_login_expired, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(this, LoginSignUp.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            break;
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        NetworkEvent.getInstance().unregister(this);
    }


    public void displayErrorDialog(String title,
                                   String desc) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(desc)
                .setCancelable(false)
                .setPositiveButton("Ok",
                        (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })
                .show();
    }
}
