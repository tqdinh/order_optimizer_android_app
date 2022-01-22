package com.example.myoptimizationbill

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.myoptimizationbill.activities.BaseActivity
import com.example.myoptimizationbill.presenters.Fragments.OrdersFragment
import com.example.myoptimizationbill.presenters.Fragments.OptimizingFragment
import com.example.myoptimizationbill.presenters.Fragments.VouchersFragment
import com.example.myoptimizationbill.presenters.PagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import me.relex.circleindicator.CircleIndicator3
import java.util.*

class MainCalculationActivity : BaseActivity() {

    private lateinit var mViewPager2: ViewPager2
    private lateinit var mPagerAdapter: PagerAdapter
    private var listOfPage = ArrayList<Fragment>()
    private lateinit var circleIndicator3: CircleIndicator3

    lateinit var bt_navigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_calculation)
        bt_navigation = findViewById(R.id.bt_navigation)
        bt_navigation.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.orders ->
                        mViewPager2.currentItem = 0
                    R.id.vouchers ->
                        mViewPager2.currentItem = 1

                    R.id.bills ->
                        mViewPager2.currentItem = 2
                }
                return true
            }
        })
//        listOfPage.add(OrdersFragment.newInstance())
//        listOfPage.add(VouchersFragment.newInstance())
//        listOfPage.add(OptimizingFragment.newInstance())

        listOfPage.add(OrdersFragment())
        listOfPage.add(VouchersFragment())
        listOfPage.add(OptimizingFragment())


        circleIndicator3 = findViewById<CircleIndicator3>(R.id.indicator)

        mViewPager2 = findViewById(R.id.view_pager2)



        mPagerAdapter = PagerAdapter(supportFragmentManager, lifecycle = lifecycle)

        mPagerAdapter.updateFragment(listOfPage)

        mViewPager2.setAdapter(mPagerAdapter)
        circleIndicator3.setViewPager(mViewPager2)
        mViewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when (position) {
                    0 ->
                        bt_navigation.menu.findItem(R.id.orders).isChecked = true
                    1 ->
                        bt_navigation.menu.findItem(R.id.vouchers).isChecked = true
                    2 -> {
                        bt_navigation.menu.findItem(R.id.bills).isChecked = true
                        mPagerAdapter.refreshFragment(2, OptimizingFragment())
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

    }


}