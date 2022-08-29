package com.example.smarttrader.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.smarttrader.R
import com.example.smarttrader.adapters.FragmentPagerAdapter
import com.example.smarttrader.databinding.ActivityMenuBinding
import com.google.android.material.tabs.TabLayoutMediator

class MenuActivity : AppCompatActivity() {


    private  lateinit var fragmentPagerAdapter: FragmentPagerAdapter
    private lateinit var binding: ActivityMenuBinding
    private var selectedTabPosition: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPagerViewPager()
    }




    private fun setUpPagerViewPager () {
        fragmentPagerAdapter = FragmentPagerAdapter(this)
        binding.pager.apply {
            this.adapter = fragmentPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    selectedTabPosition = position
                    offscreenPageLimit = 2
                }
            })
            currentItem = selectedTabPosition
        }

        TabLayoutMediator(binding.tabLayout, binding.pager) { currentTab, currentPosition ->
            currentTab.text = when (currentPosition) {
                FragmentPagerAdapter.LOGIN_SCREEN_POSITION -> {
                    getString(R.string.accountdet)

                }
                FragmentPagerAdapter.MENU_SCREEN -> {
                    getString(R.string.mainmenu)

                }
                else -> getString(R.string.datafeed)
            }
        }.attach()
    }
}