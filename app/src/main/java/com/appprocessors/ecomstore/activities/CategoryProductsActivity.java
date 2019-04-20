package com.appprocessors.ecomstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.adapter.CategoryProductAdapter;
import com.appprocessors.ecomstore.adapter.CategorySliderAdapter;
import com.appprocessors.ecomstore.model.categoryhome.CategoryHome;
import com.appprocessors.ecomstore.model.pictureslider.PictureSlider;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.CommonOptionMenu;
import com.appprocessors.ecomstore.utils.PicassoImageLoadingService;
import com.appprocessors.ecomstore.utils.SimpleDividerItemDecoration;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Slider.init(new PicassoImageLoadingService(this));
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        if (getIntent().getExtras() != null) {
            String currentCategoryId = getIntent().getExtras().getString("categoryId");
            String currentCategoryName = getIntent().getExtras().getString("categoryName");
            if (currentCategoryId != null && currentCategoryName != null) {
                mShimmerViewContainer.startShimmer();
                mShimmerViewContainer.setVisibility(View.VISIBLE);
                NSCategoryProduct.setVisibility(View.GONE);
                setTitle(currentCategoryName);
                mService = Common.getAPI(this);

                //Category
                top_store_category = findViewById(R.id.lst_top_store);
                GridLayoutManager mGridLayoutManager = new GridLayoutManager(CategoryProductsActivity.this, 3);
                top_store_category.setLayoutManager(mGridLayoutManager);
                top_store_category.setHasFixedSize(true);
                top_store_category.setNestedScrollingEnabled(false);

                //Set Horizontal and Vertical Devider
                top_store_category.addItemDecoration(new SimpleDividerItemDecoration(1, 3));

                //Fetch Banner Images
                getCategoryBanner(currentCategoryId);

                //Load Category by Product
                LoadCategoryByProducts(currentCategoryId);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void getCategoryBanner(String parentCategoryId) {
        compositeDisposable.add(mService.getCategoryPictureSlider(parentCategoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PictureSlider>>() {
                    @Override
                    public void accept(List<PictureSlider> pictureSliderList) throws Exception {
                        displaygetCategoryBanner(pictureSliderList);
                    }
                }));

    }

    private void displaygetCategoryBanner(List<PictureSlider> pictureSliderLists) {

        categoryBannerSlider.setAdapter(new CategorySliderAdapter(pictureSliderLists));
        categoryBannerSlider.setInterval(2500);
        categoryBannerSlider.setOnSlideClickListener(new OnSlideClickListener() {
            @Override
            public void onSlideClick(int position) {

                String productId = "";
                for (int i = 0; i < pictureSliderLists.size(); i++) {
                    if (pictureSliderLists.get(position).getGenericAttributes().get(i).getKey().equals("ProductId")) {
                        productId = pictureSliderLists.get(position).getGenericAttributes().get(i).getValue();
                        break;
                    }
                }
                if (productId != null) {
                    Log.d(TAG, "banner Clicked: at " + position + " for " + productId);
                    Intent bannerIntent = new Intent(CategoryProductsActivity.this, ProductDetailsActivity.class);
                    bannerIntent.putExtra("productCode", productId);
                    startActivity(bannerIntent);
                }
            }
        });


    }

    private void LoadCategoryByProducts(String currentCategoryId) {

        compositeDisposable.add(mService.getCategoryProductsByParentCategoryId(currentCategoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CategoryHome>>() {
                    @Override
                    public void accept(List<CategoryHome> categoryHomeList) throws Exception {
                        displayCategoryTopStore(categoryHomeList);
                    }

                }));


    }

    private void displayCategoryTopStore(List<CategoryHome> categoryHomeList) {

        CategoryProductAdapter categoryProductAdapter = new CategoryProductAdapter(this, categoryHomeList);
        top_store_category.setAdapter(categoryProductAdapter);

        //Set Visibility of Progressbar and Relative Layout
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
        NSCategoryProduct.setVisibility(View.VISIBLE);
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
