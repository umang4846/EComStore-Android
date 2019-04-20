package com.appprocessors.ecomstore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.order.Order;
import com.appprocessors.ecomstore.utils.Common;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.CartViewHolder> {

    Activity context;
    List<Order> orders;


    public OrderAdapter(Activity context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
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

        String imageID = orders.get(position).getPictureDetails().get(0).get_id();
        String imageNmae = orders.get(position).getPictureDetails().get(0).getSeoFilename();
        String imageMimeType = orders.get(position).getPictureDetails().get(0).getMimeType().replace("image/", "").trim();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Common.IMAGE_BASE_URL).append(imageID).append("_").append(imageNmae).append("_415.").append(imageMimeType);
        //Load Image with Picasso
        Picasso.get().load(stringBuilder.toString()).placeholder(R.color.md_grey_300).into(cartViewHolder.ivOrder);

        cartViewHolder.tvNameOrderItem.setText(orders.get(position).getProductDetails().get(0).getName());
        cartViewHolder.RLItemOrder.setVisibility(View.VISIBLE);

        cartViewHolder.tvOrderTotalAmount.setText("TOTAL AMOUNT : " + context.getResources().getString(R.string.rupee) + " " + orders.get(position).getOrderTotal());

        if (orders.get(position).getOrderStatusId() == 10) {
            cartViewHolder.tvOrderStatus.setText("Pending");
            cartViewHolder.tvOrderStatus.setTextColor(context.getResources().getColor(R.color.orange));
        }
        if (orders.get(position).getOrderStatusId() == 20) {
            cartViewHolder.tvOrderStatus.setText("Shipped");
            cartViewHolder.tvOrderStatus.setTextColor(context.getResources().getColor(R.color.blue));
        }

        if (orders.get(position).getOrderStatusId() == 30) {
            cartViewHolder.tvOrderStatus.setText("Delivered");
            cartViewHolder.tvOrderStatus.setTextColor(context.getResources().getColor(R.color.forest_green));

        }

        if (orders.get(position).getOrderStatusId() == 40) {
            cartViewHolder.tvOrderStatus.setText("Cancelled");
            cartViewHolder.tvOrderStatus.setTextColor(context.getResources().getColor(R.color.red));
        }

        cartViewHolder.tvDeliveryProductList.setText("Ordered on " + orders.get(position).getCreatedOnUtc());


    /*    cartViewHolder.tvRateReviewOrderItem.setOnClickListener(new View.OnClickListener() {
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
        });*/

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_order)
        ImageView ivOrder;
        @BindView(R.id.tv_name_order_item)
        TextView tvNameOrderItem;
        @BindView(R.id.tv_order_total_amount)
        TextView tvOrderTotalAmount;
        @BindView(R.id.tv_delivery_bullet_product_list)
        TextView tvDeliveryBulletProductList;
        @BindView(R.id.tv_delivery_product_list)
        TextView tvDeliveryProductList;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.RL_item_order)
        RelativeLayout RLItemOrder;

        public CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
