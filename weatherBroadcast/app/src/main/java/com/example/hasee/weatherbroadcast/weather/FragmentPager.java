package com.example.hasee.weatherbroadcast.weather;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.example.hasee.weatherbroadcast.adapter.MyFragmentPagerAdapter;

/**
 * Created by hasee on 2018/2/6.
 */

public class FragmentPager implements ViewPager.OnPageChangeListener{
    private MyFragmentPagerAdapter mAdapter;
    private ViewPager vpager;
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public FragmentPager(FragmentManager fm, ViewPager vpager){
        mAdapter=new MyFragmentPagerAdapter(fm);
        this.vpager=vpager;
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);

    }
    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
/*        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    break;
                case PAGE_TWO:
                    break;
                case PAGE_THREE:
                    break;
            }
        }*/
    }
}
