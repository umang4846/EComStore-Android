package com.appprocessors.ecomstore.adapter;

import com.appprocessors.ecomstore.model.Banner;
import com.appprocessors.ecomstore.model.pictureslider.PictureSlider;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class HomeSliderAdapter extends SliderAdapter {

    List<PictureSlider> bannerList;

    public HomeSliderAdapter(List<PictureSlider> bannerList) {
        this.bannerList = bannerList;
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {

            viewHolder.bindImageSlide(bannerList.get(position).getLink());

    }
}
