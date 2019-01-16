package com.appprocessors.ecomstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.adapter.ProductDetailsSliderAdapter;
import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.database.modeldb.Cart;
import com.appprocessors.ecomstore.helper.NonScrollListView;
import com.appprocessors.ecomstore.model.ProductDetails;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.CommonOptionMenu;
import com.appprocessors.ecomstore.utils.PicassoImageLoadingService;
import com.google.gson.Gson;
import com.like.LikeButton;
import com.nex3z.notificationbadge.NotificationBadge;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ss.com.bannerslider.Slider;


public class ProductDetailsActivity extends CommonOptionMenu {

    String TAG = "ProductDetailsActivity";
    NotificationBadge badge;

    IEStoreAPI mServices;
    CompositeDisposable compositeDisposable;
    @BindView(R.id.star_button)
    LikeButton starButton;
    @BindView(R.id.productName)
    TextView productName;
    @BindView(R.id.tv_rating_product_list)
    TextView tvRatingProductList;
    @BindView(R.id.tv_total_rating_product_list)
    TextView tvTotalRatingProductList;
    @BindView(R.id.iv_assured)
    ImageView ivAssured;
    @BindView(R.id.tv_price_product_list)
    MoneyTextView tvPriceProductList;
    @BindView(R.id.tv_mrp_product_list)
    TextView tvMrpProductList;
    @BindView(R.id.tv_discount_product_list)
    MoneyTextView tvDiscountProductList;
    @BindView(R.id.LLMoney)
    LinearLayout LLMoney;
    @BindView(R.id.tv_inc_of_all_taxes)
    TextView tvIncOfAllTaxes;
    @BindView(R.id.tv_delivery_by)
    TextView tvDeliveryBy;
    @BindView(R.id.tv_delivery_pin_code)
    TextView tvDeliveryPinCode;
    @BindView(R.id.tv_pin_change)
    TextView tvPinChange;
    @BindView(R.id.iv_shipping_fee)
    ImageView ivShippingFee;
    @BindView(R.id.tv_shipping_fee)
    TextView tvShippingFee;
    @BindView(R.id.tv_shipping_price)
    TextView tvShippingPrice;
    @BindView(R.id.iv_replace)
    ImageView ivReplace;
    @BindView(R.id.tv_return)
    TextView tvReturn;
    @BindView(R.id.tv_return_in_days)
    TextView tvReturnInDays;
    @BindView(R.id.tv_return_know_more)
    TextView tvReturnKnowMore;
    @BindView(R.id.iv_cancellation)
    ImageView ivCancellation;
    @BindView(R.id.tv_cancellation)
    TextView tvCancellation;
    @BindView(R.id.tv_cancellation_allow)
    TextView tvCancellationAllow;
    @BindView(R.id.iv_installation)
    ImageView ivInstallation;
    @BindView(R.id.tv_installation)
    TextView tvInstallation;
    @BindView(R.id.tv_installation_available)
    TextView tvInstallationAvailable;
    @BindView(R.id.tv_installation_more)
    TextView tvInstallationMore;
    @BindView(R.id.iv_certified_seller)
    ImageView ivCertifiedSeller;
    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.listView_highlights)
    NonScrollListView listViewHighlights;
    @BindView(R.id.tv_product_all_details)
    TextView tvProductAllDetails;
    @BindView(R.id.LLProductInfo)
    LinearLayout LLProductInfo;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.btn_add_to_cart)
    Button btnAddToCart;
    @BindView(R.id.btn_buy_now)
    Button btnBuyNow;
    @BindView(R.id.LL_card_buy_button)
    LinearLayout LLCardBuyButton;
    @BindView(R.id.RL_ProductDetaiContent)
    RelativeLayout RLProductDetaiContent;
    @BindView(R.id.PB_productDetails)
    ProgressBar PBProductDetails;
    @BindView(R.id.product_details_slider)
    Slider productDetailsSlider;


    private int lay_height = 0;
    int height = 0;
    String productCode;
    List<String> listProductDesc = new ArrayList<>();

    ProductDetails currentProductDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        setTitle("");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        compositeDisposable = new CompositeDisposable();
        //API Service
        mServices = Common.getAPI();
        Slider.init(new PicassoImageLoadingService(this));
        //get product code from Intent
        if (getIntent() != null) {
            productCode = getIntent().getExtras().getString("productCode");
            if (productCode != null) {
                PBProductDetails.setVisibility(View.VISIBLE);
                RLProductDetaiContent.setVisibility(View.GONE);
                getProductDetails(productCode);
            }
        }

        //Check Item Already exist in cart or not
        if (Common.cartRepository.isAlreadyInCart(productCode) == 1) {
            btnAddToCart.setText("GO TO CART");

            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentCart = new Intent(ProductDetailsActivity.this, CartActivity.class);
                    startActivity(intentCart);
                }
            });
        }


        //Add to cart
        if (btnAddToCart.getText().equals("ADD TO CART")) {
            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Add Item to Room Database(SQLite)
                    //Create new Cart Item
                    try {
                        Cart cart = new Cart();
                        cart.productId = currentProductDetails.getId();
                        cart.productName = currentProductDetails.getProductName();
                        cart.brand = currentProductDetails.getBrand();
                        cart.brandCertifiedSeller = currentProductDetails.getBrandCertifiedSeller();
                        cart.capacity = currentProductDetails.getQuantity();
                        cart.estoreAssured = currentProductDetails.getEstoreAssured();
                        cart.genuineProduct = currentProductDetails.getGenuineProduct();
                        cart.idealFor = currentProductDetails.getIdealFor();
                        cart.imageMain = currentProductDetails.getImageMain();
                        cart.menuid = currentProductDetails.getMenuid();
                        cart.mrp = currentProductDetails.getMrp();
                        cart.price = currentProductDetails.getPrice();
                        cart.productCode = currentProductDetails.getProductName();
                        cart.shippingFee = currentProductDetails.getShippingFee();
                        cart.soldBy = currentProductDetails.getSoldBy();
                        cart.type = currentProductDetails.getType();

                        Common.cartRepository.insertToCart(cart);
                        Log.d(TAG, new Gson().toJson(cart));
                        Toast.makeText(ProductDetailsActivity.this, "Item Added to Cart", Toast.LENGTH_SHORT).show();
                        btnAddToCart.setText("GO TO CART");
                        btnAddToCart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (btnAddToCart.getText().equals("GO TO CART")) {
                                    Intent intentCart = new Intent(ProductDetailsActivity.this, CartActivity.class);
                                    startActivity(intentCart);
                                }
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(ProductDetailsActivity.this, "ERROR :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        //Buy Now
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addressIntent = new Intent(ProductDetailsActivity.this, MyAddressActivity.class);
                startActivity(addressIntent);
                /*OrderModel orderModel = new OrderModel();
                orderModel.setMenuid(Common.currentproductDetails.getMenuid());
                orderModel.setOrderDate(Common.currentproductDetails.getExpiryDate());
                orderModel.setProductName(Common.currentproductDetails.getProductName());
                orderModel.setProductPrice(Common.currentproductDetails.getMrp());
                orderModel.setImageUrl(Common.currentproductDetails.getImageMain());
                mServices.addOrder(orderModel).enqueue(new Callback<OrderModel>() {
                    @Override
                    public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                        Toast.makeText(ProductDetailsActivity.this, "Successfully Ordered !", Toast.LENGTH_SHORT).show();
                        btnBuyNow.setText("See Orders");
                        if (btnBuyNow.getText().equals("See Orders")) {
                            startActivity(new Intent(ProductDetailsActivity.this, MyOrdersActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderModel> call, Throwable t) {

                    }
                });*/


            }
        });

    }

    private void getProductDetails(String productCode) {

        compositeDisposable.add(mServices.getProductDetailsByProductCode(productCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProductDetails>() {
                               @Override
                               public void accept(ProductDetails productDetails) throws Exception {
                                   if (productDetails != null) {
                                       currentProductDetails = productDetails;
                                       SetImageSliderAndHighlights(productDetails);
                                       SetProductDetails(productDetails);
                                   }

                               }
                           }
                ));

    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.common_option_menu, menu);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        if (id == R.id.menu_search) {
            Intent cart = new Intent(ProductDetailsActivity.this, FavouritesActivity.class);
            startActivity(cart);
            return true;
        }
        if (id == R.id.menu_favourites) {
            Intent cart = new Intent(ProductDetailsActivity.this, FavouritesActivity.class);
            startActivity(cart);
            return true;
        }
        if (id == R.id.menu_cart) {
            Intent cart = new Intent(ProductDetailsActivity.this, CartActivity.class);
            startActivity(cart);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private void SetProductDetails(ProductDetails productDetails) {

        if (productDetails.getEstoreAssured()) {
            ivAssured.setVisibility(View.VISIBLE);
        } else {
            ivAssured.setVisibility(View.GONE);
        }
        productName.setText(productDetails.getProductName());
        tvMrpProductList.setText(getIndianRupee(productDetails.getMrp()));

        tvPriceProductList.setAmount(Float.parseFloat(productDetails.getPrice()));
        tvDiscountProductList.setAmount((float) Common.DiscountInPercentage(productDetails.getMrp(), productDetails.getPrice()));
        if (Common.DiscountInPercentage(productDetails.getMrp(), productDetails.getPrice()) <= 0) {
            tvDiscountProductList.setVisibility(View.GONE);
            tvMrpProductList.setVisibility(View.GONE);
        } else {
            tvDiscountProductList.setVisibility(View.VISIBLE);
            tvMrpProductList.setVisibility(View.VISIBLE);
        }
        tvSellerName.setText(productDetails.getSoldBy());
        if (productDetails.getBrandCertifiedSeller()) {
            ivCertifiedSeller.setVisibility(View.VISIBLE);
        } else {
            ivCertifiedSeller.setVisibility(View.GONE);
        }
        tvRatingProductList.setText((CharSequence) productDetails.getProductAverageRating());
        String totalRatings = String.valueOf(new StringBuilder("(").append(productDetails.getProductNoOfRatings()).append(")"));
        tvTotalRatingProductList.setText(totalRatings);

        //Setup for all Shipping, Return,Cancellation,Intsallation,COD

        //Shipping Fee
        if (productDetails.getShippingFree()) {
            tvShippingPrice.setText("FREE");
            tvShippingPrice.setTextColor(getResources().getColor(R.color.green));

        } else {
            String shippingFee = String.valueOf(new StringBuilder(getResources().getString(R.string.rupee)).append(" ").append(productDetails.getShippingFee()));
            tvShippingPrice.setText(shippingFee);
            tvShippingPrice.setTextColor(getResources().getColor(R.color.black));
        }

        //Retun
        if (productDetails.getReplaceAvailable()) {
            ivReplace.setImageDrawable(getResources().getDrawable(R.drawable.replace));
            String returnInDays = String.valueOf(new StringBuilder().append(productDetails.getReturnsInDays()).append(" ").append(Html.fromHtml("Days<sup>*</sup>")));
            tvReturnInDays.setText(returnInDays);
            tvReturnKnowMore.setText("More");
            tvReturnKnowMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProductDetailsActivity.this, "Return Allowed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            ivReplace.setImageDrawable(getResources().getDrawable(R.drawable.replace));
            tvReturnInDays.setText("Not Allowed");
            tvReturnKnowMore.setText("More");
            tvReturnKnowMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProductDetailsActivity.this, "Return Not Allowed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //Cancellation
        if (productDetails.getCancellationAvailable()) {
            ivCancellation.setVisibility(View.VISIBLE);
            tvCancellation.setVisibility(View.VISIBLE);
            tvCancellationAllow.setVisibility(View.VISIBLE);
            ivCancellation.setImageDrawable(getResources().getDrawable(R.drawable.cancellation));
            tvCancellation.setText("Cancellation");
            tvCancellationAllow.setText("Allowed");
        } else {
            ivCancellation.setVisibility(View.VISIBLE);
            tvCancellation.setVisibility(View.VISIBLE);
            tvCancellationAllow.setVisibility(View.VISIBLE);
            ivCancellation.setImageDrawable(getResources().getDrawable(R.drawable.cancellation));
            tvCancellation.setText("Cancellation");
            tvCancellationAllow.setText("Not Allowed");
        }
        //Installation & COD
        if (productDetails.getInstallationAvailable()) {
            ivInstallation.setVisibility(View.VISIBLE);
            tvInstallation.setVisibility(View.VISIBLE);
            tvInstallationAvailable.setVisibility(View.VISIBLE);
            tvInstallationMore.setVisibility(View.VISIBLE);

            ivInstallation.setImageDrawable(getResources().getDrawable(R.drawable.installation));
            tvInstallation.setText("Installation");
            tvInstallationAvailable.setText("Available");
            tvInstallationMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProductDetailsActivity.this, "Installation True", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            ivInstallation.setVisibility(View.VISIBLE);
            tvInstallation.setVisibility(View.VISIBLE);
            tvInstallationAvailable.setVisibility(View.VISIBLE);
            tvInstallationMore.setVisibility(View.VISIBLE);
            ivInstallation.setImageDrawable(getResources().getDrawable(R.drawable.replace));
            tvInstallation.setText("Return");
            String returnInDaysnew = String.valueOf(new StringBuilder().append(productDetails.getReturnsInDays()).append(" ").append(Html.fromHtml("Days<sup>*</sup>")));
            tvInstallationAvailable.setText(returnInDaysnew);
            tvInstallationMore.setText("More");

            tvInstallationMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProductDetailsActivity.this, "Return True", Toast.LENGTH_SHORT).show();

                }
            });
            ivReplace.setImageDrawable(getResources().getDrawable(R.drawable.cod));
            tvReturn.setText("COD");
            if (currentProductDetails.getCodAvailable()) {
                tvReturnInDays.setText("Available");
            } else {
                tvReturnInDays.setText("N/A");
            }
            tvReturnKnowMore.setVisibility(View.GONE);

        }
    }

    private void SetImageSliderAndHighlights(ProductDetails productDetails) {
        //set Product Images in Slider
        List<String> Imageslist = new ArrayList<>(productDetails.getProductImages());
        Log.d(TAG,  "SetImageSliderAndHighlights: "+Imageslist);
        productDetailsSlider.setAdapter(new ProductDetailsSliderAdapter(Imageslist));
        productDetailsSlider.setInterval(0);
        //Product Highligh in Listview
        listProductDesc.addAll(productDetails.getProductHighlight());
        NonScrollListView listView = findViewById(R.id.listView_highlights);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_list_bullet, listProductDesc));
        //Set Visibility of Progressbar and Relative Layout
        PBProductDetails.setVisibility(View.GONE);
        RLProductDetaiContent.setVisibility(View.VISIBLE);
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

    public static String getIndianRupee(String value) {
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        return format.format(new BigDecimal(value));
    }


    @Override
    protected void onResume() {
        if (Common.cartRepository.isAlreadyInCart(productCode) == 1) {
            btnAddToCart.setText("GO TO CART");
            updateCartCount();
            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btnAddToCart.getText().equals("GO TO CART")) {
                        Intent intentCart = new Intent(ProductDetailsActivity.this, CartActivity.class);
                        startActivity(intentCart);
                    }
                }
            });
        } else {
            btnAddToCart.setText("ADD TO CART");
            updateCartCount();
            //Add Item to Room Database(SQLite)
            //Create new Cart Item
            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btnAddToCart.getText().equals("ADD TO CART")) {
                        try {
                            Cart cart = new Cart();
                            cart.productId = currentProductDetails.getId();
                            cart.productName = currentProductDetails.getProductName();
                            cart.brand = currentProductDetails.getBrand();
                            cart.brandCertifiedSeller = currentProductDetails.getBrandCertifiedSeller();
                            cart.capacity = currentProductDetails.getQuantity();
                            cart.estoreAssured = currentProductDetails.getEstoreAssured();
                            cart.genuineProduct = currentProductDetails.getGenuineProduct();
                            cart.idealFor = currentProductDetails.getIdealFor();
                            cart.imageMain = currentProductDetails.getImageMain();
                            cart.menuid = currentProductDetails.getMenuid();
                            cart.mrp = currentProductDetails.getMrp();
                            cart.price = currentProductDetails.getPrice();
                            cart.productCode = currentProductDetails.getProductCode();
                            cart.shippingFee = currentProductDetails.getShippingFee();
                            cart.soldBy = currentProductDetails.getSoldBy();
                            cart.type = currentProductDetails.getType();

                            Common.cartRepository.insertToCart(cart);
                            Log.d(TAG, new Gson().toJson(cart));
                            Toast.makeText(ProductDetailsActivity.this, "Item Added to Cart", Toast.LENGTH_SHORT).show();
                            updateCartCount();
                            btnAddToCart.setText("GO TO CART");
                            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (btnAddToCart.getText().equals("GO TO CART")) {
                                        Intent intentCart = new Intent(ProductDetailsActivity.this, CartActivity.class);
                                        startActivity(intentCart);
                                    }
                                }
                            });
                        } catch (Exception e) {
                            Toast.makeText(ProductDetailsActivity.this, "ERROR :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


}
