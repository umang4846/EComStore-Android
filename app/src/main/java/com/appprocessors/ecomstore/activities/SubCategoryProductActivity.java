package com.appprocessors.ecomstore.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.appprocessors.ecomstore.adapter.SubCategoryProductAdapter;
import com.appprocessors.ecomstore.adapter.TrendingSubCategoryAdapter;
import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.CategoryProducts;
import com.appprocessors.ecomstore.model.SubCategoryProducts;
import com.appprocessors.ecomstore.model.TrendingSubCategory;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.SimpleDividerItemDecoration;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SubCategoryProductActivity extends AppCompatActivity {

    IEStoreAPI mService;


    //Rx java
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @BindView(R.id.rv_sub_category_trending)
    RecyclerView rvSubCategoryTrending;
    @BindView(R.id.rv_sub_cateogty_top_store)
    RecyclerView rvSubCateogtyTopStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_product);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mService = Common.getAPI(this);
        //Sub Category Trending Recyclerview Stuff
        rvSubCategoryTrending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvSubCategoryTrending.setHasFixedSize(true);


        //Sub Category Recyclerview Stuff
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(SubCategoryProductActivity.this, 3);
        rvSubCateogtyTopStore.setLayoutManager(mGridLayoutManager);
        rvSubCateogtyTopStore.setHasFixedSize(true);
        rvSubCateogtyTopStore.setNestedScrollingEnabled(false);
        //Set Horizontal and Vertical Devider
        rvSubCateogtyTopStore.addItemDecoration(new SimpleDividerItemDecoration(1, 3));
        if (getIntent() != null) {
            CategoryProducts currentCategoryProducts = getIntent().getParcelableExtra("categoryProducts");
            if (currentCategoryProducts != null) {
                setTitle(currentCategoryProducts.getName());


                //Get Trending Products
                getSubCategoryTrending(currentCategoryProducts.get_id());
                //Load Category by Product by Id
                LoadSubCategoryByProducts(currentCategoryProducts.get_id());
            }

        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void getSubCategoryTrending(String menuid) {

        compositeDisposable.add(mService.getSubCategoryTrendingByMenuId(menuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TrendingSubCategory>>() {
                    @Override
                    public void accept(List<TrendingSubCategory> trendingSubCategories) throws Exception {
                        displayTrending(trendingSubCategories);

                    }
                }));

    }

    private void displayTrending(List<TrendingSubCategory> trendingSubCategories) {

        TrendingSubCategoryAdapter trendingAdapter = new TrendingSubCategoryAdapter(this, trendingSubCategories);
        rvSubCategoryTrending.setAdapter(trendingAdapter);

    }

    private void LoadSubCategoryByProducts(String menuid) {
        compositeDisposable.add(mService.getSubCategoryProductsByMenuId(menuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SubCategoryProducts>>() {
                    @Override
                    public void accept(List<SubCategoryProducts> categoryProducts) throws Exception {
                        displaySubCategoryTopStore(categoryProducts);
                    }

                }));


    }

    private void displaySubCategoryTopStore(List<SubCategoryProducts> subcategoryProducts) {

        SubCategoryProductAdapter categoryProductAdapter = new SubCategoryProductAdapter(this, subcategoryProducts);
        rvSubCateogtyTopStore.setAdapter(categoryProductAdapter);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
