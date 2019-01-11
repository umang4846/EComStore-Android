package com.appprocessors.ecomstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.appprocessors.ecomstore.adapter.CategoryProductAdapter;
import com.appprocessors.ecomstore.adapter.CategorySliderAdapter;
import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.Category;
import com.appprocessors.ecomstore.model.CategoryBanner;
import com.appprocessors.ecomstore.model.CategoryProducts;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.CommonOptionMenu;
import com.appprocessors.ecomstore.utils.PicassoImageLoadingService;
import com.appprocessors.ecomstore.utils.SimpleDividerItemDecoration;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.event.OnSlideClickListener;

public class CategoryProductsActivity extends CommonOptionMenu {

    IEStoreAPI mService;
    RecyclerView top_store_category;
    private static final String TAG = "CategoryProductsActivit";
    @BindView(R.id.NS_Category_Product)
    NestedScrollView NSCategoryProduct;
    @BindView(R.id.category_banner_slider)
    Slider categoryBannerSlider;
    private ShimmerFrameLayout mShimmerViewContainer;

    //Rx java
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Slider.init(new PicassoImageLoadingService(this));
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        if (getIntent() != null) {
            Category currentCategory = getIntent().getParcelableExtra("currentCategory");
            if (currentCategory != null) {
                mShimmerViewContainer.startShimmer();
                NSCategoryProduct.setVisibility(View.GONE);

                setTitle(currentCategory.getName());
                mService = Common.getAPI();

                //Category
                top_store_category = findViewById(R.id.lst_top_store);
                GridLayoutManager mGridLayoutManager = new GridLayoutManager(CategoryProductsActivity.this, 3);
                top_store_category.setLayoutManager(mGridLayoutManager);
                top_store_category.setHasFixedSize(true);
                top_store_category.setNestedScrollingEnabled(false);

                //Set Horizontal and Vertical Devider
                top_store_category.addItemDecoration(new SimpleDividerItemDecoration(1, 3));

                //Fetch Banner Images
                getCategoryBanner(currentCategory.get_id());

                //Load Category by Product
                LoadCategoryByProducts(currentCategory.get_id());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void getCategoryBanner(String menuid) {
        compositeDisposable.add(mService.getCategoryBanner(menuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CategoryBanner>>() {
                    @Override
                    public void accept(List<CategoryBanner> categoryBanners) throws Exception {
                        displaygetCategoryBanner(categoryBanners);
                    }
                }));

    }

    private void displaygetCategoryBanner(List<CategoryBanner> categoryBanners) {

               categoryBannerSlider.setAdapter(new CategorySliderAdapter(categoryBanners));
               categoryBannerSlider.setInterval(2500);
               categoryBannerSlider.setOnSlideClickListener(new OnSlideClickListener() {
                   @Override
                   public void onSlideClick(int position) {
                       CategoryBanner categoryBanner = categoryBanners.get(position);
                       Log.d(TAG, "banner Clicked: at " + position + " for " + categoryBanner.getProductCode());
                       Intent bannerIntent = new Intent(CategoryProductsActivity.this, ProductDetailsActivity.class);
                       bannerIntent.putExtra("productCode", categoryBanners.get(position).getProductCode());
                       startActivity(bannerIntent);
                   }
               });



        //Set Visibility of Progressbar and Relative Layout
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
        NSCategoryProduct.setVisibility(View.VISIBLE);
    }

    private void LoadCategoryByProducts(String menuid) {

        compositeDisposable.add(mService.getCategoryProductsByMenuId(menuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CategoryProducts>>() {
                    @Override
                    public void accept(List<CategoryProducts> categoryProducts) throws Exception {
                        displayCategoryTopStore(categoryProducts);
                    }

                }));


    }

    private void displayCategoryTopStore(List<CategoryProducts> categoryProducts) {

        CategoryProductAdapter categoryProductAdapter = new CategoryProductAdapter(this, categoryProducts);
        top_store_category.setAdapter(categoryProductAdapter);

    }


    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();

    }

    @Override
    public void onPause() {
        super.onPause();
        mShimmerViewContainer.stopShimmer();
    }

}
