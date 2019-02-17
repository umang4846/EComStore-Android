package com.appprocessors.ecomstore.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.adapter.CategoryAdapter;
import com.appprocessors.ecomstore.adapter.HomeSliderAdapter;
import com.appprocessors.ecomstore.adapter.TrendingAdapter;
import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.database.datasource.CartRepository;
import com.appprocessors.ecomstore.database.local.CartDataSource;
import com.appprocessors.ecomstore.database.local.CartDatabase;
import com.appprocessors.ecomstore.interfaces.UiUpdaterListener;
import com.appprocessors.ecomstore.model.Banner;
import com.appprocessors.ecomstore.model.Category;
import com.appprocessors.ecomstore.model.Trending;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.PicassoImageLoadingService;
import com.appprocessors.ecomstore.utils.SimpleDividerItemDecoration;
import com.appprocessors.ecomstore.utils.Utility;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.event.OnSlideClickListener;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UiUpdaterListener {
    NavigationView navigationView;
    IEStoreAPI mService;
    RecyclerView lst_trending;
    RecyclerView lst_category;
    private static final String TAG = "HomeActivity";
    @BindView(R.id.banner_slider)
    Slider bannerSlider;
    NotificationBadge badge;

    @BindView(R.id.NS_Home)
    NestedScrollView NSHome;

    private Toast mToast;
    private NetworkReceiver mNetworkReceiver;
    private IntentFilter mNetworkIntentFilter;
    private ShimmerFrameLayout mShimmerViewContainer;


    //Rx java
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @BindView(R.id.LL_Home_Content)
    LinearLayout LLHomeContent;
    @BindView(R.id.LL_Product_List_Error)
    LinearLayout LLHomeError;
    @BindView(R.id.ivErrorIcon)
    ImageView ivErrorIcon;
    @BindView(R.id.tvError)
    TextView tvError;
    @BindView(R.id.btnTryAgain)
    Button btnTryAgain;
    @BindView(R.id.loadingIndicator)
    ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmer();
        NSHome.setVisibility(View.GONE);
        Slider.init(new PicassoImageLoadingService(this));
        mService = Common.getAPI();

        mNetworkReceiver = new NetworkReceiver();
        mNetworkIntentFilter = new IntentFilter();
        mNetworkIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        btnTryAgain.setVisibility(View.VISIBLE);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.isNetworkAvailable(HomeActivity.this)) {
                    Toast.makeText(HomeActivity.this, "Network ComeBack !!!", Toast.LENGTH_SHORT).show();
                } else {
                    showToast(getString(R.string.error_message_internet));
                }
            }
        });

        //Categoty
        lst_category = findViewById(R.id.lst_category);
        int mNoOfColumns = Utility.calculateNoOfColumns(getApplicationContext());//For Auto - Fit Columns

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(HomeActivity.this, 3);
        //  lst_category.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        lst_category.setLayoutManager(mGridLayoutManager);
        lst_category.setHasFixedSize(true);
        lst_category.setNestedScrollingEnabled(false);
        //Set Horizontal and Vertical Devider
   /*     Drawable horizontalDivider = ContextCompat.getDrawable(this, R.drawable.gridview_devider);
        Drawable verticalDivider = ContextCompat.getDrawable(this, R.drawable.gridview_devider);*/
        lst_category.addItemDecoration(new SimpleDividerItemDecoration(1, 3));


        //Trending Products
        lst_trending = findViewById(R.id.lst_trending);
        lst_trending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        lst_trending.setHasFixedSize(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_home).setCheckable(true);
        //Fetch Banner Images
        getBannerImage();

        //Get Category
        getCategory();

        //Get Trending Products
        getTrending();

        //Init Database
        initDB();


    }


    private void initDB() {

        Common.cartDatabase = CartDatabase.getInstance(this);
        Common.cartRepository = CartRepository.getInstance(CartDataSource.getInstance(Common.cartDatabase.cartDAO()));
    }

    private void getCategory() {

        compositeDisposable.add(mService.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        displayCategory(categories);
                    }
                }));

    }

    private void displayCategory(List<Category> categories) {

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
        lst_category.setAdapter(categoryAdapter);


    }


    private void getTrending() {
        compositeDisposable.add(mService.getTrending()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Trending>>() {
                    @Override
                    public void accept(List<Trending> trendings) throws Exception {
                        displayTrending(trendings);
                    }
                }));


    }

    private void displayTrending(List<Trending> trendings) {

        TrendingAdapter trendingAdapter = new TrendingAdapter(this, trendings);
        lst_trending.setAdapter(trendingAdapter);

    }

    private void getBannerImage() {
        compositeDisposable.add(mService.getBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Banner>>() {
                               @Override
                               public void accept(List<Banner> banners) throws Exception {
                                   displayImage(banners);
                               }

                           }
                ));

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    private void displayImage(List<Banner> banners) {

        bannerSlider.setAdapter(new HomeSliderAdapter(banners));
        bannerSlider.setInterval(2500);
        bannerSlider.setOnSlideClickListener(new OnSlideClickListener() {
            @Override
            public void onSlideClick(int position) {
                Banner banner = banners.get(position);
                Log.d(TAG, "banner Clicked: at " + position + " for " + banner.getProductCode());
                Intent bannerIntent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                bannerIntent.putExtra("productCode", banner.getProductCode());
                startActivity(bannerIntent);
            }
        });
        // stop animating Shimmer and show the layout
        mShimmerViewContainer.stopShimmer();
        NSHome.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        final View view = menu.findItem(R.id.menu_cart).getActionView();
        badge = view.findViewById(R.id.home_cart_badge);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menu.findItem(R.id.menu_cart));
            }
        });
        updateCartCount();
        return true;
    }

    private void updateCartCount() {

        if (badge == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Common.cartRepository.countCartItems() == 0) {
                    badge.setVisibility(View.GONE);
                } else {
                    badge.setVisibility(View.VISIBLE);
                    badge.setText(String.valueOf(Math.min(Common.cartRepository.countCartItems(), 9)));
                    if (Common.cartRepository.countCartItems() > 9) {
                        badge.setText("9+");
                    }
                    //badge.setText(String.valueOf(Common.cartRepository.countCartItems()));
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_cart) {
            Intent cart = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(cart);
            return true;
        }
        if (id == R.id.menu_notification) {
            Intent cart = new Intent(HomeActivity.this, NotificationActivity.class);
            startActivity(cart);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        item.setChecked(true);

        if (id == R.id.nav_home) {
            Intent intentMyAccount = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intentMyAccount);
        } else if (id == R.id.nav_categories) {
            Intent intentMyAccount = new Intent(HomeActivity.this, AllCategoriesActivity.class);
            startActivity(intentMyAccount);
        } else if (id == R.id.nav_myorders) {
            Intent intentMyAccount = new Intent(HomeActivity.this, MyOrdersActivity.class);
            startActivity(intentMyAccount);

        } else if (id == R.id.nav_mycart) {
            Intent intentMyAccount = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intentMyAccount);

        } else if (id == R.id.nav_wishlist) {
            Intent intentMyAccount = new Intent(HomeActivity.this, FavouritesActivity.class);
            startActivity(intentMyAccount);

        } else if (id == R.id.nav_settings) {
            Intent intentMyAccount = new Intent(HomeActivity.this, MyAccountActivity.class);
            startActivity(intentMyAccount);

        } else if (id == R.id.nav_notifications) {
            Intent intentMyAccount = new Intent(HomeActivity.this, NotificationActivity.class);
            startActivity(intentMyAccount);
        } else if (id == R.id.nav_helpfaq) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_home);
        updateCartCount();
        registerReceiver(mNetworkReceiver, mNetworkIntentFilter);
        mShimmerViewContainer.startShimmer();


    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkReceiver);
        mShimmerViewContainer.stopShimmer();

    }

    @Override
    protected void onRestart() {
        navigationView.setCheckedItem(R.id.nav_home);
        super.onRestart();
    }

    @Override
    public void error(String errorMessage) {
        showError(errorMessage);
    }

    @Override
    public void updateViews(boolean isLoading) {

        if (isLoading) {
            loadingIndicator.setVisibility(View.VISIBLE);
        } else {
            loadingIndicator.setVisibility(View.INVISIBLE);
            if (LLHomeContent.getVisibility() == View.INVISIBLE) {
                LLHomeContent.setVisibility(View.VISIBLE);
            }
        }
        LLHomeError.setVisibility(View.INVISIBLE);
    }


    private class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (Common.isNetworkAvailable(context)) {
                if (LLHomeContent.getVisibility() != View.VISIBLE) {
                    LLHomeContent.setVisibility(View.VISIBLE);
                }
                LLHomeError.setVisibility(View.GONE);


            } else {
                showError(getString(R.string.error_message_internet));
            }
        }
    }

    private void showError(String errMessage) {
        int iconResource = R.drawable.ic_alert_black_24dp;
        if (errMessage.equals(getString(R.string.error_message_internet))) {
            iconResource = R.drawable.no_internet;
        }
        loadingIndicator.setVisibility(View.INVISIBLE);
        LLHomeContent.setVisibility(View.INVISIBLE);
        LLHomeError.setVisibility(View.VISIBLE);

        tvError.setText(errMessage);
        ivErrorIcon.setImageResource(iconResource);
    }

    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
