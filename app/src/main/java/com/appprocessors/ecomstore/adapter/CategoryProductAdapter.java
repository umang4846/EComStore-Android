package com.appprocessors.ecomstore.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appprocessors.ecomstore.activities.ProductListActivity;
import com.appprocessors.ecomstore.activities.SubCategoryProductActivity;
import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.interfaces.IItemClickListner;
import com.appprocessors.ecomstore.model.CategoryProducts;
import com.appprocessors.ecomstore.model.categoryhome.CategoryHome;
import com.appprocessors.ecomstore.utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductViewHolder> {

    Context context;
    List<CategoryHome> categoryHomeList;

    public CategoryProductAdapter(Context context, List<CategoryHome> categoryHomeList) {
        this.context = context;
        this.categoryHomeList = categoryHomeList;
    }
    @NonNull
    @Override
    public CategoryProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.category_top_store_item_layout, null);
        return new CategoryProductViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final CategoryProductViewHolder holder, final int position) {

        String imageID = categoryHomeList.get(position).getPictureDetails().get(0).get_id();
        String imageNmae =categoryHomeList.get(position).getPictureDetails().get(0).getSeoFilename();
        String imageMimeType =categoryHomeList.get(position).getPictureDetails().get(0).getMimeType().replace("image/","").trim();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Common.IMAGE_BASE_URL).append(imageID).append("_").append(imageNmae).append("_450.").append(imageMimeType);

        //Load Image with Picasso
        Picasso.get().load(stringBuilder.toString()).into(holder.img);

        holder.txt.setText(categoryHomeList.get(position).getName());

        holder.setItemClickListner(new IItemClickListner() {
            @Override
            public void onClick(View v) {
                //Start New Activity
                /*Intent intent = new Intent(context,SubCategoryProductActivity.class);
                intent.putExtra("categoryProducts",categoryProducts.get(position));
                context.startActivity(intent);*/
                Common.currentSubcategoryProducts = categoryHomeList.get(position);

                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("subcategory",categoryHomeList.get(position));
                context.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        return categoryHomeList.size();
    }
}
