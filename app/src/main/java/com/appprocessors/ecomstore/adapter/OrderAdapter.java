package com.appprocessors.ecomstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.Order;
import com.appprocessors.ecomstore.model.ProductDetails;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.CartViewHolder> {

    Context context;
    List<Order> orders;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IEStoreAPI mServices = Common.getAPI();


    public OrderAdapter(Context context, List<Order> orders) {
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
        compositeDisposable.add(mServices.getProductDetailsByProductCode(orders.get(position).getProductCode())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProductDetails>() {
                               @Override
                               public void accept(ProductDetails productDetails) throws Exception {
                                   if (productDetails != null) {
                                       Picasso.get().load(productDetails.getImageMain()).into(cartViewHolder.ivOrderItem);
                                       cartViewHolder.tvNameOrderItem.setText(productDetails.getProductName());

                                   }

                               }
                           }
                ));
        cartViewHolder.tvDeliveryProductList.setText(orders.get(position).getOrderDateTime());
        cartViewHolder.tvRateReviewOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

        public CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
