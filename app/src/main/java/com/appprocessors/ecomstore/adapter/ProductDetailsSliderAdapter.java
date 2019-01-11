package com.appprocessors.ecomstore.adapter;

import android.widget.ImageView;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class ProductDetailsSliderAdapter extends SliderAdapter {

    List<String> Imageslist;
   String TAG = "ProductDetailsSliderAdapter";
    public ProductDetailsSliderAdapter( List<String> Imageslist) {
        this.Imageslist = Imageslist;
    }

    @Override
    public int getItemCount() {
        return Imageslist.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
            viewHolder.bindImageSlide(Imageslist.get(position));
            viewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


    }
}
