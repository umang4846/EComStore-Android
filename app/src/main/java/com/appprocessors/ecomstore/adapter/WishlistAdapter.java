package com.appprocessors.ecomstore.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.activities.ProductDetailsActivity;
import com.appprocessors.ecomstore.activities.WishlistActivity;
import com.appprocessors.ecomstore.model.customer.ShoppingCartItems;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.squareup.picasso.Picasso;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.accountkit.internal.AccountKitController.getApplicationContext;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlisViewHolder> {
    WishlistActivity wishlistActivity;
    List<ShoppingCartItems> shoppingCartItems;

    IEStoreAPI mServices;

    // User Session Manager Class
    UserSessionManager session;

    public WishlistAdapter(WishlistActivity wishlistActivity, List<ShoppingCartItems> ShoppingCartItems) {
        this.wishlistActivity = wishlistActivity;
        this.shoppingCartItems = ShoppingCartItems;
        mServices = Common.getAPI(wishlistActivity);
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());
    }

    @NonNull
    @Override
    public WishlisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(wishlistActivity).inflate(R.layout.wishlist_item_layout, viewGroup, false);

        return new WishlisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlisViewHolder WishlisViewHolder, final int position) {

        String imageID = shoppingCartItems.get(position).getPictureDetails().get(0).get_id();
        String imageNmae = shoppingCartItems.get(position).getPictureDetails().get(0).getSeoFilename();
        String imageMimeType = shoppingCartItems.get(position).getPictureDetails().get(0).getMimeType().replace("image/", "").trim();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Common.IMAGE_BASE_URL).append(imageID).append("_").append(imageNmae).append("_415.").append(imageMimeType);
        //Load Image with Picasso
        Picasso.get().load(stringBuilder.toString()).placeholder(R.color.md_grey_300).into(WishlisViewHolder.ivWishlistImage);

        WishlisViewHolder.tvWishlistProductName.setText(shoppingCartItems.get(position).getProductDetails().getName());
        WishlisViewHolder.tvWishlistMrp.setText(String.valueOf(shoppingCartItems.get(position).getProductDetails().getOldPrice()));
        WishlisViewHolder.tvWishlistMrp.setPaintFlags(WishlisViewHolder.tvWishlistMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        WishlisViewHolder.tvWishlistPrice.setAmount(Float.parseFloat(String.valueOf(shoppingCartItems.get(position).getProductDetails().getPrice())));
        WishlisViewHolder.tvWishlistDiscount.setVisibility(View.GONE);
        WishlisViewHolder.ivWishlistRemove.setVisibility(View.VISIBLE);
        WishlisViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(wishlistActivity, ProductDetailsActivity.class);
                intent.putExtra("productCode",shoppingCartItems.get(position).getProductDetails().get_id());
                wishlistActivity.startActivity(intent);
            }
        });
        WishlisViewHolder.ivWishlistRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mServices.deleteCartItemByProductId(session.getUserDetails().get(UserSessionManager.KEY_PHONE).replace("+", ""), shoppingCartItems.get(position).getProductId())
                        .enqueue(new Callback<List<ShoppingCartItems>>() {
                            @Override
                            public void onResponse(Call<List<ShoppingCartItems>> call, Response<List<ShoppingCartItems>> response) {
                                if (response.isSuccessful()) {
                                    shoppingCartItems.remove(position);
                                    if (shoppingCartItems.size() == 0) {
                                        wishlistActivity.LLEmptyWishlist.setVisibility(View.VISIBLE);
                                        wishlistActivity.rvWishlist.setVisibility(View.GONE);
                                        wishlistActivity.btnRemoveAll.setVisibility(View.GONE);
                                    } else {
                                        notifyDataSetChanged();
                                        Toast.makeText(wishlistActivity, "Item Removed From WishList !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ShoppingCartItems>> call, Throwable t) {
                                Toast.makeText(wishlistActivity, "Deleting Wishlist Item Failed !" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingCartItems.size();
    }


    class WishlisViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_wishlist_image)
        ImageView ivWishlistImage;
        @BindView(R.id.tv_wishlist_product_name)
        TextView tvWishlistProductName;
        @BindView(R.id.tv_wishlist_price)
        MoneyTextView tvWishlistPrice;
        @BindView(R.id.tv_wishlist_mrp)
        TextView tvWishlistMrp;
        @BindView(R.id.tv_wishlist_discount)
        MoneyTextView tvWishlistDiscount;
        @BindView(R.id.iv_wishlist_remove)
        ImageView ivWishlistRemove;

        public WishlisViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}

