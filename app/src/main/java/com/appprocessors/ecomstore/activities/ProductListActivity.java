package com.appprocessors.ecomstore.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.adapter.ProductListAdapter;
import com.appprocessors.ecomstore.model.CategoryProducts;
import com.appprocessors.ecomstore.model.Content;
import com.appprocessors.ecomstore.model.categoryhome.CategoryHome;
import com.appprocessors.ecomstore.paging.Injection;
import com.appprocessors.ecomstore.paging.ProductListViewModel;
import com.appprocessors.ecomstore.paging.Resource;
import com.appprocessors.ecomstore.paging.ViewModelFactory;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.CommonOptionMenu;
import com.appprocessors.ecomstore.utils.SimpleDividerItemDecoration;
import com.omega_r.libs.OmegaCenterIconButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.appprocessors.ecomstore.adapter.ProductListAdapter.SPAN_COUNT_ONE;
import static com.appprocessors.ecomstore.adapter.ProductListAdapter.SPAN_COUNT_TWO;

public class ProductListActivity extends CommonOptionMenu {

    String TAG = "ProductListActivity";
    IEStoreAPI mService;
    RecyclerView rv_product_list;
    @BindView(R.id.iv_product_list_list_to_grid)
    ImageView ivProductListListToGrid;
    @BindView(R.id.bnt_product_list_sort)
    OmegaCenterIconButton bntProductListSort;
    @BindView(R.id.btn_product_list_filter)
    OmegaCenterIconButton btnProductListFilter;

    GridLayoutManager gridLayoutManager;

    ProductListAdapter productListAdapter;

    private ProductListViewModel viewModel;

    int SORT_BY_ITEM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        mService = Common.getAPI();

        rv_product_list = findViewById(R.id.rv_product_list);
        ivProductListListToGrid.setImageDrawable(AnimatedVectorDrawableCompat.create(ProductListActivity.this, R.drawable.avd_grid_to_list));
        rv_product_list.getItemAnimator().setChangeDuration(100);
        gridLayoutManager = new GridLayoutManager(this, 1);

        rv_product_list.setLayoutManager(gridLayoutManager);
        rv_product_list.addItemDecoration(new SimpleDividerItemDecoration(2, 1));
        rv_product_list.setHasFixedSize(true);
        // draw network status and errors messages to fit the whole row(2 spans)
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (productListAdapter.getItemViewType(position)) {
                    case R.layout.item_progress:
                        return gridLayoutManager.getSpanCount();
                    default:
                        return 1;
                }
            }
        });

        if (getIntent() != null) {
            CategoryHome categoryHome = getIntent().getParcelableExtra("subcategory");
            if (categoryHome != null) {
                setTitle(categoryHome.getName());
                viewModel = obtainViewModel(this);

                ivProductListListToGrid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_ONE) {
                            gridLayoutManager.setSpanCount(SPAN_COUNT_TWO);
                            rv_product_list.addItemDecoration(new SimpleDividerItemDecoration(2, 2));
                        } else {
                            gridLayoutManager.setSpanCount(SPAN_COUNT_ONE);
                            rv_product_list.addItemDecoration(new SimpleDividerItemDecoration(2, 1));
                        }
                        productListAdapter.notifyItemRangeChanged(0, productListAdapter.getItemCount());
                        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_TWO) {
                            ivProductListListToGrid.setImageDrawable(AnimatedVectorDrawableCompat.create(ProductListActivity.this, R.drawable.avd_list_to_grid));
                        } else {
                            ivProductListListToGrid.setImageDrawable(AnimatedVectorDrawableCompat.create(ProductListActivity.this, R.drawable.avd_grid_to_list));

                        }

                    }

                });

                bntProductListSort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProductListActivity.this);
                        mBuilder.setTitle("SORT BY ");
                        final String sortby[] = new String[]{"Popularity", "New Arrival", "Price - Low to High", "Price - High to Low"};
                        mBuilder.setSingleChoiceItems(sortby, SORT_BY_ITEM, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (sortby[which].equalsIgnoreCase("Popularity")) {
                                    SORT_BY_ITEM = which;
                                    viewModel.setSortProductBy(sortby[which]);
                                } else if (sortby[which].equalsIgnoreCase("New Arrival")) {
                                    SORT_BY_ITEM = which;
                                    viewModel.setSortProductBy(sortby[which]);
                                } else if (sortby[which].equalsIgnoreCase("Price - Low to High")) {
                                    SORT_BY_ITEM = which;
                                    viewModel.setSortProductBy(sortby[which]);
                                } else if (sortby[which].equalsIgnoreCase("Price - High to Low")) {
                                    SORT_BY_ITEM = which;
                                    viewModel.setSortProductBy(sortby[which]);
                                }

                            }
                        });

                        Dialog dialog = mBuilder.create();
                        dialog.show();


                    }
                });
                //Load Category by Product0
                LoadProductListByMenuid(categoryHome.get_id());
            }

        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    public static ProductListViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = Injection.provideViewModelFactory(activity);
        return ViewModelProviders.of(activity, factory).get(ProductListViewModel.class);
    }


    private void LoadProductListByMenuid(String menuid) {

        Log.d(TAG, "LoadProductListByMenuid: " + menuid);

        viewModel = ViewModelProviders.of(this).get(ProductListViewModel.class);

        productListAdapter = new ProductListAdapter(this, gridLayoutManager, viewModel);

        // observe paged list
        viewModel.getPagedList().observe(this, new Observer<PagedList<Content>>() {
            @Override
            public void onChanged(PagedList<Content> movies) {
                productListAdapter.submitList(movies);

            }
        });

        // observe network state
        viewModel.getNetworkState().observe(this, new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {
                productListAdapter.setNetworkState(resource);
            }
        });

        rv_product_list.setAdapter(productListAdapter);

    }


}
