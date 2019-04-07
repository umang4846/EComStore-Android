package com.appprocessors.ecomstore.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.ProductDetails;
import com.appprocessors.ecomstore.model.order.Order;
import com.appprocessors.ecomstore.model.product.Product;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.CartViewHolder> {

    Activity context;
    List<Order> orders;
    List<Product> productDetailsList;


    public OrderAdapter(Activity context, List<Order> orders, List<Product> productDetailsList) {
        this.context = context;
        this.orders = orders;
        this.productDetailsList = productDetailsList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orders, viewGroup, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, final int position) {
        cartViewHolder.RLItemOrder.setVisibility(View.GONE);
        ProgressDialog loadingOrdersDialog = new ProgressDialog(context);
        loadingOrdersDialog.setMessage("Please wait");
        loadingOrdersDialog.setCancelable(false);
        loadingOrdersDialog.show();


       // Picasso.get().load(productDetailsList.get(position).get()).into(cartViewHolder.ivOrderItem);
    //    cartViewHolder.tvNameOrderItem.setText(productDetailsList.get(position).getProductName());
        cartViewHolder.RLItemOrder.setVisibility(View.VISIBLE);
        loadingOrdersDialog.dismiss();
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("EEEE,dd-MM-yyyy");

        cartViewHolder.tvDeliveryProductList.setText(orders.get(position).getVatNumberStatusId() + " On " + Common.getFormattedDate(date));
        cartViewHolder.tvRateReviewOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AppRatingDialog.Builder()
                        .setPositiveButtonText("Submit")
                        .setNegativeButtonText("Cancel")
                        .setNoteDescriptions(Arrays.asList("Very Bad", "Bad", "Good", "Very Good", "Excellent !!"))
                        .setDefaultRating(3)
                        .setTitle("Rate the product")
                        .setDescription("Please select some stars and give your feedback to this product")
                        .setCommentInputEnabled(true)
                        .setStarColor(R.color.starColor)
                        .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
                        .setTitleTextColor(R.color.titleTextColor)
                        .setDescriptionTextColor(R.color.descriptionTextColor)
                        .setHint("Please write your review here ...")
                        .setHintTextColor(R.color.hintTextColor)
                        .setCommentTextColor(R.color.commentTextColor)
                        .setCommentBackgroundColor(R.color.colorPrimaryDark)
                        .setWindowAnimation(R.style.MyDialogFadeAnimation)
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .create((FragmentActivity) context)
                        .show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_order_item)
        ImageView ivOrderItem;
        @BindView(R.id.tv_name_order_item)
        TextView tvNameOrderItem;
        @BindView(R.id.tv_delivery_product_list)
        TextView tvDeliveryProductList;
        @BindView(R.id.tv_rate_review_order_item)
        TextView tvRateReviewOrderItem;
        @BindView(R.id.RL_item_order)
        RelativeLayout RLItemOrder;

        public CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
