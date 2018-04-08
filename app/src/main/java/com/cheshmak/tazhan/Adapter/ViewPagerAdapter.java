package com.cheshmak.tazhan.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cheshmak.tazhan.FragmentBillboardsList;
import com.cheshmak.tazhan.FragmentBillboardsMap;
import com.cheshmak.tazhan.cheshmak.R;

/**
 * Created by mmr on 02/03/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final Context context;
    FragmentBillboardsList fragmentBillboardsList;
    FragmentBillboardsMap fragmentBillboardsMap;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            if (fragmentBillboardsList != null) {
                return fragmentBillboardsList;
            } else {
                fragmentBillboardsList = new FragmentBillboardsList();
                return fragmentBillboardsList;
            }
        } else {
            if (fragmentBillboardsMap != null) {
                return fragmentBillboardsMap;
            } else {
                fragmentBillboardsMap = new FragmentBillboardsMap();
                return fragmentBillboardsMap;
            }
        }


    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = context.getResources().getString(R.string.list);
        } else if (position == 1) {
            title = context.getResources().getString(R.string.map);
        }

        return title;
    }

    public Fragment getFragments(int idx) {
        switch (idx) {
            case 0:
                return fragmentBillboardsList;
            case 1:
                return fragmentBillboardsMap;
            default:
                return null;
        }
    }
}
