package cn.com.hisistar.showclient.program;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;

import cn.com.hisistar.showclient.picture_selector.ProgramSelectorFragment;


/**
 * @author lxj
 * @date 2018/8/23
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<String> mStrings = new ArrayList<>();
    ArrayList<Fragment> mFragments = new ArrayList<>();

    public MyViewPagerAdapter(FragmentManager fm, ArrayList<String> strings, ArrayList<Fragment> fragments) {
        super(fm);
        mStrings = strings;
        mFragments = fragments;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if ((position >= 0) && (position < mStrings.size())) {
            return mStrings.get(position);
        }
        return super.getPageTitle(position);
    }

    @Override
    public Fragment getItem(int i) {

        /*ProgramSelectorFragment fragment = new ProgramSelectorFragment();

        if (i == 0) {
            fragment.setPictureMimeType(PictureConfig.TYPE_ALL);
        } else if (i == (getCount() - 2)) {
            fragment.setPictureMimeType(PictureConfig.TYPE_AUDIO);
        } else if (i == (getCount() -1)) {
           return new ProgramFragment();
        } else {
            fragment.setPictureMimeType(PictureConfig.TYPE_IMAGE);
        }*/
        Fragment fragment = new Fragment();
        if ((mFragments != null) && ((i >= 0) && (i < mFragments.size()))) {
            fragment = mFragments.get(i);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        if (mStrings != null) {
            return mStrings.size();
        } else
            return 0;
    }
}
