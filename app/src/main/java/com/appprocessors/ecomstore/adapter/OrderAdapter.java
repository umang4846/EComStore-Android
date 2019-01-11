package com.appprocessors.ecomstore.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.OrderModel;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.CartViewHolder> {

    Context context;
    List<OrderModel> orderModels;

    IEStoreAPI mServices = Common.getAPI();

    public OrderAdapter(Context context, List<OrderModel> orderModels) {
        this.context = context;
        this.orderModels = orderModels;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_orders, viewGroup, false);


        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, final int position) {
        Picasso.get().load(orderModels.get(position).getImageUrl()).into(cartViewHolder.ivProductList);

        cartViewHolder.tvNameProductList.setText(orderModels.get(position).getProductName());
        cartViewHolder.tvColorProductList.setText(orderModels.get(position).getProductPrice());
        cartViewHolder.tvReorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderModel orderModel = new OrderModel();
        /*        orderModel.setMenuid(Common.currentproductDetails.getMenuid());
                orderModel.setProductName(Common.currentproductDetails.getProductName());
                orderModel.setProductPrice(Common.currentproductDetails.getMrp());
                orderModel.setImageUrl(Common.currentproductDetails.getImageMain());*/

                mServices.addOrder(orderModel).enqueue(new Callback<OrderModel>() {
                    @Override
                    public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                        Toast.makeText(context, "Successfully Re-Ordered !", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<OrderModel> call, Throwable t) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }


    class CartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_product_list)
        ImageView ivProductList;
        @BindView(R.id.tv_name_product_list)
        TextView tvNameProductList;
        @BindView(R.id.tv_color_product_list)
        TextView tvColorProductList;
        @BindView(R.id.tv_reorder)
        TextView tvReorder;
        public CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
