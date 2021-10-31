package com.app.social21.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.social21.Fragments.Notification2Fragment;
import com.app.social21.Fragments.RequestFragment;
import com.app.social21.databinding.FragmentProfileBinding;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Notification2Fragment();

            case 1:
                return new RequestFragment();
            default:
                return new Notification2Fragment();

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Notification";

        } else if (position == 1) {
            title = "Request";
        }
        return title;
    }
}

