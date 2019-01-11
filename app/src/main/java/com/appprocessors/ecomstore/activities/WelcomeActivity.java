package com.appprocessors.ecomstore.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appprocessors.ecomstore.Fragments.IntroFragment1;
import com.appprocessors.ecomstore.Fragments.IntroFragment2;
import com.appprocessors.ecomstore.Fragments.IntroFragment3;
import com.appprocessors.ecomstore.R;
import com.github.paolorotolo.appintro.AppIntro;

public class WelcomeActivity extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {

        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(new IntroFragment1());
        addSlide(new IntroFragment2());
        addSlide(new IntroFragment3());
        setCustomTransformer(new ZoomOutPageTransformer());
        setDepthAnimation();
        TextView textView = findViewById(R.id.bottom_separator);
        textView.setVisibility(View.GONE);
        LinearLayout linearLayout = findViewById(R.id.bottom);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.blue_adv));

    }


    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        loadMainActivity();
    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {
        // Do something when the slide changes.
    }

    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }
    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
    private void loadMainActivity(){
        Intent intent = new Intent(this, LoginSignUp.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}


