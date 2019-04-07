package com.appprocessors.ecomstore.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.adapter.CategoryAdapter;
import com.appprocessors.ecomstore.adapter.HomeSliderAdapter;
import com.appprocessors.ecomstore.adapter.TrendingAdapter;
import com.appprocessors.ecomstore.interfaces.UiUpdaterListener;
import com.appprocessors.ecomstore.model.categoryhome.CategoryHome;
import com.appprocessors.ecomstore.model.pictureslider.PictureSlider;
import com.appprocessors.ecomstore.model.product.ProductList;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.PicassoImageLoadingService;
import com.appprocessors.ecomstore.utils.SimpleDividerItemDecoration;
import com.appprocessors.ecomstore.utils.UserSessionManager;
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

    // User Session Manager Class
    UserSessionManager session;

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
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mService = Common.getAPI();

        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        //Categoty
        lst_category = findViewById(R.id.lst_category);
        int mNoOfColumns = Utility.calculateNoOfColumns(getApplicationContext());//For Auto - Fit Columns

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(HomeActivity.this, 3);
        //  lst_category.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        lst_category.setLayoutManager(mGridLayoutManager);
        lst_category.setHasFixedSize(true);
        lst_category.setNestedScrollingEnabled(false);
        //Set Horizontal and Vertical Devider
           /* Drawable horizontalDivider = ContextCompat.getDrawable(this, R.drawable.gridview_devider);
            Drawable verticalDivider = ContextCompat.getDrawable(this, R.drawable.gridview_devider);*/
        lst_category.addItemDecoration(new SimpleDividerItemDecoration(1, 3));
        Slider.init(new PicassoImageLoadingService(this));

        //Trending Products
        lst_trending = findViewById(R.id.lst_trending);
        lst_trending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        lst_trending.setHasFixedSize(true);

        mNetworkReceiver = new NetworkReceiver();
        mNetworkIntentFilter = new IntentFilter();
        mNetworkIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        btnTryAgain.setVisibility(View.VISIBLE);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.isNetworkAvailable(HomeActivity.this)) {
                    startLoadingData();
                    Toast.makeText(HomeActivity.this, "Network ComeBack !!!", Toast.LENGTH_SHORT).show();
                } else {
                    showError(getString(R.string.error_message_internet));
                    showToast(getString(R.string.error_message_internet));
                }
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_home).setCheckable(true);

        setCustomerDetails();


        //Init Database
        //initDB();


    }

    private void setCustomerDetails() {

        View header = navigationView.getHeaderView(0);
        TextView tvFullName = (TextView) header.findViewById(R.id.tvFullName);
        TextView tvPhone = (TextView) header.findViewById(R.id.tvPhone);

        if (session.isUserLoggedIn()) {

            if (session.getUserDetails().get(UserSessionManager.KEY_PHONE) != null && session.getUserDetails().get(UserSessionManager.KEY_FIRST_NAME) != null && session.getUserDetails().get(UserSessionManager.KEY_LAST_NAME) != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Hi ! ").append(session.getUserDetails().get(UserSessionManager.KEY_FIRST_NAME)).append(" ").append(session.getUserDetails().get(UserSessionManager.KEY_LAST_NAME));
                tvFullName.setText(stringBuilder.toString());
                tvPhone.setText(session.getUserDetails().get(UserSessionManager.KEY_PHONE));
            } else {
                // user is not logged in redirect him to Login Activity
                Intent i = new Intent(this, LoginSignUp.class);

                // Closing all the Activities from stack
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                // Staring Login Activity
                startActivity(i);

            }
        } else {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(this, LoginSignUp.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            // Staring Login Activity
            startActivity(i);

        }
    }


    private void startLoadingData() {

        mShimmerViewContainer.setVisibility(View.VISIBLE);
        NSHome.setVisibility(View.GONE);
        LLHomeError.setVisibility(View.GONE);
        mShimmerViewContainer.startShimmer();


        //Fetch Banner Images
        getBannerImage();

        //Get Category
        getCategory();

        //Get Trending Products
        getTrending();

    }


/*    private void initDB() {

        Common.cartDatabase = CartDatabase.getInstance(this);
        Common.cartRepository = CartRepository.getInstance(CartDataSource.getInstance(Common.cartDatabase.cartDAO()));
    }*/

    private void getCategory() {
        compositeDisposable.add(mService.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CategoryHome>>() {
                    @Override
                    public void accept(List<CategoryHome> categoryHomes) throws Exception {
                        displayCategory(categoryHomes);
                    }
                }));

    }

    private void displayCategory(List<CategoryHome> categories) {

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
        lst_category.setAdapter(categoryAdapter);

    }


    private void getTrending() {
        compositeDisposable.add(mService.getTrending(6)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ProductList>>() {
                    @Override
                    public void accept(List<ProductList> trendings) throws Exception {
                        displayTrending(trendings);
                    }
                }));


    }

    private void displayTrending(List<ProductList> trendings) {

        TrendingAdapter trendingAdapter = new TrendingAdapter(this, trendings);
        lst_trending.setAdapter(trendingAdapter);
        stopShimmerAnimation();

    }

    private void getBannerImage() {
        compositeDisposable.add(mService.getBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PictureSlider>>() {
                               @Override
                               public void accept(List<PictureSlider> pictureSliderList) throws Exception {
                                   displayImage(pictureSliderList);
                               }
                           }
                ));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    private void displayImage(List<PictureSlider> pictureSliders) {

        bannerSlider.setAdapter(new HomeSliderAdapter(pictureSliders));
        bannerSlider.setInterval(2500);
        bannerSlider.setOnSlideClickListener(new OnSlideClickListener() {
            @Override
            public void onSlideClick(int position) {
                String productId = "";
                for (int i = 0; i < pictureSliders.get(position).getGenericAttributes().size(); i++) {
                    if (pictureSliders.get(position).getGenericAttributes().get(i).getKey().equalsIgnoreCase("ProductId")) {
                        productId = pictureSliders.get(position).getGenericAttributes().get(i).getValue();
                        break;
                    }
                }
                if (productId != null) {
                    Log.e(TAG, "banner Clicked: at " + position + " for " + productId);
                    Intent bannerIntent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                    bannerIntent.putExtra("productCode", productId);
                    startActivity(bannerIntent);
                } else {
                    Toast.makeText(HomeActivity.this, "Product Id is Null !", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void stopShimmerAnimation() {
        // stop animating Shimmer and show the layout
        mShimmerViewContainer.stopShimmer();
        NSHome.setVisibility(View.VISIBLE);
        LLHomeError.setVisibility(View.GONE);
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
        // updateCartCount();
        return true;
    }

    private void updateCartCount() {

        if (badge == null) return;
       /* runOnUiThread(new Runnable() {
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
        });*/

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
        // updateCartCount();
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
                startLoadingData();
            } else {
                showError(getString(R.string.error_message_internet));
            }
        }

    }

    private void showError(String errMessage) {
        int iconResource = R.drawable.ic_alert_black_24dp;
        if (errMessage.equals(getString(R.string.error_message_internet))) {
            iconResource = R.drawable.no_internet;
            errMessage = "No Internet Connection !";
        }
        loadingIndicator.setVisibility(View.GONE);
        NSHome.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.GONE);
        LLHomeError.setVisibility(View.VISIBLE);
   /*     FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER | Gravity.CENTER_VERTICAL;*/


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;
        params.gravity = Gravity.CENTER;
        LLHomeError.setLayoutParams(params);

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
