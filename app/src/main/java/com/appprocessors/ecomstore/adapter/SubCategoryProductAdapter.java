package com.appprocessors.ecomstore.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appprocessors.ecomstore.activities.ProductListActivity;
import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.interfaces.IItemClickListner;
import com.appprocessors.ecomstore.model.SubCategoryProducts;
import com.appprocessors.ecomstore.utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubCategoryProductAdapter extends RecyclerView.Adapter<SubCategoryProductViewHolder> {

    Context context;
    List<SubCategoryProducts> subcategoryProducts;

    public SubCategoryProductAdapter(Context context, List<SubCategoryProducts> subcategoryProducts) {
        this.context = context;
        this.subcategoryProducts = subcategoryProducts;
    }
    @NonNull
    @Override
    public SubCategoryProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.subcategory_top_store_item_layout, null);
        return new SubCategoryProductViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final SubCategoryProductViewHolder holder, final int position) {
        //Load Image with Picasso
        Picasso.get( ).load(subcategoryProducts.get(position).getLink()).into(holder.img);

        holder.txt.setText(subcategoryProducts.get(position).getName());

        holder.setItemClickListner(new IItemClickListner() {
            @Override
            public void onClick(View v) {
              //  Common.currentSubcategoryProducts = subcategoryProducts.get(position);
                //Start New Activity
                Intent intent = new Intent(context,ProductListActivity.class);
                intent.putExtra("subcategory",subcategoryProducts.get(position));
                context.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        return subcategoryProducts.size();
    }
}
