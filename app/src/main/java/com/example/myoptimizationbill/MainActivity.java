package com.example.myoptimizationbill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import java.util.*;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.example.myoptimizationbill.activities.BaseActivity;
import com.example.myoptimizationbill.presenters.Fragments.OrdersFragment;
import com.example.myoptimizationbill.presenters.Fragments.OptimizingFragment;
import com.example.myoptimizationbill.presenters.Fragments.VouchersFragment;
import com.example.myoptimizationbill.presenters.PagerAdapter;
import com.example.myoptimizationbill.presenters.UI.HorizontalMarginItemDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class MainActivity extends BaseActivity {

    private SeekBar mSeekBar = null;
    private ViewPager2 mViewPager2 = null;
    private PagerAdapter mPagerAdapter = null;
    private ArrayList<Fragment> listOfPage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listOfPage.add(OrdersFragment.Companion.newInstance());
        listOfPage.add(VouchersFragment.Companion.newInstance());
        listOfPage.add(OptimizingFragment.Companion.newInstance());

        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mSeekBar.setMax(listOfPage.size() - 1);
        mViewPager2 = findViewById(R.id.view_pager2);
        mViewPager2.setOffscreenPageLimit(1);
        float nextItemVisiblePx = getResources().getDimension(R.dimen.viewpager_next_item_visible);
        float  currentItemHorizontalMarginPx = getResources().getDimension(R.dimen.viewpager_current_item_horizontal_margin);
        float pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx;
        ViewPager2.PageTransformer pageTransformer=  new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull @NotNull View page, float position) {
                page.setTranslationX(  -pageTranslationX * position);
                // Next line scales the item's height. You can remove it if you don't want this effect
                page.setScaleY( 1 - (0.25f * abs(position)));
            }
        };


        mViewPager2.setPageTransformer(pageTransformer);

// The ItemDecoration gives the current (centered) item horizontal margin so that
// it doesn't occupy the whole screen width. Without it the items overlap
        HorizontalMarginItemDecoration itemDecoration = new HorizontalMarginItemDecoration(
                this,
                R.dimen.viewpager_current_item_horizontal_margin
        );
        mViewPager2.addItemDecoration(itemDecoration);

        mSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),getLifecycle());

        mViewPager2.setAdapter(mPagerAdapter);
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setPhase(position);


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    public void setPhase(int iPhase) {
        if (iPhase <= mSeekBar.getMax()  && iPhase >= 0)
            mSeekBar.setProgress(iPhase);
    }

    public void nextPhase() {
        int currentProgress = mSeekBar.getProgress();
        if (currentProgress < mSeekBar.getMax() - 1)
            mSeekBar.setProgress(mSeekBar.getProgress() + 1);
    }

    public void previousPhase() {
        int currentProgress = mSeekBar.getProgress();
        if (currentProgress > 0)
            mSeekBar.setProgress(mSeekBar.getProgress() - 1);
    }
}