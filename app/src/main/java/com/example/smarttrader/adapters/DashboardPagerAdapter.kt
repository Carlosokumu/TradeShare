package com.example.smarttrader.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.smarttrader.fragments.DataFeed
import com.example.smarttrader.fragments.Forum
import com.example.smarttrader.fragments.Positions
import com.example.smarttrader.fragments.SignIn
import com.example.smarttrader.settings.Settings

class DashboardPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return  3
    }

    override fun createFragment(position: Int): Fragment {
        return  when(position){
            DATA_FEED -> DataFeed()
            POSITIONS -> if(Settings.isUserLogged()!!)  Positions() else SignIn()
            FORUM -> Forum()
            else ->   throw IllegalArgumentException("no item")
        }
    }



    companion object {
        internal const val DATA_FEED= 0
        internal const val POSITIONS = 1
        internal  const val FORUM = 2
    }


}