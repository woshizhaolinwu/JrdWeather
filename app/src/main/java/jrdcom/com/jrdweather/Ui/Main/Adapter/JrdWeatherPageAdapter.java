package jrdcom.com.jrdweather.Ui.Main.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import jrdcom.com.jrdweather.Ui.Main.MainFragment;

/**
 * Created by longcheng on 2017/5/3.
 */

public class JrdWeatherPageAdapter extends FragmentPagerAdapter {
    private List<MainFragment> list;
    public JrdWeatherPageAdapter(FragmentManager fm, List<MainFragment> fragments) {
        super(fm);
        list = fragments;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    /*@Override
    public CharSequence getPageTitle(int position) {
        return tabString[position];
    }*/
}
