package com.app.social21.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.social21.Adapter.ViewPagerAdapter;
import com.app.social21.R;
import com.google.android.material.tabs.TabLayout;


public class NotificationFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}