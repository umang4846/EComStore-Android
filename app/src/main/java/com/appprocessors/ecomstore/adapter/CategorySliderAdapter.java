package com.appprocessors.ecomstore.adapter;

import com.appprocessors.ecomstore.model.CategoryBanner;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class CategorySliderAdapter extends SliderAdapter {

    List<CategoryBanner> categoryBannerList;

    public CategorySliderAdapter(List<CategoryBanner> categoryBannerList) {
        this.categoryBannerList = categoryBannerList;
    }

    @Override
    public int getItemCount() {
        return categoryBannerList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {

            viewHolder.bindImageSlide(categoryBannerList.get(position).getLink());


    }
}
