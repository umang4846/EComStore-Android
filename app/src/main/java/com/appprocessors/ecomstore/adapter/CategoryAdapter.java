package com.appprocessors.ecomstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.activities.CategoryProductsActivity;
import com.appprocessors.ecomstore.activities.HomeActivity;
import com.appprocessors.ecomstore.activities.ProductDetailsActivity;
import com.appprocessors.ecomstore.interfaces.IItemClickListner;
import com.appprocessors.ecomstore.model.categoryhome.CategoryHome;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    Context context;
    List<CategoryHome> categories;

    public CategoryAdapter(Context context, List<CategoryHome> categories) {
        this.context = context;
        this.categories = categories;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.category_item_layout, null);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {
        String baseUrl = "http://192.168.20.46:1997/content/images/thumbs/";
        String imageID = categories.get(position).getPictureDetails().get(0).get_id();
        String imageNmae =categories.get(position).getPictureDetails().get(0).getSeoFilename();
        String imageMimeType =categories.get(position).getPictureDetails().get(0).getMimeType().replace("image/","").trim();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl).append(imageID).append("_").append(imageNmae).append("_450.").append(imageMimeType);
        //Load Image with Picasso
       Picasso.get().load(stringBuilder.toString()).placeholder(R.color.md_grey_300).into(holder.img);

        holder.txt.setText(categories.get(position).getName());

        holder.setItemClickListner(new IItemClickListner() {
            @Override
            public void onClick(View v) {
                //Start New Activity
                Intent categoryIntent = new Intent(context, CategoryProductsActivity.class);
                categoryIntent.putExtra("categoryId",categories.get(position).get_id());
                categoryIntent.putExtra("categoryName",categories.get(position).getName());
                context.startActivity(categoryIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
