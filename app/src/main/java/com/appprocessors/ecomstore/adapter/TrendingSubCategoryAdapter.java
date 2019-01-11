package com.appprocessors.ecomstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appprocessors.ecomstore.activities.ProductDetailsActivity;
import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.interfaces.IItemClickListner;
import com.appprocessors.ecomstore.model.TrendingSubCategory;
import com.appprocessors.ecomstore.utils.Common;
import com.squareup.picasso.Picasso;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrendingSubCategoryAdapter extends RecyclerView.Adapter<TrendingSubCategoryAdapter.TrendingViewHolder> {

    Context context;
    List<TrendingSubCategory> trendings;

    public TrendingSubCategoryAdapter(Context context, List<TrendingSubCategory> trendingSubCategories) {
        this.context = context;
        this.trendings = trendingSubCategories;
    }

    public static String getIndianRupee(String value) {
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        return format.format(new BigDecimal(value));
    }

    @NonNull
    @Override
    public TrendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.trending_item_layout, null);
        return new TrendingViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(@NonNull TrendingViewHolder holder, int position) {

        //Load Image with Picasso
        Picasso.get().load(trendings.get(position).getImageMain()).into(holder.ivTrendingProduct);
        holder.tvTrendingProductName.setText(trendings.get(position).getProductName());
        holder.tvTrendingProductPrice.setAmount(Float.parseFloat(trendings.get(position).getPrice()));
        holder.tvTrendingProductMrp.setText(getIndianRupee(trendings.get(position).getMrp()));
        holder.tvTrendingProductMrp.setPaintFlags(holder.tvTrendingProductMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvTrendingProductDiscount.setAmount((float) Common.DiscountInPercentage(trendings.get(position).getMrp(), trendings.get(position).getPrice()));
        if (Common.DiscountInPercentage(trendings.get(position).getMrp(), trendings.get(position).getPrice()) <= 0) {
            holder.tvTrendingProductMrp.setVisibility(View.GONE);
            holder.LLTrendingDiscounts.setVisibility(View.GONE);
        } else {
            holder.tvTrendingProductMrp.setVisibility(View.VISIBLE);
            holder.LLTrendingDiscounts.setVisibility(View.VISIBLE);
        }
        if (trendings.get(position).getPrice().equalsIgnoreCase(trendings.get(position).getMrp())) {
            holder.tvTrendingProductMrp.setVisibility(View.GONE);
            holder.LLTrendingDiscounts.setVisibility(View.GONE);
        } else {
            holder.tvTrendingProductMrp.setVisibility(View.VISIBLE);
            holder.LLTrendingDiscounts.setVisibility(View.VISIBLE);
        }
        holder.setItemClickListner(new IItemClickListner() {
            @Override
            public void onClick(View v) {
                Intent bannerIntent = new Intent(context, ProductDetailsActivity.class);
                bannerIntent.putExtra("productCode", trendings.get(position).getProductCode());
                context.startActivity(bannerIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return trendings.size();
    }

    class TrendingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_trending_product)
        ImageView ivTrendingProduct;
        @BindView(R.id.tv_trending_product_name)
        TextView tvTrendingProductName;
        @BindView(R.id.tv_trending_product_price)
        MoneyTextView tvTrendingProductPrice;
        @BindView(R.id.tv_trending_product_mrp)
        TextView tvTrendingProductMrp;
        @BindView(R.id.tv_trending_product_discount)
        MoneyTextView tvTrendingProductDiscount;
        @BindView(R.id.LL_trending_discounts)
        LinearLayout LLTrendingDiscounts;

        IItemClickListner itemClickListner;

        public void setItemClickListner(IItemClickListner itemClickListner) {
            this.itemClickListner = itemClickListner;
        }

        public TrendingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListner.onClick(v);
        }
    }

}
