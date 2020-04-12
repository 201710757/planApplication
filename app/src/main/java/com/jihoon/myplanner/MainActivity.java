package com.jihoon.myplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;
    ViewPager vpPager;
    String TAG = "jihoonDebugging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        vpPager.setCurrentItem(1);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               //HERE!
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "((((((((((((SELECTED! : " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "((((((((((((PAGESTATECHANED!  :  " + state);
            }
        });

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    public void refresh()
    {
        adapterViewPager.notifyDataSetChanged();
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            Log.d("jihoonDebugging", "getITEM");
            switch (position) {
                case 1:
                    return FirstFragment.newInstance(0, "Page # 1");
                    //return FirstFragment.newInstance(0, "Page # 1");
                case 2:
                    return SecondFragment.newInstance(1, "Page # 2");
                case 0:
                    return ThirdFragment.newInstance(2, "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

}