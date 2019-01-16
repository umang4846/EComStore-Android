package com.appprocessors.ecomstore.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appprocessors.ecomstore.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment1 extends Fragment {


    public IntroFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.intro_fragment1, container, false);
    }

}
