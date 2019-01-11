package com.appprocessors.ecomstore.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.interfaces.IItemClickListner;

public class CategoryProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView img;
    TextView txt;

    IItemClickListner itemClickListner;

    public void setItemClickListner(IItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    public CategoryProductViewHolder(View itemView) {
        super(itemView);

        txt = itemView.findViewById(R.id.tv_top_store);
        img = itemView.findViewById(R.id.iv_top_store);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
            itemClickListner.onClick(v);
    }
}
