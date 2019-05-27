package com.whosssmade.bluetoothterminal.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class ItemFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private FragmentManager fragmentManager;

    public ItemFragmentPagerAdapter(List<Fragment> list, FragmentManager fm) {
        super(fm);
        this.list = list;
        this.fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        Fragment fragment = list.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }
}
