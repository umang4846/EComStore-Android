package com.appprocessors.ecomstore.activities;

import android.content.Intent;
import android.os.Bundle;
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

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.adapter.ProductDetailsSliderAdapter;
import com.appprocessors.ecomstore.helper.NonScrollListView;
import com.appprocessors.ecomstore.model.order.OrderItem;
import com.appprocessors.ecomstore.model.product.Product;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.CommonOptionMenu;
import com.appprocessors.ecomstore.utils.PicassoImageLoadingService;
import com.like.LikeButton;
import com.nex3z.notificationbadge.NotificationBadge;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
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
    @BindView(R.id.product_details_slider)
    Slider productDetailsSlider;
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
    @BindView(R.id.tv_freeSheeping)
    TextView tvFreeSheeping;
    @BindView(R.id.tv_cod)
    TextView tvCod;
    @BindView(R.id.tv_easyReturn)
    TextView tvEasyReturn;
    @BindView(R.id.LL_fs_cod_easyReturn)
    LinearLayout LLFsCodEasyReturn;
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


    private int lay_height = 0;
    int height = 0;
    String productCode;
    List<String> listProductDesc = new ArrayList<>();

    Product currentProductDetails;


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
      /*  if (Common.cartRepository.isAlreadyInCart(productCode) == 1) {
            btnAddToCart.setText("GO TO CART");

            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentCart = new Intent(ProductDetailsActivity.this, CartActivity.class);
                    startActivity(intentCart);
                }
            });
        }*/


        //Add to cart
        if (btnAddToCart.getText().equals("ADD TO CART")) {
            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Add Item to Room Database(SQLite)
                    //Create new Cart Item
                    try {
                      /*  Cart cart = new Cart();
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
                        Log.d(TAG, new Gson().toJson(cart));*/
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
                ArrayList<OrderItem> orderItemArrayList = new ArrayList<OrderItem>();
                OrderItem orderItem = new OrderItem();
                orderItem.setProductId(currentProductDetails.get_id());
                orderItem.setPriceExclTax(currentProductDetails.getPrice());
                orderItem.setPriceInclTax(currentProductDetails.getPrice());
                orderItem.setUnitPriceExclTax(currentProductDetails.getPrice());
                orderItem.setUnitPriceInclTax(currentProductDetails.getPrice());
                orderItem.setUnitPriceWithoutDiscExclTax(currentProductDetails.getPrice());
                orderItem.setUnitPriceWithoutDiscInclTax(currentProductDetails.getPrice());
                orderItem.setQuantity(1);
                orderItemArrayList.add(orderItem);
                Intent addressIntent = new Intent(ProductDetailsActivity.this, MyAddressActivity.class);
                addressIntent.putParcelableArrayListExtra("OrderItems",orderItemArrayList );
                startActivity(addressIntent);
            }
        });

    }

    private void getProductDetails(String productCode) {

        compositeDisposable.add(mServices.getProductById(productCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Product>() {
                               @Override
                               public void accept(Product productDetails) throws Exception {
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


    private void SetProductDetails(Product product) {


        productName.setText(product.getName());
        tvMrpProductList.setText(getIndianRupee(String.valueOf(product.getOldPrice())));

        tvPriceProductList.setAmount(Float.parseFloat(String.valueOf(product.getPrice())));
        tvDiscountProductList.setAmount((float) Common.DiscountInPercentage(product.getOldPrice(), product.getPrice()));
        if (Common.DiscountInPercentage(product.getOldPrice(), product.getPrice()) <= 0) {
            tvDiscountProductList.setVisibility(View.GONE);
            tvMrpProductList.setVisibility(View.GONE);
        } else {
            tvDiscountProductList.setVisibility(View.VISIBLE);
            tvMrpProductList.setVisibility(View.VISIBLE);
        }
       /* tvSellerName.setText(product.ven());
        if (product.getBrandCertifiedSeller()) {
            ivCertifiedSeller.setVisibility(View.VISIBLE);
        } else {
            ivCertifiedSeller.setVisibility(View.GONE);
        }*/
     //   tvRatingProductList.setText(String.valueOf(Integer.parseInt(String.valueOf(product.getApprovedRatingSum() / product.getApprovedTotalReviews()))));
        String totalRatings = String.valueOf(new StringBuilder("(").append(product.getApprovedTotalReviews()).append(")"));
        tvTotalRatingProductList.setText(totalRatings);
        tvRatingProductList.setVisibility(View.GONE);
        tvTotalRatingProductList.setVisibility(View.GONE);
        ivAssured.setVisibility(View.GONE);
        //Setup for all Shipping, Return,Cancellation,Intsallation,COD

       /* //Shipping Fee
        if (product.isFreeShipping()) {
            tvShippingPrice.setText("FREE");
            tvShippingPrice.setTextColor(getResources().getColor(R.color.green));

        } else {
            String shippingFee = String.valueOf(new StringBuilder(getResources().getString(R.string.rupee)).append(" ").append(product.getAdditionalShippingCharge()));
            tvShippingPrice.setText(shippingFee);
            tvShippingPrice.setTextColor(getResources().getColor(R.color.black));
        }

        //Retun
        if (!product.isNotReturnable()) {
            ivReplace.setImageDrawable(getResources().getDrawable(R.drawable.replace));
            // String returnInDays = String.valueOf(new StringBuilder().append(product.getReturnsInDays()).append(" ").append(Html.fromHtml("Days<sup>*</sup>")));
            //  tvReturnInDays.setText(returnInDays);
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
        if (product.isNotReturnable()) {
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
        if (product.isTelecommunicationsOrBroadcastingOrElectronicServices()) {
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
            String returnInDaysnew = String.valueOf(new StringBuilder().append(product.isNotReturnable()).append(" ").append(Html.fromHtml("Days<sup>*</sup>")));
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
            if (currentProductDetails.isFreeShipping()) {
                tvReturnInDays.setText("Available");
            } else {
                tvReturnInDays.setText("N/A");
            }
            tvReturnKnowMore.setVisibility(View.GONE);*/


    }

    private void SetImageSliderAndHighlights(Product product) {
        //set Product Images in Slider
        List<String> imagesList = new ArrayList<String>();
        for (int i=0;i<product.getPictureDetails().size();i++){
            String baseUrl = "http://192.168.20.46:1997/content/images/thumbs/";
            String imageID = product.getPictureDetails().get(i).get_id();
            String imageNmae =product.getPictureDetails().get(i).getSeoFilename();
            String imageMimeType =product.getPictureDetails().get(i).getMimeType().replace("image/","").trim();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(baseUrl).append(imageID).append("_").append(imageNmae).append(".").append(imageMimeType);
            imagesList.add(stringBuilder.toString());
        }

        Log.d(TAG, "SetImageSliderAndHighlights: " + imagesList);
        productDetailsSlider.setAdapter(new ProductDetailsSliderAdapter(imagesList));
        productDetailsSlider.setInterval(0);
        //Product Highligh in Listview
        listProductDesc.addAll(Collections.singleton(product.getFullDescription()));
        NonScrollListView listView = findViewById(R.id.listView_highlights);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_list_bullet, listProductDesc));
        //Set Visibility of Progressbar and Relative Layout
        PBProductDetails.setVisibility(View.GONE);
        RLProductDetaiContent.setVisibility(View.VISIBLE);
    }

    private void updateCartCount() {

        if (badge == null) return;
        /*runOnUiThread(new Runnable() {
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

    public static String getIndianRupee(String value) {
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        return format.format(new BigDecimal(value));
    }


    @Override
    protected void onResume() {
        /*f (Common.cartRepository.isAlreadyInCart(productCode) == 1) {
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
                           *//* Cart cart = new Cart();
                            cart.productId = currentProductDetails.get();
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
                            cart.type = currentProductDetails.getType();*//*

                            // Common.cartRepository.insertToCart(cart);
                            //   Log.d(TAG, new Gson().toJson(cart));
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
        }   */
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
