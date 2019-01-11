package com.appprocessors.ecomstore.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appprocessors.ecomstore.R;

public class IntroAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public  IntroAdapter(Context context)
    {
        this.context = context;
    }

    public  int[] slide_images = {

            R.drawable.intro_food,
            R.drawable.intro_student,
            R.drawable.intro_owl,

    };

    public  String[] slide_title = {

            "EAT","STUDY","SLEEP"

    };
    public  String[] slide_descs = {

            "Life is succession of lessons which must be lived to be unberstood.",
            "You come into the life with nothing, and the purpose of life is to make something out of nothing.",
            "Life is what happens while you are busy making other plans."

    };

    @Override
    public int getCount() {
        return slide_title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.intro_layout,container,false);

        ImageView iv = view.findViewById(R.id.intro_image);
        TextView tt = view.findViewById(R.id.intro_title);
        TextView td = view.findViewById(R.id.intro_descs);

        iv.setImageResource(slide_images[position]);
        tt.setText(slide_title[position]);
        td.setText(slide_descs[position]);

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }
}
