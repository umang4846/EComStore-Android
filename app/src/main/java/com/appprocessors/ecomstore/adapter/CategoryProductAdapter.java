package com.appprocessors.ecomstore.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appprocessors.ecomstore.activities.SubCategoryProductActivity;
import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.interfaces.IItemClickListner;
import com.appprocessors.ecomstore.model.CategoryProducts;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductViewHolder> {

    Context context;
    List<CategoryProducts> categoryProducts;

    public CategoryProductAdapter(Context context, List<CategoryProducts> categoryProducts) {
        this.context = context;
        this.categoryProducts = categoryProducts;
    }
    @NonNull
    @Override
    public CategoryProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.category_top_store_item_layout, null);
        return new CategoryProductViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final CategoryProductViewHolder holder, final int position) {
        //Load Image with Picasso
        Picasso.get().load(categoryProducts.get(position).getLink()).into(holder.img);

        holder.txt.setText(categoryProducts.get(position).getName());

        holder.setItemClickListner(new IItemClickListner() {
            @Override
            public void onClick(View v) {
                //Start New Activity
                Intent intent = new Intent(context,SubCategoryProductActivity.class);
                intent.putExtra("categoryProducts",categoryProducts.get(position));
                context.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        return categoryProducts.size();
    }
}
