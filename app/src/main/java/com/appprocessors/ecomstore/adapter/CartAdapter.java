package com.appprocessors.ecomstore.adapter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.activities.CartActivity;
import com.appprocessors.ecomstore.activities.ProductDetailsActivity;
import com.appprocessors.ecomstore.model.customer.ShoppingCartItems;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.squareup.picasso.Picasso;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.accountkit.internal.AccountKitController.getApplicationContext;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private static final String TAG = "CartAdapter";
    CartActivity cartActivity;
    List<ShoppingCartItems> shoppingCartItems;

    private int mSelectedIndex = 0;

    IEStoreAPI mServices;

    // User Session Manager Class
    UserSessionManager session;


    private ProgressDialog placingCartDialog;


    public CartAdapter(CartActivity cartActivity, List<ShoppingCartItems> ShoppingCartItems) {
        this.cartActivity = cartActivity;
        this.shoppingCartItems = ShoppingCartItems;
        mServices = Common.getAPI(cartActivity);
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(cartActivity).inflate(R.layout.cart_item_layout, viewGroup, false);


        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, final int position) {

        String imageID = shoppingCartItems.get(position).getPictureDetails().get(0).get_id();
        String imageNmae = shoppingCartItems.get(position).getPictureDetails().get(0).getSeoFilename();
        String imageMimeType = shoppingCartItems.get(position).getPictureDetails().get(0).getMimeType().replace("image/", "").trim();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Common.IMAGE_BASE_URL).append(imageID).append("_").append(imageNmae).append("_415.").append(imageMimeType);
        //Load Image with Picasso
        Picasso.get().load(stringBuilder.toString()).placeholder(R.color.md_grey_300).into(cartViewHolder.ivCartImage);

        cartViewHolder.tvCartProductName.setText(shoppingCartItems.get(position).getProductDetails().getName());
        cartViewHolder.tvCartProductMrp.setText(String.valueOf(shoppingCartItems.get(position).getProductDetails().getOldPrice()));
        cartViewHolder.tvCartProductMrp.setPaintFlags(cartViewHolder.tvCartProductMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        cartViewHolder.tvCartProductPrice.setAmount(Float.parseFloat(String.valueOf(shoppingCartItems.get(position).getProductDetails().getPrice())));
        cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(cartActivity, ProductDetailsActivity.class);
                intent.putExtra("productCode",shoppingCartItems.get(position).getProductDetails().get_id());
                cartActivity.startActivity(intent);
            }
        });

        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        //  ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, list);
        final boolean[] isInitialized = {false};

        ArrayAdapter dataAdapter = new ArrayAdapter<String>(cartActivity, android.R.layout.simple_spinner_dropdown_item, list) {
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                tv.setTextColor(Color.BLUE);

                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView(position, convertView, parent);

                // Set the text color of drop down items
                tv.setTextColor(Color.BLACK);

                // If this item is selected item
                if (position == mSelectedIndex) {
                    // Set spinner selected popup item's text color
                    tv.setTextColor(Color.BLUE);
                }

                // Return the modified view
                return tv;
            }

        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cartViewHolder.spQuantity.setAdapter(dataAdapter);
        if (list.contains(String.valueOf(shoppingCartItems.get(position).getQuantity())))
            cartViewHolder.spQuantity.setSelection(shoppingCartItems.get(position).getQuantity() - 1);
        cartViewHolder.spQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                mSelectedIndex = i;
                if (!isInitialized[0]) {
                    isInitialized[0] = true;
                    return;
                }
                //Call API & Save Quantity
                ShoppingCartItems shoppingCartItem = new ShoppingCartItems();
                shoppingCartItem.set_id("");
                shoppingCartItem.setProductId(shoppingCartItems.get(position).getProductDetails().get_id());
                shoppingCartItem.setQuantity(Integer.parseInt(cartViewHolder.spQuantity.getSelectedItem().toString()));
                shoppingCartItem.setCreatedOnUtc("");
                shoppingCartItem.setUpdatedOnUtc("");

                //Add Product to cart
                placingCartDialog = new ProgressDialog(cartActivity);
                placingCartDialog.setMessage("Please wait");
                placingCartDialog.setCancelable(false);
                placingCartDialog.show();

                mServices.addToCart(session.getUserDetails().get(UserSessionManager.KEY_PHONE).replace("+", ""), shoppingCartItem).enqueue(new Callback<List<ShoppingCartItems>>() {
                    @Override
                    public void onResponse(Call<List<ShoppingCartItems>> call, Response<List<ShoppingCartItems>> response) {
                        if (response.isSuccessful()) {
                            List<ShoppingCartItems> shoppingCartItemsList = response.body();
                            if (shoppingCartItemsList != null && shoppingCartItemsList.size() != 0) {
                                placingCartDialog.dismiss();
                                Toast.makeText(cartActivity, "Added to Cart !", Toast.LENGTH_SHORT).show();
                                cartViewHolder.spQuantity.setSelection(cartViewHolder.spQuantity.getSelectedItemPosition());
                                cartActivity.shoppingCartItemsList.get(position).setQuantity(Integer.parseInt(cartViewHolder.spQuantity.getSelectedItem().toString()));
                                notifyDataSetChanged();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<ShoppingCartItems>> call, Throwable t) {
                        placingCartDialog.dismiss();
                        Log.e(TAG, "onFailure: Adding Item Quantity to cart Failed " + t.getMessage());
                        Toast.makeText(cartActivity, "Failed to add Quantity to cart ! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                shoppingCartItems.get(position).setQuantity(1);

            }
        });
        cartViewHolder.ivCartRemove.setVisibility(View.VISIBLE);

        cartViewHolder.ivCartRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(cartActivity, R.style.MaterialAlertDialog_MaterialComponents_Title_Text);
                materialAlertDialogBuilder.setTitle("Remove Item From Cart");
                materialAlertDialogBuilder.setMessage("Are you sure you want to remove this item ?");
                materialAlertDialogBuilder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mServices.deleteCartItemByProductId(session.getUserDetails().get(UserSessionManager.KEY_PHONE).replace("+", ""), shoppingCartItems.get(position).getProductId())
                                .enqueue(new Callback<List<ShoppingCartItems>>() {
                                    @Override
                                    public void onResponse(Call<List<ShoppingCartItems>> call, Response<List<ShoppingCartItems>> response) {
                                        if (response.isSuccessful()) {
                                            shoppingCartItems.remove(position);
                                            if (shoppingCartItems.size() == 0) {
                                                cartActivity.LLEmptyCart.setVisibility(View.VISIBLE);
                                                cartActivity.rvCartList.setVisibility(View.GONE);
                                                cartActivity.btnCartContinue.setVisibility(View.GONE);
                                            } else {
                                                notifyDataSetChanged();
                                                Toast.makeText(cartActivity, "Item Removed From Cart !", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<ShoppingCartItems>> call, Throwable t) {
                                        Toast.makeText(cartActivity, "Deleting Cart Items Failed !" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return shoppingCartItems.size();
    }


    class CartViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cart_image)
        ImageView ivCartImage;
        @BindView(R.id.tv_quantity)
        TextView tvQuantity;
        @BindView(R.id.sp_quantity)
        Spinner spQuantity;
        @BindView(R.id.LL_Quantity)
        LinearLayout LLQuantity;
        @BindView(R.id.tv_cart_product_name)
        TextView tvCartProductName;
        @BindView(R.id.tv_cart_product_price)
        MoneyTextView tvCartProductPrice;
        @BindView(R.id.tv_cart_product_mrp)
        TextView tvCartProductMrp;
        @BindView(R.id.iv_cart_remove)
        ImageView ivCartRemove;

        public CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}

