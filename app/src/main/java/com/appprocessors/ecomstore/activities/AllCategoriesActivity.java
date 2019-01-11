package com.appprocessors.ecomstore.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.Category;
import com.appprocessors.ecomstore.model.CategoryProducts;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class AllCategoriesActivity extends AppCompatActivity {

    String TAG = "AllCategoriesActivity ";

    @BindView(R.id.AC_tv1)
    TextView ACTv1;
    @BindView(R.id.AC_rv1)
    RecyclerView ACRv1;
    @BindView(R.id.AC_LL1)
    LinearLayout ACLL1;
    @BindView(R.id.AC_tv2)
    TextView ACTv2;
    @BindView(R.id.AC_rv2)
    RecyclerView ACRv2;
    @BindView(R.id.AC_LL2)
    LinearLayout ACLL2;
    @BindView(R.id.AC_tv3)
    TextView ACTv3;
    @BindView(R.id.AC_rv3)
    RecyclerView ACRv3;
    @BindView(R.id.AC_LL3)
    LinearLayout ACLL3;
    @BindView(R.id.AC_tv4)
    TextView ACTv4;
    @BindView(R.id.AC_rv4)
    RecyclerView ACRv4;
    @BindView(R.id.AC_LL4)
    LinearLayout ACLL4;
    @BindView(R.id.AC_tv5)
    TextView ACTv5;
    @BindView(R.id.AC_rv5)
    RecyclerView ACRv5;
    @BindView(R.id.AC_LL5)
    LinearLayout ACLL5;
    @BindView(R.id.AC_tv6)
    TextView ACTv6;
    @BindView(R.id.AC_rv6)
    RecyclerView ACRv6;
    @BindView(R.id.AC_LL6)
    LinearLayout ACLL6;
    @BindView(R.id.AC_tv7)
    TextView ACTv7;
    @BindView(R.id.AC_rv7)
    RecyclerView ACRv7;
    @BindView(R.id.AC_LL7)
    LinearLayout ACLL7;
    @BindView(R.id.AC_tv8)
    TextView ACTv8;
    @BindView(R.id.AC_rv8)
    RecyclerView ACRv8;
    @BindView(R.id.AC_LL8)
    LinearLayout ACLL8;
    @BindView(R.id.AC_tv9)
    TextView ACTv9;
    @BindView(R.id.AC_rv9)
    RecyclerView ACRv9;
    @BindView(R.id.AC_LL9)
    LinearLayout ACLL9;
    @BindView(R.id.AC_LL_all)
    LinearLayout ACLLAll;

    //API Stuff
    IEStoreAPI mService;
    //Rx java
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    //List of Cateogry and subCategory
    List<Category> listCategory ;
    List<CategoryProducts> listSubCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);
        ButterKnife.bind(this);
        setTitle("All Categories");

        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
/*        mService = Common.getAPI();

        //Get Category
        getCategory();
        //Get Sub Category
        getAllSubCategory();
        //Load All Category and Subcategory
        loadAllCategoriesAndSubCategories();

    }

    private void getCategory() {

        compositeDisposable.add(mService.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        if (listCategoty!=null) {
                            listCategoty = null;
                        }
                        listCategoty = categories;
                        Log.d(TAG, "accept: Categories ::"+new Gson().toJson(listCategoty));

                    }
                }));

    }

    private void getAllSubCategory() {

        compositeDisposable.add(mService.getAllSubCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CategoryProducts>>() {
                    @Override
                    public void accept(List<CategoryProducts> categoryProducts) throws Exception {
                        if (listSubCategoty!=null) {
                            listSubCategoty = null;
                        }
                        listSubCategoty = categoryProducts;
                        Log.d(TAG, "accept: SubCategories ::"+new Gson().toJson(listSubCategoty));

                    }
                }));

    }*/


  /*  private void loadAllCategoriesAndSubCategories() {

        Gson gson = new Gson();
        Category[] categories = gson.fromJson(new Gson().toJson(listCategoty), Category[].class);
       Log.d(TAG, "loadAllCategoriesAndSubCategories: Categories Size :"+categories.length);
       // Log.d(TAG, "loadAllCategoriesAndSubCategories: Categories Size :"+Arrays.asList(categories).size());
        //Log.d(TAG, "loadAllCategoriesAndSubCategories : JSON ::"+new Gson().toJson(listCategoty));

        String Cate[] = new String[Arrays.asList(categories).size()];

        for (int i = 0; i < Arrays.asList(categories).size(); i++) {
            Cate[i] = Arrays.asList(categories).get(i).name;
        }
        Log.d(TAG, "onCreate : " + Arrays.asList(Cate));

    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}
