package com.appprocessors.ecomstore.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.database.modeldb.Cart;
import com.appprocessors.ecomstore.utils.Common;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.omega_r.libs.OmegaCenterIconButton;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.squareup.picasso.Picasso;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    List<Cart> carts;


    public CartAdapter(Context context, List<Cart> carts) {
        this.context = context;
        this.carts = carts;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, viewGroup, false);


        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, final int position) {
        Picasso.get().load(carts.get(position).imageMain).into(cartViewHolder.ivCartImage);

        cartViewHolder.tvNameProductList.setText(carts.get(position).productName);
        cartViewHolder.tvMrpProductList.setText(carts.get(position).mrp);
        cartViewHolder.tvMrpProductList.setPaintFlags(cartViewHolder.tvMrpProductList.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        cartViewHolder.tvPriceProductList.setAmount(Float.parseFloat(carts.get(position).price));
        cartViewHolder.tvDiscountProductList.setAmount((float) Common.DiscountInPercentage(carts.get(position).price, carts.get(position).mrp));
        cartViewHolder.tvSellerName.setText(carts.get(position).soldBy);
        cartViewHolder.tvColorProductList.setText(carts.get(position).idealFor);
        cartViewHolder.cartProductQuantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Cart cart = carts.get(position);
                cart.mrp = String.valueOf((Integer.parseInt(cart.mrp) * newValue));
                cart.price = String.valueOf((Integer.parseInt(cart.price) * newValue));
                Common.cartRepository.updateToCart(cart);

            }
        });
        cartViewHolder.btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FancyAlertDialog.Builder((Activity) context)
                        .setTitle("Are you sure ?")
                        .setMessage("Do you really want to Remove this Item from Cart ?")
                        .setNegativeBtnText("CANCEL")
                        .setPositiveBtnText("REMOVE")
                        .setAnimation(Animation.POP)
                        .isCancellable(false)
                        .setIcon(R.drawable.ic_shopping_cart_24dp, Icon.Visible)
                        .OnPositiveClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {
                                Common.cartRepository.deleteCartItem(carts.get(position));
                                if (Common.cartRepository.countCartItems() == 0) {
                                    Toast.makeText(context, "No Items in cart Update View", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .OnNegativeClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {

                            }
                        })
                        .build();

            }
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }


    class CartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cart_image)
        ImageView ivCartImage;
        @BindView(R.id.cart_product_quantity)
        ElegantNumberButton cartProductQuantity;
        @BindView(R.id.tv_name_product_list)
        TextView tvNameProductList;
        @BindView(R.id.tv_color_product_list)
        TextView tvColorProductList;
        @BindView(R.id.tv_Seller)
        TextView tvSeller;
        @BindView(R.id.tv_seller_name)
        TextView tvSellerName;
        @BindView(R.id.iv_certified_seller)
        ImageView ivCertifiedSeller;
        @BindView(R.id.tv_price_product_list)
        MoneyTextView tvPriceProductList;
        @BindView(R.id.tv_mrp_product_list)
        TextView tvMrpProductList;
        @BindView(R.id.tv_discount_product_list)
        MoneyTextView tvDiscountProductList;
        @BindView(R.id.btn_save_for_later)
        OmegaCenterIconButton btnSaveForLater;
        @BindView(R.id.btn_remove_item)
        OmegaCenterIconButton btnRemoveItem;


        public CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
