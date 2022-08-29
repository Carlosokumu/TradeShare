package com.example.smarttrader.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.smarttrader.R
import com.example.smarttrader.adapters.DashboardPagerAdapter
import com.example.smarttrader.databinding.ActivityMainBinding
import com.example.smarttrader.settings.Settings
import com.example.smarttrader.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var binding: ActivityMainBinding
    private lateinit var dashboardPagerAdapter: DashboardPagerAdapter
    private var selectedTabPosition: Int = 0
    private val  mainVm: MainViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPagerViewPager()
        binding.mainMenu.setOnClickListener(this)

        Log.d("OTPCODE", Settings.getOtp().toString())

        if(Settings.isEmailVerified()!!){
            mainVm.sendConfirmationEmail(Settings.getEmail()!!,Settings.getUserName()!!)
        }

    }


    private fun setUpPagerViewPager() {
        dashboardPagerAdapter = DashboardPagerAdapter(this)
        binding.pager.apply {
            this.adapter = dashboardPagerAdapter
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
                DashboardPagerAdapter.DATA_FEED -> {
                    getString(R.string.datafeed)

                }
                DashboardPagerAdapter.POSITIONS -> {
                    getString(R.string.positions)
                }
                DashboardPagerAdapter.FORUM -> {
                    getString(R.string.forums)
                }
                else -> getString(R.string.datafeed)
            }
        }.attach()

    }

    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.mainMenu -> {
                startActivity(Intent(this, MenuActivity::class.java))
            }
        }

    }


    override fun onBackPressed() {
        super.onBackPressed()

        if (Settings.isUserLogged()!!) {
            Settings.setUserExit(true)
        }

    }

}