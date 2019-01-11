package com.appprocessors.ecomstore.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.interfaces.IItemClickListner;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    ImageView img;
    TextView txt;

    IItemClickListner itemClickListner;

    public void setItemClickListner(IItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    public CategoryViewHolder(View itemView) {
        super(itemView);

        txt = itemView.findViewById(R.id.tv_trending_item);
        img = itemView.findViewById(R.id.image_product);

        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
            itemClickListner.onClick(v);
    }
}
